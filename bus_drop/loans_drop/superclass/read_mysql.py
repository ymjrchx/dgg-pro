#!/usr/bin/env python
# encoding: utf-8
'''
@author: 罗成
@contact: luocheng@dgg.net
@file: read_mysql.py
@time: 2019-05-06 09:35
@desc: 基类，用于从mysql中读取数据
'''
from loans_drop.config.database import *
import pymysql
from DBUtils.PooledDB import PooledDB
from loans_drop.config.c_define import *


class Read_From_MySQL(object):
    def __init__(self, type = 0):
        self.type = type
        print_to_log('初始化connection')

    def getConnectionFromDataCenter(self, database = None):
        """
        杨世杰：从连接池获取连接对象，该方法是从DBUtils直接获取连接池，以后可能更换连接池
        Args:
            database: 数据库名字, 如'bi_behavior'

        Returns:
            connection对象
        """
        # 3为连接池里的最少连接数，setsession=['SET AUTOCOMMIT = 1']是用来设置线程池是否打开自动更新的配置，0为False，1为True
        pool = PooledDB(pymysql, 3, host=DataCenter_host, user=DataCenter_user, passwd=DataCenter_psw,
                        db=database, port=DataCenter_port, setsession=['SET AUTOCOMMIT = 1'])
        conn = pool.connection()  # 以后每次需要数据库连接就是用connection（）函数获取连接就好了
        return conn


    def getConnectionFromDataWarehousing(self, database = None):
        """
        徐志强：从连接池获取连接对象，该方法是从DBUtils直接获取连接池，以后可能更换连接池
        Args:
            database: 数据库名字, 如'bi_behavior'

        Returns:
            connection对象
        """
        # 3为连接池里的最少连接数，setsession=['SET AUTOCOMMIT = 1']是用来设置线程池是否打开自动更新的配置，0为False，1为True
        pool = PooledDB(pymysql, 3, host=DataWarehousing_host,
                        user=DataWarehousing_user,
                        passwd=DataWarehousing_psw,
                        db=database,
                        port=DataWarehousing_port,
                        setsession=['SET AUTOCOMMIT = 1'])
        conn = pool.connection()  # 以后每次需要数据库连接就是用connection（）函数获取连接就好了
        return conn


    def column_from_mysql(self, database_name, table_name):
        """
        获取数据库的特征列表
        Args:
            database_name: 数据库名字
            table_name: 数据表名字

        Returns:
            list = ['特征1',['特征2']。。。。]
        """

        if self.type == 0:
            conn = self.getConnectionFromDataWarehousing(database=database_name)
        elif self.type == 1:
            conn = self.getConnectionFromDataCenter(database=database_name)
        else:
            raise Exception('目前只支持以上数据仓库',self.type)
        cs1 = conn.cursor()

        count2 = cs1.execute('SHOW FULL FIELDS FROM %s' % table_name)
        result2 = list(cs1.fetchall())
        columns = [column_set[0] for column_set in result2]

        cs1.close()
        conn.close()
        print_to_log("查询到%d条数据:" % count2)
        return columns