#!usr/bin/env python
# encoding: utf-8
"""
@author: 孙小龙
@contact: sunxiaolong@dgg.net
@file: onehot_encoder.py
@time: 2019/5/23 上午11:46
@desc:
"""
import pandas as pd
import numpy as np


class OneHot:
    def onehot_fit(self, data, var_list):
        """得到每个字段的字典，key为字段名，value为key对应的unique的列表
        data:全量数据
        var_list:所有待one-hot的字段列表
        """
        onehot_cols = []  # 装所有字段的unique的列表
        dic = {}  # value 为 one-hot后的列名
        for v in var_list:
            unique_list = data.loc[data[v].notnull(), v].unique()
            onehot_col = []
            for a in unique_list:
                if isinstance(a, str):
                    onehot_col.append(v + "_" + a)
                    onehot_cols.append(v + "_" + a)
                else:
                    onehot_col.append(v + "_" + str(a))
                    onehot_cols.append(v + "_" + str(a))
            dic[v] = onehot_col

        return dic, onehot_cols


    def onehot_transform(self, data, var_list, dic):
        """data为待onehot的数据集，可以是x_train,x_test,新来数据
        var_list：待one-hot的字段列表，必须是onehot_fit的var_list的子集"""
        onehot_cols = []  # onehot之后
        for c in var_list:
            for j in dic.get(c):
                onehot_cols.append(j)
        df = pd.DataFrame(np.zeros((data.shape[0], len(onehot_cols)), dtype=np.int), columns=onehot_cols)
        df.index = range(len(df))
        data = data.astype(str)
        cols = data.columns  # 列名的列表
        for c in var_list:  # 可选字段列表
            value_list = dic.get(c)
            for v in value_list:
                df.loc[data[c] == v.split("_")[-1], v] = 1
        return df


if __name__ =="__main__":
    data = pd.DataFrame(data=[["我", "你", "他", "我"], ["一", 1.0, "三", "一"], ["吴", "一", "孙", "孙"]]).T
    data.columns = ["a", "b", "c"]
    print(data)

    onehot = OneHot()
    dic, onehot_cols = onehot.onehot_fit(data, var_list=data.columns) #1. 手动模拟sklearn的onehot_encoder的fit过程，必须是全量数据
    print(dic)

    df = onehot.onehot_transform(data, var_list=["a", "b"], dic=dic) # 2. 手动模拟sklearn的onehot_encoder的transform过程
    print(df)