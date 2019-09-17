#!/usr/bin/env python
# encoding: utf-8
'''
@author: Li Huan
@contact: lihuan@dgg.net
@file: CB.py
@time: 2019/5/7 18:45
@desc:
'''
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np
class CB_similarity_matrix():
    '''
    计算训练样本与测试样本之间的相似度矩阵
    输入：
    test_data : 待预测数据, X x M ,X为数据条数，M为字段维度
    train_data: 历史数据，N x M ,N为历史数据条数，M为字段维度
    user_list : 历史数据对应的商务工号列表，1 x N，N为历史数据数量，与train_data的数量相对应,格式为np.array
    函数:
    .top_N(n):
    返回一个 X x n 的矩阵，代表了X调待分配商机的前n个商务的商务工号，格式为np.array
    '''

    def __init__(self,test_data,train_data,user_list):
        # 计算相似矩阵，将返回形状为 X x N 的矩阵
        self.sim = cosine_similarity(test_data, train_data)
        self.user_list = user_list
    def top_N(self,n=50):
        # 对每一条待分配商机挑选出前n个商务的工号
        # 返回 一个X x n 的矩阵
        target = []
        for item in self.sim():
            candidate = np.argmax(item)[::-1][:n]
            target.append(self.user_list[candidate])
        return np.array(target)


