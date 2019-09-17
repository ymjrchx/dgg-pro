#!/usr/bin/env python
# encoding: utf-8
'''
@author: Li Huan
@contact: lihuan@dgg.net
@file: order_mongo.py
@time: 2019/5/14 9:24
@desc:
'''
import pandas as pd
from loans_drop.superclass.read_mongo import Read_From_Mongo
import datetime
import time
import os
import sys
sys.path.append(os.environ['PUSHPATH'])
from loans_drop.config.c_define import *

pd.set_option('display.width', 1000)
# 显示所有列
pd.set_option('display.max_columns', 1000)
# 显示所有行
pd.set_option('display.max_rows', None)
# 设置value的显示长度为100，默认为50
pd.set_option('max_colwidth', 1000)

class Read_order(Read_From_Mongo):
    def __init__(self):
        super(Read_order,self).__init__()

    def read_from_mongo(self, table_name=None, column_list=None, filters=None):
        # 父类方法不满足时，再去重写父类方法
        return super(Read_order,self).read_from_mongo(table_name=table_name, column_list=column_list, filter_dict=filters)


def read_order_from_mongo(read_day = 180):
    # 读取订单记录，写入本地文件
    column_list = ['customer_id', 'user_no', 'product_amount']
    read = Read_order()
    print_to_log('read_order_from_mongo')
    start = time.time()
    if read_day != None:
        today = datetime.date.today()
        start_day = today - datetime.timedelta(read_day)
        today = today.strftime('%Y%m%d')
        start_day = start_day.strftime('%Y%m%d')
        # 过滤时间和字段
        filters = {'dw_date': {'$gte': int(start_day), '$lte': int(today)},'user_no':{'$ne':None},'business_id':{'$ne':None},'customer_id':{'$ne':None},'business_type_id':{'$regex':'BUS_YT_DK.*'}}
        results = read.read_from_mongo(table_name='bi_order_detail', column_list=column_list, filters=filters)
    else:
        # 过滤字段
        filters = {'user_no':{'$ne':None},'business_id':{'$ne':None},'customer_id':{'$ne':None},'business_type_id':{'$regex':'BUS_YT_DK.*'}}
        results = read.read_from_mongo(table_name='bi_order_detail', column_list=column_list, filters=filters)
    df = pd.DataFrame(list(results))
    print('历史数据拉取成功, 耗时:{:.3f}s'.format(time.time()-start))
    return df


def read_order_data():
    """
    读取历史订单记录，用作评分数据
    Returns:
        pandas-dataframe
    """
    print_to_log("开始读取今天的订单记录")
    order_df = read_order_from_mongo(read_day=60)
    order_df.rename(columns={'user_no': 'login_name'}, inplace=True)
    # order_df = order_df[['customer_id', 'login_name', 'business_id', 'place_order_time', 'product_amount']]
    print_to_log(order_df.shape)
    print_to_log(order_df.info())
    order_df['login_name'] = order_df['login_name'].astype('object')
    order_df['customer_id'] = order_df['customer_id'].astype('int')
    print_to_log(order_df.info())
    return order_df


if __name__ == '__main__':
    df = read_order_from_mongo(read_day=120)
    print(df.shape)
    print(df.info())
    print(df.head())





