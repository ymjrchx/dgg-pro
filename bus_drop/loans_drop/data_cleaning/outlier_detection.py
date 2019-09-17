#!/usr/bin/env python
# encoding: utf-8
'''
@author: 罗成
@contact: luocheng@dgg.net
@file: outlier_detection.py
@time: 2019-04-30 09:48
@desc:
'''


import os
import numpy as np

import matplotlib.pyplot as plt
import pandas as pd


def detection_by_scatter(dataframe, x_feature = '芝麻信用分', y_feature = 'performance_amount'):
    """
    # 发现离群点
    Args:
        dataframe:
        x_feature: 需要检测异常值的特征1
        y_feature: 需要检测异常值的特征2

    Returns:
        没有返回值，直接显示图例

    """
    fig, ax = plt.subplots()
    ax.scatter(x=dataframe[x_feature], y=dataframe[y_feature])
    plt.ylabel(y_feature, fontsize=13)
    plt.xlabel(x_feature, fontsize=13)
    plt.show()


def drop_detection_scatter(dataframe, x_feature = '芝麻信用分', y_feature = 'performance_amount', x_min = 0, x_max = 0, y_min = 0, y_max = 0):
    """
    干掉离群点
    Args:
        dataframe:
        x_feature: 需要处理异常值的特征1
        y_feature: 需要处理异常值的特征2
        x_min: 特征1的正常取值范围，超过此范围舍去
        x_max: 特征1的正常取值范围，超过此范围舍去
        y_min: 特征2的正常取值范围，超过此范围舍去
        y_max: 特征2的正常取值范围，超过此范围舍去

    Returns:
        处理掉异常值后的dataframe

    """
    train = dataframe.drop(dataframe[(dataframe[x_feature] < x_min) & (dataframe[x_feature] > x_max) & (dataframe[y_feature] < y_min) & (dataframe[y_feature] > y_max)].index)

    # Check the graphic again
    fig, ax = plt.subplots()
    ax.scatter(train[x_feature], train[y_feature])
    plt.ylabel(y_feature, fontsize=13)
    plt.xlabel(x_feature, fontsize=13)
    plt.show()
    return train




if __name__ == '__main__':
    print('开始检测异常值:')