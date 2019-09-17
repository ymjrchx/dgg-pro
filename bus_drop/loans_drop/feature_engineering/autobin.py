#!/usr/bin/env python
# encoding: utf-8
'''
@author: 李颖
@contact: liying6@dgg.net
@file: autobin.py
@time: 2019/5/5 14:00
@desc:
'''
import _pickle as cPickle
import os

import numpy as np
import pandas as pd
from scipy.stats import chi2

'''
方法：
bin_equal:等宽等频分箱
bin_kmeans:聚类分箱
bin_chi:卡方分箱

'''


def BinEqual(variance, k=4, method='equal_wid'):
    '''
    对特征进行等宽或者等深分箱
    param：
    variance：str| 需要分箱的变量名称（字符串）
    k：int            | 最多箱的数目,默认是4
    method：选择方法，equal_freq, equal_wid，默认是equal_wid
    输出：
    d：series     | 分箱的结果

    '''
    if method == 'equal_freq':
        d = pd.cut(variance, k, labels=range(k))
    elif method == 'equal_wid':
        w = [1.0 * i / k for i in range(k + 1)]
        w = variance.describe(percentiles=w)[4:4 + k + 1]  # 使用describe函数自动计算分位数
        w[0] = w[0] * (1 - 1e-10)
        d = pd.cut(variance, w, labels=range(k))
    return d


def BinKmeans(df,variance, k=4, n_jobs=4):
    '''
    对特征进行分箱
    param：
    variance：str| 需要分箱的变量名称（字符串）
    k：int        | 最多箱的数目,默认是4
    n_jobs：int    |并行数,默认是4， 一般等于CPU数较好
    输出：
    d：series     | 分箱的结果
    '''
    from sklearn.cluster import KMeans  # 引入KMeans
    kmodel = KMeans(n_clusters=k, n_jobs=4)  # 建立模型，n_jobs是并行数，一般等于CPU数较好
    kmodel.fit(df.variance.values.reshape((len(variance), 1)))  # 训练模型
    c = pd.DataFrame(kmodel.cluster_centers_).sort_values(0)  # 输出聚类中心，并且排序（默认是随机序的）
    w = c.rolling(2).mean().iloc[1:]  # 相邻两项求中点，作为边界点
    w = [0] + list(w[0]) + [variance.max()]  # 把首末边界点加上
    d = pd.cut(df.variance, w, labels=range(k))
    return d


class BinChi:
    """
    进行卡方分箱的类
    1. 方法： chi(self,arr)
    2. chi_merge(self, df, x, y, max_groups=None, threshold=None)
    """
    def chi(self, arr):
        """
        计算数组相邻两行的卡方值
        Args:
            arr: 待计算的数组
        Returns:
            卡方值
        """
        assert (arr.ndim == 2)
        R_N = arr.sum(axis=1)  # 计算每行总频数

        C_N = arr.sum(axis=0)  # 计算每列总行数
        N = arr.sum()  # 总频数

        E = np.ones(arr.shape) * C_N / N
        E = (E.T * R_N).T  # 计算每个位置的期望频数

        square = (arr - E) ** 2 / E
        square[E == 0] = 0  # 期望频数为0时，做除数没有意义，不计入卡方值

        v = square.sum()  # 相加等到卡方值
        return v

    def chi_merge(self, df, x, y, max_groups=None, threshold=None):
        """
        进行卡方分箱核心代码
        Args:
            df: 待分箱数据框
            x: 待分箱变量名
            y: 目标变量
            max_groups: 设置最大分组个数
            threshold: 设置卡方阈值

        Returns:
            变量的分箱的切分点的列表
        """
        freq_tab = pd.crosstab(df[x], df[y])  # 交叉表计算频数
        freq = freq_tab.values
        cutoffs = freq_tab.index.values


        if max_groups is None:
            # 如果未指定卡方阈值，就已95%的置信度(自由度为类数目-1)设定阈值
            if threshold is None:
                # 类数目
                cols_num = freq.shape[-1]
                threshold = chi2.isf(0.05, df=cols_num - 1)

        while True:
            minvalue = None
            minidx = None
            for i in range(len(freq) - 1):
                v = self.chi(freq[i:i + 2])  # 计算相邻的卡方值
                #             print(v)
                if (minvalue is None) or (minvalue > v):
                    minvalue = v
                    minidx = i
            #                 print(minvalue)
            #                 print(minidx)

            # 如果最小卡方值小于阈值，则合并最小卡方值所在的相邻两组，并继续循环
            if (max_groups is not None and max_groups < len(freq)) or (threshold is not None and threshold > minvalue):
                # 更新频数表
                tmp = freq[minidx] + freq[minidx + 1]  # 合并卡方值最小的两个值，合并结果为两个相加
                freq[minidx] = tmp  #
                freq = np.delete(freq, minidx + 1, 0)  # 删除minidx后一行，也就是卡方值最小的两个值，后面的那个位置
                cutoffs = np.delete(cutoffs, minidx + 1, 0)  # 删除对应的切分点
            #             print(minvalue)

            else:  # 最小卡方值不小于阈值，停止合并
                break
        return cutoffs


class Desicion_Tree_Bin:
    def transform_data(self,data,var,target):
        """1. 将单列数据转换成estimator需要的格式"""
        X = np.array(data[var]).reshape((-1,1))
        y = data[target]
        return X,y
    def grid_search(self,param_grid,X,y,scoring = "roc_auc"):
        """2. 用于决策树分箱的决策树训练网格搜索，返回最佳超参数"""
        from sklearn.tree import DecisionTreeClassifier
        from sklearn.model_selection import GridSearchCV
        estimator = DecisionTreeClassifier()
        grid = GridSearchCV(estimator=estimator,param_grid= param_grid,scoring = scoring,cv=3,n_jobs = -1)
        grid.fit(X=X,y=y)
        print("网格搜索得到的最佳%s为%s" %(scoring,grid.best_score_))
        dic = grid.best_params_
        return dic
    def bin_tree(self,data,dic,var,target):
        """ 3. 进行正式决策树分箱的函数
        dic为网格搜索得到的最佳参数
        X:为待分箱的数据，数组形式，可以为transform_data()函数得到的X

        returns:
            cutoffs:分箱的切分点
        """
        from sklearn.tree import DecisionTreeClassifier
        dtc = DecisionTreeClassifier(max_depth=dic.get("max_depth"),
                                           min_samples_leaf = dic.get("min_samples_leaf"))
        X = np.array(data[var]).reshape((-1,1))
        y = data[target]
        dtc.fit(X,y)
        threshold = dtc.tree_.threshold #每个节点的分割值
        cutoffs = threshold[threshold > 0]
        return cutoffs


class Value2Group:
    """
    1. value2group(self, x, cutoffs)
    2. group_add_df(self, df, X, cutoffs, var, inplace)
    """
    def value2group(self, i, cutoffs):
        """
        Args:
            i:某个待分组的元素
            cutoffs: 切分点的列表

        Returns:
            这个元素对应的组别
        """
        cutoffs = sorted(cutoffs)  # 切分点排序
        num_groups = len(cutoffs)  #
        if i <= cutoffs[0]:
            return "小于等于{}".format(cutoffs[0])
        elif i > cutoffs[-1]:
            return "大于{}".format(cutoffs[-1])
        else:
            for j in range(1, num_groups):
                if cutoffs[j - 1] < i <= cutoffs[j]:
                    return "大于%s小于等于%s" % (cutoffs[j - 1], cutoffs[j])

    def group_add_df(self, df, var, cutoffs, new_var, inplace):
        """
        对某个变量进行分组处理
        Args:
            df: 待分组的数据框
            var: 待分组的变量名
            cutoffs: 分组的切分点
            new_var: 分组后的变量名
            inplace: 是否删除原变量

        Returns:
            分组后的数据框
        """
        group = []
        for i in df[var]:
            group.append(self.value2group(i=i, cutoffs= cutoffs))
        df[new_var] = group
        if inplace == False:
            return df
        else:
            df = df.drop(var, axis=0)
            return df


if __name__ == "__main__":

    df = pd.read_excel("../data/bin_data.xlsx")
    print(df.head())

    # 1. 进行分箱得到切分点的列表，并持久化
    ## 1.1.测试卡方分箱
    # bin_chi = BinChi()
    # # 得到切分点的列表
    # x = "年龄"
    # y = "是否违约"
    # cutoff = bin_chi.chi_merge(df=df, x=x, y=y, max_groups=None)

    ## 1.2 测试决策树分箱
    dtb = Desicion_Tree_Bin()
    X, y = dtb.transform_data(data=df, var="年龄", target="是否违约")
    param_grid = {"max_depth": range(1, 8), "min_samples_leaf": range(1, 100)}
    scoring = "roc_auc"
    dic = dtb.grid_search(param_grid, scoring=scoring, X=X, y=y)
    cutoffs = dtb.bin_tree(data=df, dic=dic, var="年龄", target="是否违约")
    print(cutoffs)

    # 2. 加载序列化文件
    ## 2.1. 判断是否存在切分点的序列化文件
    cutoff_pk_path = "./fe_pickles/cutoff.pkl"
    if os.path.isfile(cutoff_pk_path ):
        pass
    else:
        cPickle.dump(cutoffs, open(cutoff_pk_path , "wb"))  # 存到序列化文件

    # 3. 利用切分点的pickle文件，对数据进行分组
    value2group = Value2Group()
    cutoffs = cPickle.load(open(cutoff_pk_path , "rb"))
    df = value2group.group_add_df(df=df, var="年龄", cutoffs=cutoffs, new_var="分组", inplace=False)
    print(df.head())

    # #  2.测试kmeans分箱
    # d = BinKmeans(df = df,variance="年龄")
    # print(d)

