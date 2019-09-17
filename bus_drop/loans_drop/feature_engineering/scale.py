#!usr/bin/env python
# encoding: utf-8
"""
@author: 孙小龙
@contact: sunxiaolong@dgg.net
@file: scale.py
@time: 2019/5/14 下午6:43
@desc:
"""
import pandas as pd
import numpy as np
import os
import _pickle as cPickle


class Data_Scaler:
    def fit(self, data, var, method="std"):
        """
        进行标准化归一化的fit，并将fit的结果保存为pkl文件
        """
        from sklearn import preprocessing
        import _pickle as cPickle
        df = data[var].values.reshape(-1, 1)
        dic = {}
        if method == "std":
            std = preprocessing.StandardScaler().fit(df)
            dic["mean"] = float(std.mean_)
            dic["scale"] = float(std.scale_)

        elif method == "minmax":
            mm = preprocessing.MinMaxScaler().fit(df)
            dic["min"] = float(mm.data_min_)
            dic["max"] = float(mm.data_max_)

        else:
            pass
        return dic

    def pickles(self, var, scale_pkl_path, dic=None):
        """
        判断某个pkl文件是否存在，不存在则生成pkl文件
        """
        if os.path.isfile(scale_pkl_path):  # 某个pkl文件是否存在
            pass
        else:
            name = var + "_scale_pickle.pkl"
            pk_path = os.path.split(scale_pkl_path)[0]
            #             dic =
            cPickle.dump(dic, open(os.path.join(pk_path, name), "wb"))
        print("变量%s的pkl文件已生成" % var)
        return scale_pkl_path

    def transform(self, data, var, scale_pkl_path, method="std"):
        """
        利用fit的标准，再对pkl文件为标准对新数据进行transofrm
        """
        dic = cPickle.load(open(scale_pkl_path, "rb"))
        if method == "std":
            func = lambda x: (x - dic.get("mean")) / dic.get("scale")
            data[var] = data[var].apply(func=func)

        elif method == "minmax":
            func = lambda x: (x - dic.get("min")) / dic.get("max")
            data[var] = data[var].apply(func=func)
        return data


if __name__ == "__main__":
    data = pd.read_excel("../data/bin_data.xlsx")
    data.head()

    # TODO: 进行单变量的标准化和归一化
    scaler = Data_Scaler()
    var = "年龄"
    dic = scaler.fit(data, var="年龄", method="minmax")
    scale_pkl_path = "./fe_pickles/年龄_scale_pickle.pkl"
    if os.path.isfile(scale_pkl_path):  # 某个pkl文件是否存在
        pass
    else:
        name = var + "_scale_pickle.pkl"
        pk_path = os.path.split(scale_pkl_path)[0]
        dic = dic
        cPickle.dump(dic, open(os.path.join(pk_path, name), "wb"))
        print("变量%s的pkl文件已生成" % var)
    data = scaler.transform(data=data, scale_pkl_path=scale_pkl_path, var="年龄", method="minmax")
    print(data.head())
