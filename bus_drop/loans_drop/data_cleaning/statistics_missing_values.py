#!/usr/bin/env python
# encoding: utf-8
'''
@author: 罗成
@contact: luocheng@dgg.net
@file: statistics_missing_values.py
@time: 2019-04-30 11:03
@desc:
'''

import missingno as msno
import pandas as pd  # 导入数据分析库Pandas
import numpy as np


if __name__ == '__main__':
    df = pd.read_excel('../data/二分类样本数据.xlsx', encoding='utf-8')
    print(df.shape)
    print(df.info())
    msno.matrix(df, figsize=(24, 10), width_ratios=(10, 1))

    msno.heatmap(df, figsize=(24, 10))

    print("All datas:", len(df))
    print("商机与商务会面次数缺失：", len(df[(df['商机与商务会面次数'].isnull())]))
    print("资源上门率缺失：：", len(df[(df['资源上门率'].isnull())]))
    print('两个都同时缺失：', len(df[(df['资源上门率'].isnull()) & (df['商机与商务会面次数'].isnull())]))
    print(df.describe().T)

    print(np.mean(df.groupby('login_name')['芝麻信用分'].mean()))

    # 部分数据没法用在历史数据训练，比如操行分，商务离职后，历史数据中大量缺失，实时数据中缺失较少
    df = df[df['操行分'].notnull()]
    df.shape[0]