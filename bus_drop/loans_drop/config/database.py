#!/usr/bin/env python
# encoding: utf-8
'''
@author: 罗成
@contact: luocheng@dgg.net
@file: database.py
@time: 2019-05-05 17:07
@desc: 用于存储数据常用的常量
'''
import sys
import os
sys.path.append(os.environ['PUSHPATH'])
from loans_drop.config.map import environment


def Singleton(cls):
    _instance = {}

    def _singleton(*args, **kargs):
        if cls not in _instance:
            _instance[cls] = cls(*args, **kargs)
        return _instance[cls]

    return _singleton

@Singleton
class DatabaseTool(object):
    def __init__(self):
        """徐志强、郭芳整理的数据仓---产线环境"""
        self.mongo_host = '172.16.0.25' if environment == 'DEBUG' else '10.2.1.111'
        self.mongo_port = 17017 if environment == 'DEBUG' else 17017
        self.mongo_database = 'warehouse' if environment == 'DEBUG' else 'warehouse'
        self.mongo_user = None if environment == 'DEBUG' else 'sjznts'
        self.mongo_psw = None if environment == 'DEBUG' else '16IvgbuHTM'

        """潘进维护的MySQL"""
        self.DataWarehousing_host = '172.16.0.205' if environment == 'DEBUG' else '10.2.1.110'
        self.DataWarehousing_port = 3306 if environment == 'DEBUG' else 5506
        self.DataWarehousing_user = 'root' if environment == 'DEBUG' else 'db_data_warehouse'
        self.DataWarehousing_psw = 'root123456' if environment == 'DEBUG' else 'y0HTAw7S34d5jMt7'

        """数据中心---杨世杰---产线环境"""
        self.DataCenter_host = None
        self.DataCenter_port = None
        self.DataCenter_user = None
        self.DataCenter_psw = None

        kafka1 = ['192.168.254.80:9091', '192.168.254.80:9092', '192.168.254.80:9093']
        kafka2 = ['10.0.0.111:9092', '10.0.0.112:9092', '10.0.0.113:9092']
        self.kafka_servers = kafka1 if environment == 'DEBUG' else kafka2

        redis1 = [{'host': '192.168.254.117', 'port': 7001},
                  {'host': '192.168.254.117', 'port': 7002},
                  {'host': '192.168.254.117', 'port': 7003},
                  {'host': '192.168.254.118', 'port': 7004},
                  {'host': '192.168.254.118', 'port': 7005},
                  {'host': '192.168.254.118', 'port': 7006}]

        redis2 = [{'host':'10.0.0.114', 'port': 7001},
                  {'host': '10.0.0.114', 'port': 7002},
                  {'host': '10.0.0.114', 'port': 7003},
                  {'host': '10.0.0.115', 'port': 7004},
                  {'host': '10.0.0.115', 'port': 7005},
                  {'host': '10.0.0.115', 'port': 7006}]
        self.redis_hosts = redis1 if environment == 'DEBUG' else redis2


mongo_host = DatabaseTool().mongo_host
mongo_port = DatabaseTool().mongo_port
mongo_database = DatabaseTool().mongo_database
mongo_user = DatabaseTool().mongo_user
mongo_psw = DatabaseTool().mongo_psw

DataWarehousing_host = DatabaseTool().DataWarehousing_host
DataWarehousing_port = DatabaseTool().DataWarehousing_port
DataWarehousing_user = DatabaseTool().DataWarehousing_user
DataWarehousing_psw = DatabaseTool().DataWarehousing_psw

"""数据中心---杨世杰---产线环境"""
DataCenter_host = "10.2.1.170"
DataCenter_port = 5506
DataCenter_user = "ai_account"
DataCenter_psw = "qvufj2nj"

"""kafka地址"""
kafka_servers = DatabaseTool().kafka_servers

"""kafka商机入topic"""
topic_in = 'resourcealloc_smart'
"""kafka商机出topic"""
topic_out = 'resourcealloc_result'
topic_temp = 'temp_out'


"""Redis测试地址"""
redis_hosts = DatabaseTool().redis_hosts


"""Redis里商务队列"""
# redis：busralloc:sync:{areaCode}:{businessType}   areaCode为商机所属区域，businessType为商机所属业态

if __name__ == '__main__':
    print()
    print(DatabaseTool().mongo_host)