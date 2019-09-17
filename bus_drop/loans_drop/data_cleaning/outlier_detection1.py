#!/usr/bin/env python
# encoding: utf-8
'''
@author: 李颖
@contact: liying6@dgg.net
@file: outlier_detection_lynn.py
@time: 2019/5/5 15:20
@desc:
'''
import numpy as np
import pandas as pd


def OutlierDetection(data, variance, method='std'):
    '''
    异常值检测
    param：
    data：dataframe| 需要检测的数据
    variance：str            | 需要检测的变量
    method：str | 选择方法，std,box，默认是std
    输出：
    bool
    '''

    if method == 'std':
        # 异常值检测之标准差法
        xbar = data[variance].mean()
        xstd = data[variance].std()
        print('标准差法异常值上限检测：\n', any(data[variance] > xbar + 3 * xstd))
        # print(data[data[variance] > xbar + 3 * xstd])
        print('标准差法异常值下限检测：\n', any(data[variance] < xbar - 3 * xstd))
        # print(data[data[variance] < xbar - 3 * xstd])

    elif method == 'box':
        # 异常值检测之箱线图法
        Q1 = data[variance].quantile(q=0.25)
        Q3 = data[variance].quantile(q=0.75)
        IQR = Q3 - Q1
        print('箱线图法异常值上限检测：\n', any(data[variance] > Q3 + 1.5 * IQR))
        # print(data[data[variance] > Q3 + 1.5 * IQR])
        print('箱线图法异常值下限检测：\n', any(data[variance] < Q1 - 1.5 * IQR))
        # print(data[data[variance] < Q1 - 1.5 * IQR])


def OutlierKmeans(data, variance, k=5, threshold=2, iteration=500):
    '''
    异常值检测
    param：
    data：dataframe| 需要检测的数据
    variance：list            | 需要检测的变量
    k: 聚类类别
    threshold:离散点阈值,默认是2
    iteration：聚类最大循环次数
    show_outlier：bool  | 是否显示异常值，默认False
    输出：


    '''

    data_zs = 1.0 * (data - data.mean()) / data.std()
    from sklearn.cluster import KMeans
    model = KMeans(n_clusters=k, max_iter=iteration)
    model.fit(data_zs)  # 开始聚类
    r = pd.concat([data_zs, pd.Series(model.labels_, index=data.index)], axis=1)  # 每个样本对应的类别
    r.columns = list(data.columns) + [u'聚类类别']  # 重命名表头
    norm = []
    for i in range(k):  # 逐一处理
        norm_tmp = r[variance][r[u'聚类类别'] == i] - model.cluster_centers_[i]
        norm_tmp = norm_tmp.apply(np.linalg.norm, axis=1)  # 求出绝对距离
        norm.append(norm_tmp / norm_tmp.median())  # 求相对距离并添加
    norm = pd.concat(norm)  # 合并

    discrete_points = norm[norm > threshold]  # 离群点
    return discrete_points


def OutlierSvm(data, kernel='rbf', gamma='auto', tol=0.001):
    '''
    异常值检测
    param：
    data：dataframe| 需要检测的数据或变量
    kernel：默认‘rbf’，核函数'linear'，'poly'，'rbf'，'sigmoid'，'precomputed'
    gamma：'rbf'，'poly'和'sigmoid'的核系数，默认‘auto’
    tol：默认0.001,精度
    输出：
    dataframe：data['isolate']，1是正常样本，-1是异常样本
    '''

    import numpy as np
    from sklearn import svm

    # fit the model
    clf = svm.OneClassSVM(nu=0.1, kernel="rbf", gamma=0.1)
    clf.fit(data)
    y_pred = clf.predict(data)
    data['classSVM'] = y_pred
    return data


def OutlierIsolate(data, behaviour='new', max_samples=100, contamination='auto'):
    '''
    异常值检测
    param：
    data：dataframe| 需要检测的数据或变量
    behaviour：默认‘new’，使decision_function更改与其他异常检测算法API相匹配，也可以是‘old’，
    max_samples：int  | 基估算器数量，默认100
    random_state：默认‘rng’,如果是int，则random_state是随机数生成器使用的种子; 如果是RandomState实例，则random_state是随机数生成器;
    contamination：| 异常值阈值，默认‘auto’，决策函数阈值，如原始论文中所示
    输出：
    dataframe：data['isolate']，1是正常样本，-1是异常样本
    '''

    import numpy as np
    from sklearn.ensemble import IsolationForest
    rng = np.random.RandomState(42)
    # fit the model
    clf = IsolationForest(behaviour='new', max_samples=100,
                          random_state=rng, contamination='auto')
    clf.fit(data)
    y_pred = clf.predict(data)
    data['isolate'] = y_pred
    return data


def OutlierLOF(data, n_neighbors=20, contamination=0.1):
    '''
    异常值检测
    param：
    data：dataframe| 需要检测的数据或变量
    behaviour：默认‘new’，使decision_function更改与其他异常检测算法API相匹配，也可以是‘old’，
    max_samples：int  | 基估算器数量，默认100
    random_state：默认‘rng’,如果是int，则random_state是随机数生成器使用的种子; 如果是RandomState实例，则random_state是随机数生成器;
    contamination：| 异常值阈值，默认‘auto’，决策函数阈值，如原始论文中所示
    '''
    import numpy as np
    from sklearn.neighbors import LocalOutlierFactor

    # fit the model for outlier detection (default)
    clf = LocalOutlierFactor(n_neighbors=20, contamination=0.1, novelty=True)
    clf.fit(data)
    y_pred = clf.predict(data)
    data['lof'] = y_pred
    return data
