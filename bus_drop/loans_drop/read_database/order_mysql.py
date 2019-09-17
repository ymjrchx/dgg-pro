#!/usr/bin/env python
# encoding: utf-8
'''
@author: 罗成
@contact: luocheng@dgg.net
@file: order_mysql.py
@time: 2019-05-06 09:22
@desc: 重构孙小龙用于读取历史订单的代码,将直接连接改为连接池的方式读取
'''
import pandas as pd
from loans_drop.superclass.read_mysql import Read_From_MySQL as readMysql


def read_order_from_mysql(database = None, columns = None):
    db = readMysql().getConnectionFromPool(database = database)
    cursor = db.cursor()
    sql = "select id,order_id,business_id,business_no,place_order_time,customer_id,user_no,business_user_name,\
            organization_name,sign_org_name,business_type_name,bt_name,status,product_amount\
              from bi_order_detail where business_type_name like '%贷%'"
    # 商机创建时间 要利用商机id，关联bus_business表
    try:
        cursor.execute(sql)
        results = cursor.fetchall()
        results_list = []

        for row in results:
            zipped = zip(columns, row)
            dic = {}
            for zips in zipped:
                dic[zips[0]] = zips[1]
                results_list.append(dic)

    except:
        print("Error")
    finally:
        cursor.close()
        db.close()
        return results_list

if __name__ == '__main__':
    columns = ["id", "order_id", "business_id", "business_no", "place_order_time", "customer_id",
               "user_no", "business_user_name", "organization_name", "sign_org_name",
               "business_type_name", "bt_name", "status", "product_amount"]
    result = read_order_from_mysql(database = 'bi_behavior', columns = columns)
    print(len(result))

    data = pd.DataFrame(data=result, columns=columns)
    data = data.drop_duplicates()
    print(data.shape)
    print(data.head())