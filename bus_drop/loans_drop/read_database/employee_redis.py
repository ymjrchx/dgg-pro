#!/usr/bin/env python
# encoding: utf-8
'''
@author: 罗成
@contact: luocheng@dgg.net
@file: employee_redis.py
@time: 2019-05-16 16:26
@desc:采用单例模式设计Redis工具类，防止推荐系统创建这个类多次
'''
from rediscluster import StrictRedisCluster
import json
from loans_drop.config.database import redis_hosts


def Singleton(cls):
    _instance = {}

    def _singleton(*args, **kargs):
        if cls not in _instance:
            _instance[cls] = cls(*args, **kargs)
        return _instance[cls]

    return _singleton

@Singleton
class RedisTool(object):
    num_of_instance = 0 #类变量

    def __init__(self):
        self.num_of_instance += 1 #实例变量，用于测试模式单例是否生效，多个实例的值均为1，则单例生效
        self.employees = []

    def get_employee_list(self):
        """
        获取待推荐的商务列表
        :return:
        RedisTool().get_employee_list().employees 获取数据库中最新的商务列表
        RedisTool().employees 获取缓存在内存的商务列表
        """
        # 创建redis链接对象 - 这里取第一个链接241
        rc = StrictRedisCluster(startup_nodes=redis_hosts, decode_responses=True)
        # 存储键值对
        # r.set('site','www.qi.cn')
        # 获取值
        result = rc.get('busralloc:sync:BUS_SOR_PLACE_CD:BUS_YT_DK')
        result = '{"r":' + result + '}'
        result = json.loads(result)
        result = result['r']
        # print(result)
        # print(type(result))
        # print(type(result[0]))
        self.employees = result
        return self



if __name__ == '__main__':
    print(RedisTool().get_employee_list().employees)