#!/usr/bin/env python
# encoding: utf-8
'''
@author: Li Huan
@contact: lihuan@dgg.net
@file: online_predict.py
@time: 2019/5/21 16:56
@desc:
'''
import pandas as pd
import numpy as np
from loans_drop.config.feature import *
from sklearn.linear_model import LogisticRegression
from sklearn import metrics
from sklearn.externals import joblib
from loans_drop.config.path import data_path, model_path
import pickle
import random
import datetime
from loans_drop.config.c_define import *



class rank():
    def __init__(self):
        if os.path.exists(data_path+'employee_feature.csv') and os.path.exists(data_path+'business_feature.csv') \
                and os.path.exists(model_path+'model.m'):
            print_to_log('读取模型文件...')
            self.model = joblib.load(model_path+'model.m')
            print_to_log('读取商机数据...')
            self.user_data = pd.read_csv(data_path+'employee_feature.csv')
            print_to_log('读取商务数据...')
            self.bus_data = pd.read_csv(data_path+'business_feature.csv')
            print_to_log('简单数据预处理...')
            self.bus_list = list(self.bus_data['customer_id'])
            self.user_list = list(self.user_data['login_name'])

            print_to_log('=======数据初始化完成=======')
            self.flags = 'T'
        else:
            print_to_log('=======等待模型初始化=======')
            self.flags = 'F'
            self.bus_list = []
            self.user_list = []

    def compute(self, bus_id, user_list):

        if self.flags == 'T':
            user_ = [self.user_data.loc[self.user_data['login_name']==id] for id in user_list]
            user_ = pd.concat(user_, ignore_index=True).reset_index(drop=True)
            if user_.shape[0]==0:
                print_to_log('没有待选商务，无法推送:')
                return ''
            else:
                user_list = list(user_['login_name'])
                user_.drop('login_name', axis=1, inplace=True)
                print_to_log('待选商务列表:', user_list)
                c = self.bus_data.loc[self.bus_data['customer_id']==bus_id]

                if len(c)==0:
                    print_to_log('没有商机数据，进行冷启动')
                    c = self.bus_data.iloc[[-1]]

                c.drop('customer_id',axis=1,inplace=True)
                bus_ = pd.concat([c.iloc[[-1]]]*len(user_list),ignore_index=True).reset_index(drop=True)
                print_to_log(bus_id, bus_.shape)
                merge_data = pd.concat((user_, bus_), 1)
                pred = self.model.predict_proba(merge_data)[...,1]
                index = np.argsort(pred)[::-1]

                print_to_log('pred:', pred)
                print_to_log('index:', index)
                print_to_log('user_list:', user_list)
                choice = dict()
                try:
                    choice['user'] = user_list[index[0]]
                    choice['probability'] = pred[index[0]]
                except Exception as e:
                    choice['user'] = '无法获取预测成功的商务'
                    choice['probability'] = 0
                    print_to_log('error:组装dictionary出错', e)

                print_to_log('计算结果', choice)

            return choice

        elif self.flags == 'F':
            print_to_log('模型未初始化，等待离线训练完成')
            return ''

    def update_data(self):
        self.__init__()
        print_to_log('=======数据更新完成=======')

if __name__=='__main__':
    ranker = rank()
    user_list = random.sample(ranker.user_list, 5)
    for i in range(50):
        print_to_log(i)
        bus_id = random.sample(ranker.bus_list,1)[0]
        push = ranker.compute(bus_id,user_list)