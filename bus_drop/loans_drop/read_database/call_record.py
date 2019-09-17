#!/usr/bin/env python
# encoding: utf-8
'''
@author: 罗成
@contact: luocheng@dgg.net
@file: call_record.py
@time: 2019-05-05 16:57
@desc:
'''
import pandas as pd
from loans_drop.superclass.read_mysql import Read_From_MySQL as readMysql
from loans_drop.config.path import data_path
from tqdm import tqdm_notebook as tqdm
import datetime
import os


def get_call_record():
    """
    读取通话记录
    Returns:
        没有返回值，写入本地文件
    """
    pwd = os.getcwd()
    # 当前文件的父路径
    father_path = os.path.abspath(os.path.dirname(pwd) + os.path.sep + ".")
    output_path = data_path + 'follow_record.csv'
    database_name = 'db_data_warehouse'
    conn = readMysql().getConnectionFromDataWarehousing(database=database_name)
    cs1 = conn.cursor()
    result = []
    columns = readMysql().column_from_mysql(database_name=database_name, table_name='call_record')

        #         sql = "SELECT * FROM `uc_customer_record` WHERE create_time>'%s 00:00:00' AND create_time<'%s 23:59:59' AND remark_type='BUS_3';" % (current_date, current_date)
    sql = "SELECT login_name, customer_id, call_duration FROM call_record;"
    count = cs1.execute(sql)

    result = result + [list(column_set) for column_set in list(cs1.fetchall())]
    df = pd.DataFrame(result,columns=['login_name', 'customer_id', 'call_duration'])
    # print(df.shape)
    # print(df.head())
    df.to_csv(output_path, mode='a', header=False)
    #         del (df)
    #         result = []
    #
    # cs1.close()
    # conn.close()
    return df


if __name__ == '__main__':
    pd.set_option('display.width', 1000)
    # 显示所有列
    pd.set_option('display.max_columns', 1000)
    # 显示所有行
    pd.set_option('display.max_rows', None)
    # 设置value的显示长度为100，默认为50
    pd.set_option('max_colwidth', 1000)
    df = get_call_record()
    print(df.info())
    print(df.head())