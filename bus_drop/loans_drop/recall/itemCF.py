#!/usr/bin/env python
# encoding: utf-8
'''
@author: 李颖
@contact: Liying6@dgg.net
@file: itemCF.py
@time: 2019/5/16 9:37
@desc:
'''

import numpy as np
import pandas as pd
from sklearn import preprocessing
import math

from loans_drop.data_cleaning.load_data import DataLoader
from loans_drop.config.path import data_path
from sklearn import preprocessing
import datetime


class ItemBasedCF():
    # 初始化相关参数
    def __init__(self):
        # 找到与目标用户兴趣相似的20个用户，为其推荐10个employee
        self.n_sim_user = 20
        self.n_rec_employee = 10
        self.no_num = 0

        # 将数据集划分为训练集和测试集
        self.score_data = None
        self.train_set = dict()

        # 用户相似度矩阵
        self.item_sim_matrix = {} # user:{user:count}
        self.employee_popular = {}
        self.employee_count = 0
        self.load_file()
        self.get_dataset()
        print(datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"), '=======ItemCF初始化完成========')




    def __repr__(self):
        return "我用来做协同过滤的，只不过现在有点问题"



    def get_dataset(self):
        print(datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"), '设置user-item键值对，格式：user:{item:rating}')
        # 用户-物品的评分矩阵
        train = dict()
        for line in open(data_path + 'score_data.csv', encoding='utf-8'):
            user, item, score = line.strip().split(",")
            train.setdefault(user, {})
            train[user][item] = score
        self.train_set = train

        # 建立物品-物品的共现矩阵
        C = dict()  # 物品-物品的共现矩阵
        N = dict()  # 物品被多少个不同用户购买
        for user, items in self.train_set.items():
            for i in items.keys():
                N.setdefault(i, 0)
                N[i] += 1
                C.setdefault(i, {})
                for j in items.keys():
                    if i == j:
                        continue
                    C[i].setdefault(j, 0)
                    C[i][j] += 1

        # 计算相似度矩阵
        W = dict()
        for i, related_items in C.items():
            W.setdefault(i, {})
            for j, cij in related_items.items():
                W[i][j] = cij / (math.sqrt(N[i] * N[j]))
        self.item_sim_matrix = W


    # 读文件，返回文件的每一行
    def load_file(self):
        print(datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"), '==============begin load Data:=====================')
        total_data = DataLoader().pull_total_data()
        # 删除'call_duration', 'follow_days', 'product_amount'都是缺失的值
        total_data.dropna(subset=['call_duration', 'follow_days', 'product_amount'], axis=0, how='all', inplace=True)
        # 评分，跟进次数用0填充
        total_data.fillna(value={'call_duration': 0, 'follow_days': 0, 'salary_score': 0}, inplace=True)

        # 对product_amount字段进行0,1转换,缺失0负样本，有值为1正样本
        def nan_encoder(data, variances):
            df = data.loc[:, variances]
            df = df.notnull() * 1
            columns = [i + "is_nan" for i in variances]
            df.columns = columns
            return df

        data_1 = nan_encoder(total_data, ['product_amount'])
        total_data = pd.concat([total_data, data_1], axis=1)
        total_data.rename(columns={"product_amountis_nan": "label"}, inplace=True)
        # 重置index
        total_data.index = range(len(total_data))

        # 处理粗排数据
        score_data = total_data[['customer_id', 'login_name', 'call_duration', 'follow_days', 'salary_score', 'label']]

        # 归一化

        score_data['salary_score'] = preprocessing.MinMaxScaler().fit_transform(score_data[['salary_score']])
        score_data['call_score'] = preprocessing.MinMaxScaler().fit_transform(score_data[['call_duration']])
        score_data['follow_score'] = preprocessing.MinMaxScaler().fit_transform(score_data[['follow_days']])
        # 计算评分
        '''
        计算规则：
        成单：1分，权重 10，跟进等其他情况不再计分
        未成单：
        call_score:0-1分，权重 2
        follow_score:0-1分，权重 2
        salary_score：0-1分，权重 3
        '''
        score_data['rate'] = round(
            (score_data['call_score'] * 2 + score_data['follow_score'] * 2 + score_data['salary_score'] * 3), 2)

        score = []
        for i in range(len(score_data)):
            if score_data.loc[i, "label"] == 1:
                score.append(10)
            else:
                score.append(score_data.rate[i])
        score_data['score'] = score

        # itemCF需要的数据
        score_data = score_data[['customer_id', 'login_name', 'score']]
        score_data.to_csv(data_path + 'score_data.csv', encoding='utf-8', sep=',', index=False, header=False)
        self.score_data = score_data
        print(datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"), 'Load %s success!' % data_path + 'score_data.csv')





    # 针对目标用户U，找到其最相似的K个用户，产生N个推荐
    # topN推荐
    def recommend(self, user, k=10, nitem=50):

        rank = dict()
        ru = self.train_set.get(user, {})
        for item, score in ru.items():
            try:
                for j, wj in sorted(self.item_sim_matrix[item].items(), key=lambda x: x[1], reverse=True)[0:k]:
                    if j in ru:
                        continue
                    rank.setdefault(j, 0)
                    rank[j] += float(score) * wj
            except KeyError:
                continue
        final = dict(sorted(rank.items(), key=lambda x: x[1], reverse=True)[0:nitem])
        return final

    def recommend_employee(self, customers=[]):
        """
        :param customers: list，里面装的全是字符串customer_id
        :return: {customer_id:{login_name:score}}
        """
        recommend_data = dict()
        for customer_id in customers:
            recommend_data.setdefault(customer_id, {})
            a = self.recommend("%s" % customer_id)
            recommend_data[customer_id] = a

        return recommend_data


if __name__ == '__main__':

    print(ItemBasedCF().recommend_employee())
