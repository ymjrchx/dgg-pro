#!/usr/bin/env python
# encoding: utf-8
'''
@author: 罗成
@contact: luocheng@dgg.net
@file: handing_missing_values.py
@time: 2019-04-30 10:31
@desc:
'''


import pandas as pd  # 导入数据分析库Pandas
from scipy.interpolate import lagrange  # 导入拉格朗日插值函数
from tqdm import tqdm_notebook as tqdm
import matplotlib.pyplot as plt
from pylab import mpl
import math
import os
import numpy as np

"""
1. ployinterp_column：拉格朗日差值法
2. Fill_Nan类，对mode\mean\median的缺失值填充进行持久化
3. 

"""

def ployinterp_column(s, n, k=5):
    """
    拉格朗日插值--自定义的列向量插值函数
    https://blog.csdn.net/xiaowei_cqu/article/details/8584966
    https://blog.csdn.net/deramer1/article/details/79037740
    Args:
        s:列向量
        n:被插值的位置
        k:取前后的数据个数，默认为5

    Returns:
        插值结果
    """
    y = s[list(range(n - k, n)) + list(range(n + 1, n + 1 + k))]  # 取数
    y = y[y.notnull()]  # 剔除空值
    return lagrange(y.index, list(y))(n)  # 插值并返回插值结果




"""
牛顿插值法
插值的函数表为
xi      0.4，       0.55，     0.65，      0.80，       0.90，   1.05
f(xi)   0.41075,    0.57815,   0.69675,    0.88811,    1.02652,  1.25382
"""

x = [0.4, 0.55, 0.65, 0.80, 0.90, 1.05]
y = [0.41075, 0.57815, 0.69675, 0.88811, 1.02652, 1.25382]


def five_order_difference_quotient(x, y):
    """
    计算五次差商的值
    Args:
        x:
        y:

    Returns:

    """
    # i记录计算差商的次数，这里循环5次，计算5次差商。
    i = 0
    quotient = [0, 0, 0, 0, 0, 0]
    while i < 5:
        j = 5
        while j > i:
            if i == 0:
                quotient[j] = ((y[j] - y[j - 1]) / (x[j] - x[j - 1]))
            else:
                quotient[j] = (quotient[j] - quotient[j - 1]) / (x[j] - x[j - 1 - i])
            j -= 1
        i += 1
    return quotient;


def function(data):
    return x[0] + parameters[1] * (data - 0.4) + parameters[2] * (data - 0.4) * (data - 0.55) + \
           parameters[3] * (data - 0.4) * (data - 0.55) * (data - 0.65) \
           + parameters[4] * (data - 0.4) * (data - 0.55) * (data - 0.80)


def calculate_data(x, parameters):
    """
    计算插值多项式的值和相应的误差
    Args:
        x:
        parameters:

    Returns:

    """
    returnData = [];
    for data in x:
        returnData.append(function(data))
    return returnData


def draw(newData):
    """
    画函数的图像
    Args:
        newData: 曲线拟合后的曲线
    """
    plt.scatter(x, y, label="离散数据", color="red")
    plt.plot(x, newData, label="牛顿插值拟合曲线", color="black")
    plt.scatter(0.596, function(0.596), label="预测函数点", color="blue")
    plt.title("牛顿插值法")
    mpl.rcParams['font.sans-serif'] = ['SimHei']
    mpl.rcParams['axes.unicode_minus'] = False
    plt.legend(loc="upper left")
    plt.show()


def dummy_nan(data, var, target):
    """通过判断特征值是否有缺失值定义一个新的二分类变量

    :param data: 待处理数据框
    :param var: 含有缺失值的变量名
    :param y: 类别型的目标变量名
    :param return: 返回添加了新列的数据框，并展示新列与目标变量的关系图
    """
    import seaborn as sns
    data[var + "_is_nan"] = data[var].copy()
    data.loc[(data[var].notnull()), var + "_is_nan"] = 0
    data.loc[(data[var].isnull()), var + "_is_nan"] = 1

    fig, ax = plt.subplots(figsize=(10, 5))
    sns.countplot(x=var + "_is_nan", hue=target, data=data)
    plt.show()
    return data


def find_notnan_cols(data):
    """
    返回数据框中不含有np.NaN的列的列名
    Args:
        self:
        data: 原数据框
    Returns:
        不含有np.NaN的列的列名
    """
    des = data.describe(include = "all")
    l = len(data)
    s = des.iloc[0]
    notnan_cols = list(s[s == l].index)
    print("数据框中一共有%s列,不含 np.NaN 一共有%s列,列名为 %s" % (len(s),len(notnan_cols),notnan_cols))
    return notnan_cols


class Fill_Nan:
    """mode、median、mean的持久化"""

    def fit(self, data, pickles_path=None):
        """fit出数据框中的每个特征的mode、median、mean
        pickles_path为存放pkl文件的文件夹目录"""
        import _pickle as cPickle
        import os
        mean_dict = data.mean(axis=0).to_dict()
        print(mean_dict)
        mean_dict_pkl = os.path.join(pickles_path, "mean_dict.pkl")
        if os.path.isfile(mean_dict_pkl):
            pass
        else:
            cPickle.dump(mean_dict, open(mean_dict_pkl, "wb"))
        mode_dict = data.mode(axis=0).to_dict()
        mode_dict_pkl = os.path.join(pickles_path, "mode_dict.pkl")
        if os.path.isfile(mode_dict_pkl):
            pass
        else:
            cPickle.dump(mode_dict, open(mode_dict_pkl, "wb"))

        median_dict = data.median(axis=0).to_dict()
        median_dict_pkl = os.path.join(pickles_path, "median_dict.pkl")
        if os.path.isfile(median_dict_pkl):
            pass
        else:
            cPickle.dump(median_dict, open(median_dict_pkl, "wb"))
        print("数据的mode\mean\meadian的pkl文件准备完毕")

    def fill_special_values(self, dic, pickles_path=None):
        """利用特殊值对缺失值进行填充
        dic:字段要填充的缺失值的字典
        pickles_path:pkl文件夹地址"""
        import _pickle as cPickle
        import os
        special_values_dict_pkl = os.path.join(pickles_path, "special_values.pkl")
        if os.path.isfile(special_values_dict_pkl):
            pass
        else:
            cPickle.dump(dic, open(special_values_dict_pkl, "wb"))
        print("数据的特殊值填充special_values的pkl文件准备完毕")
    def transform(self, data, variances, method, pickles_path=None):
        """
        利用mean,mode,median的pkl文件对多个变量进行缺失值填充
        """
        import _pickle as cPickle

        if method == "mean":
            mean_dict_pkl = os.path.join(pickles_path, "mean_dict.pkl")
            dic = cPickle.load(open(mean_dict_pkl, "rb"))
            print(dic)
        elif method == "mode":
            mode_dict_pkl = os.path.join(pickles_path, "mode_dict.pkl")
            dic = cPickle.load(open(mode_dict_pkl, "rb"))
        elif method == "median":
            median_dict_pkl = os.path.join(pickles_path, "median_dict.pkl")
            dic = cPickle.load(open(median_dict_pkl, "rb"))
        elif method == "special_values":
            special_values_dict_pkl = os.path.join(pickles_path, "special_values.pkl")
            dic = cPickle.load(open(special_values_dict_pkl, "rb"))
        else:
            print("请输入争取的method方法：mode\mean\meadian\special_values")

        if set(variances).issubset(set(data.columns)):  # 判断要填充的变量是否在列名中
            df = data.loc[:, variances]
            df = df.apply(func=lambda x: x.fillna(dic[x.name]), axis=0)
            print("变量%s利用%s对np.NaN填充完毕" % (variances, method))
            return df
        else:
            print("请确保%s中的元素都为数据的列名" % (variances))

class Null_Rate_Filter:
    def null_rate(self, data):
        """得到每列缺失值比例的数据框"""
        null_rate = pd.DataFrame(1 - data.count() / data.shape[0]).reset_index()
        null_rate.columns = ["feature_name", "null_rate"]
        null_rate = null_rate.sort_values(by="null_rate", ascending=False)
        return null_rate

    def filter_null_rate(self, null_rate, threshold):
        """过滤缺失率高于阈值的特征,返回缺失率高于阈值的特征的列表"""
        df = null_rate.loc[null_rate["null_rate"] > threshold, :]
        print("缺失率大于%s的特征有%s个" % (threshold, df.shape[0]))
        cols = df["feature_name"].tolist()
        return cols

    def drop_mul_col(self, df, drop_list, Inplace=True):
        """根据要删除的列名，一次性删除多列"""
        columns = list(df.columns)
        print("原数据框一共有%s列" % len(columns))
        col_index = [columns.index(i) for i in drop_list]
        data = df.drop(df.columns[col_index], axis=1, inplace=Inplace)
        print("数据框字段删除完毕,剩余%s列" % len(data.columns))
        return data


if __name__ == '__main__':
    print("开始处理缺失值")

    # # TODO: 1. 罗成代码测试
    # df = pd.read_excel('../data/二分类样本数据.xlsx', encoding='utf-8')
    # print(df.shape)
    # print(df.info())
    # df[u'芝麻信用分'][(df[u'芝麻信用分'] < 500) | (df[u'芝麻信用分'] > 800)] = None  # 过滤异常值，将其变为缺失值
    #
    # # TODO:逐个元素判断是否需要插值---拉格朗日插值
    # for i in tqdm(range(len(df))):
    #     credit_score = df.iloc[i]['芝麻信用分']
    #     if pd.isnull(credit_score):  # 如果为空即插值。
    #         new_score = ployinterp_column(df['芝麻信用分'], i)
    #         df.at[i, '芝麻信用分'] = new_score
    #         print(new_score)
    #
    # # TODO:牛顿插值法
    # parameters = five_order_difference_quotient(x, y)
    # yuanzu = calculate_data(x, parameters)
    # draw(yuanzu)


    # # TODO: 2.孙小龙代码测试
    # data = pd.read_csv("../data/titanic.csv")
    # data.head()
    # ##  2.1 dummy_nan函数测试
    # data1 = dummy_nan(data=data, var="Cabin", target="Survived")
    # print(data1.head())
    # ## 2.2 find_notnan_cols函数测试
    # print(find_notnan_cols(data))

    # TODO: 3.测试mode\mean\median的缺失值填充持久化，Fill_Nan类
    ##3.1 生成测试数据
    aa = pd.DataFrame([[np.NaN, 1, 3, 4], [3, np.NaN, np.NaN, 5], [3, 2, np.NaN, ], [np.NaN, 2, np.NaN, 4]])
    aa.columns = ["a", "b", "c", "d"]

    ## 3.2 Fill _Nan类测试
    fill_nan = Fill_Nan()
    pickles_path = "./fe_pickles"

    fill_nan.fit(aa, pickles_path=pickles_path)
    df1 = fill_nan.transform(data=aa, variances=["a", "b"], method="mean", pickles_path=pickles_path)
    print(df1)

    fill_nan.fill_special_values(dic={"c": 9999}, pickles_path=pickles_path)
    df2 = fill_nan.transform(data=aa, variances=["c"], method="special_values", pickles_path=pickles_path)
    print(df2)