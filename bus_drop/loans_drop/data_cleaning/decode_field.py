#!/usr/bin/env python
# encoding: utf-8
'''
@author: 罗成
@contact: luocheng@dgg.net
@file: decode_field.py
@time: 2019-07-11 18:30
@desc:
'''
import pandas as pd
import numpy as np
import sys
import os
sys.path.append(os.environ['PUSHPATH'])
from loans_drop.config.path import data_path

doc_path = os.environ['PUSHPATH'] + '/doc/'
#显示所有列
pd.set_option('display.max_columns', None)
#显示所有行
pd.set_option('display.max_rows', None)
#设置value的显示长度为100，默认为50
pd.set_option('max_colwidth',100)


def cat_replace(data):
    """根据数据字典data_dict将字段中的字母编码的值替换成中文解释
    :param data:df
    :param var:变量名
    :return 返回一个替换完的数据框
    """
    cols_list = ['businessStatus', 'businessOperate', 'businessLocation', 'opportunitytypeCode',
                 'addTypeCode', 'originCode', 'wayCode', 'originType', 'placeCode', 'isCost', 'lastLoseType',
                 'businessStage', 'remarkType', 'extensionCode']
    data_dict = pd.read_excel(doc_path + "数据字典.xlsx")
    for name in cols_list:
        unique_values = data.loc[data[name].notnull(), name].unique()
        dic = {}
        for value in unique_values:
            try:
                dic[value] = list(data_dict.loc[data_dict["code"] == value, "name"].values)[0]
            except:
                dic[value] = value

        data[name].replace(dic, inplace = True)
        print()
        print(dic)
        print(data[name].value_counts())

    return data


if __name__ == '__main__':
    bus_data = pd.read_csv(data_path + 'total_data20190704.csv')
    df = cat_replace(bus_data)
