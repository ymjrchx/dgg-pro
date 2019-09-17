#!/usr/bin/env python
# encoding: utf-8
'''
@author: 罗成
@contact: luocheng@dgg.net
@file: lifecycle_record.py
@time: 2019-05-06 10:39
@desc:分配、跟进历史数据--徐志强
'''
import pandas as pd
from loans_drop.superclass.read_mysql import Read_From_MySQL as readMysql
from tqdm import tqdm_notebook as tqdm
import datetime
import os
from loans_drop.config.path import data_path


def follow_drop_record(read_day=180, today=None):
    """
    读取历史记录表
    Returns:
        没有返回值，直接写入本地文件
    """
    database_name = 'db_data_warehouse'
    conn = readMysql(type=0).getConnectionFromDataWarehousing(database=database_name)
    cs1 = conn.cursor()
    result = []
    if read_day!=None:
        if today is None:
            today = datetime.date.today()
        start_day = today - datetime.timedelta(read_day)
        today = str(today.year)+'-'+str(today.month).zfill(2)+'-'+str(today.day).zfill(2)
        start_day = str(start_day.year)+'-'+str(start_day.month).zfill(2)+'-'+str(start_day.day).zfill(2)
        sql = """SELECT 
                follow_record.customer_id, follow_record.business_id, sys_user_info.login_name, follow_record.content, follow_record.followStart, follow_record.followEnd 
                FROM follow_record,sys_user_info 
                WHERE  
                follow_record.YtCode LIKE 'BUS_YT_DK%' AND 
                follow_record.login_name != 'dropper' AND 
                sys_user_info.id = follow_record.follow_id AND
                follow_record.create_time > '{} 00:00:00' AND follow_record.create_time < '{} 23:59:59' """.format(start_day, today)
    else:
        sql = """SELECT 
                follow_record.customer_id, follow_record.business_id, sys_user_info.login_name, follow_record.content, follow_record.followStart, follow_record.followEnd 
                FROM follow_record,sys_user_info 
                WHERE  
                follow_record.YtCode LIKE 'BUS_YT_DK%' AND 
                follow_record.login_name != 'dropper' AND 
                sys_user_info.id = follow_record.follow_id;"""

    # pwd = os.getcwd()
    # 当前文件的父路径
    # father_path = os.path.abspath(os.path.dirname(pwd) + os.path.sep + ".")
    # output_path = father_path + '/data/' + 'follow_record.csv'
    # output_path = data_path + 'follow_record.csv'

        #         sql = "SELECT * FROM `uc_customer_record` WHERE create_time>'%s 00:00:00' AND create_time<'%s 23:59:59' AND remark_type='BUS_3';" % (current_date, current_date)
    count = cs1.execute(sql)
    result = result + [list(column_set) for column_set in list(cs1.fetchall())]
    df = pd.DataFrame(result, columns=['customer_id', 'business_id', 'login_name', 'content', 'followStart', 'followEnd'])
    # df.to_csv(output_path, mode='a', header=False) 1.2G 太占用磁盘
    return df


def table_from_mysql(database_name):
    """
    从数据中心-杨世杰获取，已废弃，现在都从徐志强的数据仓获取历史记录
    Args:
        database_name:

    Returns:
        没有返回值，直接写入文件
    """
    orgin_date = datetime.datetime.strptime("2019-01-01 00:00", "%Y-%m-%d %H:%M")
    conn=readMysql().getConnectionFromDataCenter(database=database_name)
    cs1 = conn.cursor()
#     sql = "SELECT db_iboss2.uc_customer_record.table_id,db_iboss2.uc_customer_record.create_time,db_iboss2.sys_user_info.login_name,db_iboss2.sys_org_main.name,db_iboss2.cms_tree_book.name FROM db_iboss2.uc_customer_record,db_iboss2.sys_user_info,db_iboss2.sys_org_main,db_iboss2.cms_tree_book WHERE db_iboss2.uc_customer_record.remark_type = db_iboss2.cms_tree_book.code AND db_iboss2.cms_tree_book.name LIKE '%跟进%' AND db_iboss2.cms_tree_book.pcode = 'BUS_OPERATE' AND db_iboss2.sys_user_info.id = db_iboss2.uc_customer_record.from_user_id AND db_iboss2.sys_org_main.id = db_iboss2.sys_user_info.org_id AND db_iboss2.sys_org_main.name LIKE '%融资%' AND "
#     sql = sql + "db_iboss2.uc_customer_record.create_time > '2019-03-10 00:00:00' And db_iboss2.uc_customer_record.create_time < '2019-03-10 23:59:59';"
    result = []
    columns = ['table_id','create_time','creater_name','remark_type','content']
    for i in tqdm(range(100)):
        current_date = orgin_date+datetime.timedelta(days=i)
        current_date = current_date.strftime("%Y-%m-%d")
        print(current_date)
        # sql = "SELECT * FROM `uc_customer_record` WHERE create_time>'%s 00:00:00' AND create_time<'%s 23:59:59' AND remark_type='BUS_3';" % (current_date, current_date)
        sql = "SELECT db_iboss2.uc_customer_record.table_id,db_iboss2.uc_customer_record.create_time,db_iboss2.uc_customer_record.creater_name,db_iboss2.uc_customer_record.remark_type,db_iboss2.uc_customer_record.content FROM db_iboss2.uc_customer_record,(SELECT id,type_code FROM `bus_business` WHERE type_code LIKE 'BUS_YT_DK%') b WHERE db_iboss2.uc_customer_record.remark_type = 'BUS_3' AND b.type_code  LIKE 'BUS_YT_DK%' AND db_iboss2.uc_customer_record.table_id = b.id AND db_iboss2.uc_customer_record.create_time > '%s 00:00:00' AND db_iboss2.uc_customer_record.create_time < '%s 23:59:59';" % (current_date, current_date)
        count = cs1.execute(sql)
        result = result + [list(column_set) for column_set in list(cs1.fetchall())]
        if i % 100 == 0:
            df = pd.DataFrame(result, index=list(range(len(result))), columns=columns)
            print(df.shape)
            df.to_csv('跟进记录.csv', mode='a', header=False)
            del(df)
            result = []

    cs1.close()
    conn.close()
    print("查询到%d条数据:" % len(result))


if __name__ == '__main__':
    df = follow_drop_record(read_day=90)
    df = df.groupby(['business_id','login_name']).agg({'customer_id':lambda x:x.iloc[0]}).reset_index()
    print(df.info())
    print(df.shape)
    print(df.head())