#!/usr/bin/env python
# encoding: utf-8
'''
@author: 罗成
@contact: luocheng@dgg.net
@file: userCF.py
@time: 2019-04-29 10:15
@desc: 基于用户的协同过滤
'''

import random

import math
from operator import itemgetter
import os
import sys


class UserBasedCF():
    # 初始化相关参数
    def __init__(self):
        # 找到与目标用户兴趣相似的20个用户，为其推荐10个employee
        self.n_sim_user = 20
        self.n_rec_employee = 10
        self.no_num = 0

        # 将数据集划分为训练集和测试集
        self.trainSet = {} # user:{item:rating}
        self.testSet = {}

        # 用户相似度矩阵
        self.user_sim_matrix = {} # user:{user:count}
        self.employee_popular = {}
        self.employee_count = 0

        print('Similar user number = %d' % self.n_sim_user, file=sys.stderr)
        print('Recommneded employee number = %d' % self.n_rec_employee, file=sys.stderr)


    def __repr__(self):
        return "我用来做协同过滤的，只不过现在有点问题"


    # 读文件得到“user-item”数据
    def get_dataset(self, filename, pivot=0.75):
        print('设置user-item键值对，格式：user:{item:rating}')
        trainSet_len = 0
        testSet_len = 0
        for line in self.load_file(filename):
            user, employee, rating = line.split(',')
            if random.random() < pivot: # 训练集
                self.trainSet.setdefault(user, {})
                self.trainSet[user][employee] = rating
                trainSet_len += 1
            else: # 测试集
                self.testSet.setdefault(user, {})
                self.testSet[user][employee] = rating
                testSet_len += 1
                if testSet_len > 50000:
                    break
        print('Split trainingSet and testSet success!', file=sys.stderr)
        print('TrainSet = %s' % trainSet_len, file=sys.stderr)
        print('TestSet = %s' % testSet_len, file=sys.stderr)


    # 读文件，返回文件的每一行
    def load_file(self, filename):
        print('==============begin load Data:=====================')
        with open(filename, 'r') as f:
            for i, line in enumerate(f):
                if i == 0:  # 去掉文件第一行的title
                    continue
                yield line.strip('\r\n')
        print('Load %s success!' % filename)

    def merge_dicts(*dict_args):
        """
        合并dict,相同的key后面的会覆盖前面的
        """
        result = {}
        for dictionary in dict_args:
            result.update(dictionary)
        return result


    # 计算用户之间的相似度
    def calc_user_sim(self):
        # 构建“销售-用户”倒排索引
        # key = employeeID, value = list of userIDs who have seen this employee
        print('开始构建“item-user”倒排索引')
        employee_user = {}
        for user, employees in self.trainSet.items():
            for employee in employees:
                if employee not in employee_user:
                    employee_user[employee] = set()
                employee_user[employee].add(user)

                # count item popularity at the same time
                if employee not in self.employee_popular:
                    self.employee_popular[employee] = 0
                self.employee_popular[employee] += 1
        print('成功构建“item-user”倒排索引!')

        self.employee_count = len(employee_user)
        print('Total employee number = %d' % self.employee_count)

        print('开始构建UU矩阵 ...')
        for employee, users in employee_user.items(): # 格式 item:(user1,user2....)
            for u in users:
                for v in users:
                    if u == v:
                        continue
                    self.user_sim_matrix.setdefault(u, {})
                    self.user_sim_matrix[u].setdefault(v, 0)
                    self.user_sim_matrix[u][v] += 1
        print('Build user co-rated employees matrix success!')

        # 计算相似性
        print('Calculating user similarity matrix ...')
        for u, related_users in self.user_sim_matrix.items():
            for v, count in related_users.items():
                # self.trainSet[u] # user:{item:rating}找不到两个user同时评分的item，所以计算不了相似度
                self.user_sim_matrix[u][v] = count / math.sqrt(len(self.trainSet[u]) * len(self.trainSet[v]))
        print('Calculate user similarity matrix success!')


    # 针对目标用户U，找到其最相似的K个用户，产生N个推荐
    def recommend(self, user):

        K = self.n_sim_user
        N = self.n_rec_employee
        rank = {}
        followed_employees = self.trainSet[user]

        if user not in self.user_sim_matrix.keys():
            print('测试集用户%s在训练集中没有跟进记录' % user)
            return []



        print('用户%s的相似用户为:' % self.user_sim_matrix[user].items())

        for similar_user, similarity_factor in sorted(self.user_sim_matrix[user].items(), key=itemgetter(1), reverse=True)[0:K]:
            print(similarity_factor)
            for employee in self.trainSet[similar_user]:
                if employee in followed_employees:
                    continue
                rank.setdefault(employee, 0)
                rank[employee] += similarity_factor
        return sorted(rank.items(), key=itemgetter(1), reverse=True)[0:N]


    # 产生推荐并通过准确率、召回率和覆盖率进行评估
    def evaluate(self):
        print("Evaluation start ...")
        N = self.n_rec_employee
        # 准确率和召回率
        hit = 0
        rec_count = 0
        test_count = 0
        # 覆盖率
        all_rec_employees = set()

        print('针对trainSet的目标用户产生n_sim_user个推荐结果')
        for i, user, in enumerate(self.trainSet):
            test_employees = self.testSet.get(user, {})
            rec_employees = self.recommend(user)
            print('给用户%s推荐的商务列表为:%s' % (user,rec_employees))
            for employee, w in rec_employees:
                if employee in test_employees:
                    hit += 1
                all_rec_employees.add(employee)
            rec_count += N
            test_count += len(test_employees)

        precision = hit / (1.0 * rec_count)
        recall = hit / (1.0 * test_count)
        coverage = len(all_rec_employees) / (1.0 * self.employee_count)
        print('precisioin=%.4f\trecall=%.4f\tcoverage=%.4f' % (precision, recall, coverage))


if __name__ == '__main__':
    # 当前文件的路径
    pwd = os.getcwd()
    # 当前文件的父路径
    father_path = os.path.abspath(os.path.dirname(pwd) + os.path.sep + ".")
    rating_file = father_path + '/data/' + 'rating.csv'

    userCF = UserBasedCF()
    userCF.get_dataset(rating_file)
    userCF.calc_user_sim()
    userCF.evaluate()