#!/usr/bin/env python
# encoding: utf-8
'''
@author: 罗成
@contact: luocheng@dgg.net
@file: read_mongo.py
@time: 2019-05-06 08:58
@desc:
'''
import pymongo
from loans_drop.config.database import *


class Read_From_Mongo(object):
    def __init__(self):
        self.table_name = ''

    def read_from_mongo(self, table_name=None, column_list=None, ignore_column_list = None,filter_dict=None):
        client = pymongo.MongoClient(host=mongo_host, port=mongo_port)

        db = client[mongo_database]
        if mongo_user != None and mongo_psw != None:
            db.authenticate(mongo_user, mongo_psw)
        collection = db[table_name]  # 商务数据
        if filter_dict:
            if column_list:
                print('此处过滤列名')
                results = collection.find(filter_dict, {column:True for column in column_list})
            elif ignore_column_list:
                results = collection.find(filter_dict, {column: False for column in ignore_column_list})
            else:
                results = collection.find(filter_dict,{'_id':False,'_class':False})
        else:
            if column_list:

                results = collection.find({}, {column:True for column in column_list})
            elif ignore_column_list:
                results = collection.find(filter_dict, {column: False for column in ignore_column_list})
            else:
                results = collection.find({},{'_id':False,'_class':False})

        return results