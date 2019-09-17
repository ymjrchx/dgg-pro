#!/usr/bin/env python
# encoding: utf-8
'''
@author: 罗成
@contact: luocheng@dgg.net
@file: rating_data.py
@time: 2019-05-17 17:03
@desc: 用于加载全量数据
'''
import os
import sys
sys.path.append(os.environ['PUSHPATH'])
from loans_drop.data_cleaning.load_data import DataLoader
from loans_drop.config.c_define import *
from loans_drop.config.path import data_path


def load_date(date):
    loader = DataLoader()
    loader.date = date
    loader.out_path = data_path + 'total_data{}.csv'.format(loader.date)
    try:
        loader.pull_total_data()
        print_to_log(loader.employee_df.shape)
        print_to_log(loader.business_df.shape)
        print_to_log(loader.total_data.shape)
    except Exception as e:
        print_to_log('系统初次运行--error拉取数据失败，采用前一天的数据: ', e)


if __name__ == '__main__':
    static_day = (datetime.datetime.now()-datetime.timedelta(1)).strftime('%Y%m%d')
    if len(sys.argv) == 2:
        print('默认参数：', sys.argv)
        static_day = int(sys.argv[1])

    load_date(static_day)
