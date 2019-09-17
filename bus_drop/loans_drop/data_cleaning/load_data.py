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
import pandas as pd
import re
import json
import numpy as np
sys.path.append(os.environ['PUSHPATH'])

from loans_drop.config.path import data_path
from loans_drop.read_database.business_MongoDB import get_customer_feature
from loans_drop.read_database.employee_MongoDB import get_employee_feature
from loans_drop.read_database.order_mongo import read_order_data
from loans_drop.read_database.lifecycle_record import follow_drop_record
from loans_drop.read_database.call_record import get_call_record
from loans_drop.read_database.kafka_producer import Producer
from loans_drop.config.database import topic_in
from loans_drop.offline.off_line_modeling import train_model
from loans_drop.config.c_define import *
from util.timer.sched import SchduleTimer


# @pysnooper.snoop('content.log')
def Singleton(cls):
    _instance = {}

    def _singleton(*args, **kargs):
        if cls not in _instance:
            _instance[cls] = cls(*args, **kargs)
        return _instance[cls]

    return _singleton


@Singleton
class DataLoader(object):
    def __init__(self):
        self.total_data = None
        self.employee_df = None
        self.business_df = None
        today_date = datetime.datetime.now()
        self.date = today_date.strftime('%Y%m%d')
        # self.date = 20190704
        self.out_path = data_path + 'total_data{}.csv'.format(self.date)

    def set_salary_score(self, df):
        """
        从备注记录中读取工资信息
        Args:
            df: dataframe,传递合并之前的时间消耗较少，这里传入已经合并后的跟进记录

        Returns:
            处理完成后的dataframe
        """
        df['salary_score'] = 0
        for i in range(len(df)):
            line = df.iloc[i]['content']
            if isinstance(line, float) or isinstance(line, int):
                df.at[i, 'content'] = ''
                continue
            line = line.replace('几千', '3001').replace('几万', '30001').replace('几十', '31').replace('几百', '301').replace(
                '多',
                '')
            line = line.replace('一', '1').replace('两', '2').replace('二', '2').replace('三', '3').replace('四',
                                                                                                        '4').replace(
                '五', '5').replace('六', '6').replace('七', '7').replace('八', '8').replace('九', '9')
            line = line.replace('百', '00').replace('千', '000').replace('万', '0000').replace('员工资源', '资源')

            ret = re.findall(r"\d+", line)
            if len(ret):
                # 匹配小数
                ret = re.findall(r"\d+\.\d+", line)
                for result in ret:
                    line = line.replace(str(result), str(result).split('.')[0])

                ret = re.findall(r"\d+-\d+", line)
                for result in ret:
                    line = line.replace(result, result.split('-')[-1])

                ret = re.findall(r".{0,10}\d+[万|千|w|W|K|k]?", line)  # 税后工资七八千 流水一个月一两百万
                for result in ret:
                    sub_ret = re.findall(r"代发|流水|发现金|税后|收入|打卡|工资", result)

                    if len(sub_ret) > 0:
                        sub_ret = re.findall(r"\d{1,3}多?[w|W|K|k]|\d{1,3}-\d{1,3}多?[w|W|K|k]", result) + re.findall(
                            r"\d+-\d+", result) + re.findall(r"\d+", result)
                        if len(sub_ret) > 0:
                            salary = sub_ret[0].split('-')[-1].replace('w', '0000').replace('k', '000').replace('W',
                                                                                                                '0000').replace(
                                'K', '000')

                            df.at[i, 'salary_score'] = salary
                            # print_to_log(ret)
                            # print_to_log('工资:', salary)
                            break
        return df

    def pull_total_data(self):
        """
        获取待推荐的商务列表
        :return:
        DataLoader().pull_total_data.total_data 获取数据库中数据,重新运行合并流程
        DataLoader().total_data 获取缓存在内存的数据
        """
        # TODO:读取跟进记录
        print_to_log('=========开始读取跟进记录===========')
        allocation_df = follow_drop_record(read_day=60, today=datetime.datetime.now()-datetime.timedelta(1))
        print_to_log(allocation_df.shape)
        allocation_df.dropna(subset=['login_name', 'customer_id'], inplace=True)  # 缺失跟进日期的记录为3816581-2366629条数据
        allocation_df = allocation_df[['login_name', 'customer_id', 'followStart', 'followEnd', 'content']]
        print_to_log('After drop NA:', allocation_df.shape)
        # 跟进记录构造新特征
        allocation_df['follow_days'] = pd.to_datetime(allocation_df["followEnd"]) - pd.to_datetime(
            allocation_df["followStart"])
        allocation_df['follow_hour'] = pd.to_datetime(allocation_df['follow_days']).dt.hour
        allocation_df['follow_minute'] = pd.to_datetime(allocation_df['follow_days']).dt.minute
        allocation_df['follow_days'] = pd.to_datetime(allocation_df['follow_days']).dt.day
        allocation_df = allocation_df.groupby(['login_name', 'customer_id'])\
            .agg({'follow_days': np.sum, 'follow_hour': np.mean, 'content': np.sum})\
            .reset_index()
        allocation_df['follow_days'] = allocation_df['follow_days'] + allocation_df[
            'follow_hour'] / 8  # 每天工作8小时
        allocation_df = allocation_df[['login_name', 'customer_id', 'follow_days', 'content']]
        print_to_log('After groupby:', allocation_df.shape)
        print(allocation_df.head())


        # TODO:合并跟进记录和通话记录
        merge_df = allocation_df
        del allocation_df

        # 提取工资信息
        print_to_log("开始提取工资信息")
        self.set_salary_score(merge_df)

        # TODO:读取所有订单记录
        order_df = read_order_data()
        # 防止customer_id变成customer_idx和customer_idy
        order_df = order_df[['login_name', 'customer_id', 'product_amount']]
        order_df.dropna(subset=['product_amount'], inplace=True)
        print_to_log('After drop NA:', order_df.shape)
        order_df.drop_duplicates(subset=['login_name', 'customer_id'], inplace=True)
        print_to_log('After drop_duplicates:', order_df.shape)

        # TODO:合并历史订单到merge_df
        print_to_log("========合并历史订单记录========")
        print_to_log(merge_df.shape, order_df.shape)
        merge_df = pd.merge(order_df, merge_df, on=['customer_id', 'login_name'], how='outer')
        print_to_log(merge_df.shape)
        merge_df.drop_duplicates(subset=['customer_id', 'login_name'], inplace=True)
        print_to_log(merge_df.shape)
        print_to_log(merge_df.info())
        print(merge_df.head())
        del order_df

        # TODO:读取客户数据 没有business_id只有customer_id
        business_df = get_customer_feature(self.date)
        # 删除'businessOperate' == BUS_4 出现在跟进记录中的样本，表示已经签单 将该值的负样本删除，正样本改为其他值
        business_df['customer_id'] = business_df['customer_id'].astype('int')
        print_to_log(business_df.info())
        print_to_log(business_df.shape)
        print(business_df.head())

        # TODO:读取商务数据
        employee_df = get_employee_feature(self.date)
        print_to_log(employee_df.info())
        print_to_log(employee_df.shape)
        print(employee_df.head())

        # 合并客户数据
        print_to_log("开始合并employee_df")
        print_to_log('Before merge:', employee_df.shape, merge_df.shape)
        employee_df.dropna(subset=['login_name'], inplace=True)
        merge_df.dropna(subset=['login_name'], inplace=True)
        employee_df['login_name'] = employee_df['login_name'].astype(object)
        merge_df['login_name'] = merge_df['login_name'].astype(object)
        merge_df = pd.merge(merge_df, employee_df, on=['login_name'], how='inner')
        print_to_log('After merge:', merge_df.shape)

        print_to_log("开始合并business_df")
        print_to_log('Before merge:', business_df.shape, merge_df.shape)
        business_df.dropna(subset=['customer_id'], inplace=True)
        merge_df.dropna(subset=['customer_id'], inplace=True)
        business_df['customer_id'] = business_df['customer_id'].astype('int')
        merge_df['customer_id'] = merge_df['customer_id'].astype('int')
        print(business_df.head())
        print_to_log(merge_df.info())
        merge_df = pd.merge(merge_df, business_df, on=['customer_id'], how='inner')
        merge_df.to_csv(self.out_path, encoding='utf-8', index=0, sep=',')
        print_to_log("total_data.csv path:", self.out_path)
        print_to_log("Load Data is Ending")

        if len(merge_df) > 0: #数据拉去失败，将使用前一天的数据
            self.total_data = merge_df
        if len(employee_df) > 0:
            self.employee_df = employee_df
        if len(business_df) > 0:
            self.business_df = business_df
        # return merge_df, employee_df, business_df


def run():
    producer = Producer(topic=topic_in)
    result_dic = {"customerId": 'update_data'}
    loader = DataLoader()
    # 刷新load日期
    loader.date = (datetime.datetime.now()-datetime.timedelta(1)).strftime('%Y%m%d')
    loader.out_path = data_path + 'total_data{}.csv'.format(loader.date)
    try:
        loader.pull_total_data()
        print_to_log(loader.employee_df.shape)
        print_to_log(loader.business_df.shape)
        print_to_log(loader.total_data.shape)
        try:
            train_model(loader.total_data, loader.business_df, loader.employee_df)
            producer.sendjsondata(json.dumps(result_dic))
            print_to_log('发送数据更新请求')
        except Exception as e:
            print_to_log('Train error: ', e, level=4)
            print_to_log('result_dic:', result_dic)
    except Exception as e:
        print_to_log('Load Data Error: ', e, level=4)
        print_to_log('result_dic:', result_dic)


if __name__ == '__main__':
    run()
    sc = SchduleTimer(hour='04:00')
    sc.main(run, 30)