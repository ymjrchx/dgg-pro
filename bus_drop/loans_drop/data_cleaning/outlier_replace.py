#!/usr/bin/env python
# encoding: utf-8
'''
@author: 李颖
@contact: liying6@dgg.net
@file: outlier_replace.py
@time: 2019/5/5 15:44
@desc:
'''
import pandas as pd
import numpy as np

def OutlierReplace(sunspots, variance, method):
    # 根据正态分布处理异常值
    print('异常值替换前的数据统计特征：\n', sunspots[variance].describe())
    if method == 'std':
        xbar = sunspots[variance].mean()
        xstd = sunspots[variance].std()
        u_limit = xbar + 3 * xstd
        d_limit = xbar - 3 * xstd
        sunspots.loc[sunspots[variance] > u_limit, variance] = u_limit
        sunspots.loc[sunspots[variance] < d_limit, variance] = d_limit
    # 盖帽法处理异常值
    # 箱线图中的异常值判别上下限
    if method == 'box':
        Q1 = sunspots[variance].quantile(q=0.25)
        Q3 = sunspots[variance].quantile(q=0.75)
        IQR = Q3 - Q1
        UL = Q3 + 1.5 * IQR
        DL = Q1 - IQR
        # print('判别异常值的上限临界值：\n',UL)
        # 从数据中找出低于判别上限的最大值
        # print('判别异常值的下限临界值：\n',DL)
        # 从数据中找出低于判别上限的最大值
        # 从数据中找出高于判别下限的最小值
        replace_value1 = sunspots[variance][sunspots[variance] < UL].max()
        replace_value2 = sunspots[variance][sunspots[variance] > DL].min()
        # print('用以替换异常值的数据：\n',replace_value1, replace_value2)
        # 替换超过判别上限异常值
        # 替换低于判别下限异常值
        sunspots.loc[sunspots[variance] > UL, variance] = replace_value1
        sunspots.loc[sunspots[variance] < DL, variance] = replace_value2
    if method == 'p99':
        P1 = sunspots[variance].quantile(0.01)
        P99 = sunspots[variance].quantile(0.99)
        sunspots[variance] = list(map(lambda x: P1 if x < P1 else (P99 if x > P99 else x), sunspots[variance]))

    print('异常值替换后的数据统计特征：\n', sunspots[variance].describe())
