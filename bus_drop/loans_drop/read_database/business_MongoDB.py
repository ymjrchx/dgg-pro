#!/usr/bin/env python
# encoding: utf-8
'''
@author: 罗成
@contact: luocheng@dgg.net
@file: business_MongoDB.py
@time: 2019-05-05 16:56
@desc:
'''
import pymongo
import numpy as np
import pandas as pd
from loans_drop.superclass.read_mongo import Read_From_Mongo
from loans_drop.config.path import data_path
from loans_drop.config.c_define import *


pd.set_option('display.width', 1000)
# 显示所有列
pd.set_option('display.max_columns', 1000)
# 显示所有行
pd.set_option('display.max_rows', None)
# 设置value的显示长度为100，默认为50
pd.set_option('max_colwidth', 1000)

# today = (datetime.datetime.now()-datetime.timedelta(1)).strftime('%Y%m%d')


class Read_business(Read_From_Mongo):
    def __init__(self):
        super(Read_business, self).__init__()
        self.table_name = ''

    def read_from_mongo(self, table_name=None, column_list=None, ignore_column_list = None,filters=None):
        # 父类方法不满足时，再去重写父类方法
        return super(Read_business, self).read_from_mongo(table_name=table_name, column_list=column_list,ignore_column_list = ignore_column_list,
                                                          filter_dict=filters)


def read_dgg_customer(day_date):
    """
    dgg_customer
    userId	客户数据的mysqlid
    userNo	客户编号
    orderCount	订单数量	一段时间内(最近两年)客户融资订单的总数量
    orderAmount	订单金额  	 一段时间内(最近两年)客户融资订单的总金额  单位：元
    hasLoan	是否有贷款	一段时间内(最近两年)客户是否有成功贷款记录    1表示是，0表示否	根据orderCount，有值便是有贷款记录
    latestLoanMonthDiff	 最近一次贷款时间距离现在的时间长度,单位是月
    addWechat	 客户是否添加商务微信 1表示是，0表示否		无法获取数据
    meetingCount		一段时间内(最近2年)客户与商务见面会谈次数总和  单位：次
    containLoanOrFund		本次借款客户备注留言是否包含“贷款”或“资金”字样   1表示是，0表示否    注意：指距离现在最近一次的备注且时长不超过90天
    remarkGTenWords	客户备注是否10字以上	注意：指距离现在最近一次的备注且时长不超过90天
    beKickedOutCount	被踢出次数	一段时间(最近180天)内客户被商务从名单库移除的次数
    complaintCount	投诉次数	一段时间(最近180天)内客户投诉公司的次数  	无法获取数据
    """
    read = Read_business()
    ignore_column_list = ['userNo','_id','_class','statisticDay']
    return read.read_from_mongo(table_name='dgg_customer', filters={'statisticDay': int(day_date)}, ignore_column_list=ignore_column_list)  # 读取客户评级属性


def read_customer_tccs_entity(day_date):
    """
    customer_tccs_entity
    customer_id	客户id
    tccs	剔除次数
    statistic_day	统计日期
    """
    read = Read_business()
    ignore_column_list = ['statistic_day', '_id', '_class']

    return read.read_from_mongo(table_name='customer_tccs_entity', filters={'statistic_day': int(day_date)},ignore_column_list=ignore_column_list)  # 读取客户评级属性


def read_last_ninety_customer_data():
    """
    last_ninety_customer_data
    customerId	客户id
    flagContent		本次借款客户备注留言是否包含“贷款”或“资金”字样   1表示是，0表示否    注意：指距离现在最近一次的备注且时长不超过90天
    flagRemark	客户备注是否10字以上	注意：指距离现在最近一次的备注且时长不超过90天
    """
    read = Read_business()
    return read.read_from_mongo(table_name='last_ninety_customer_data')  # 读取客户评级属性


def read_last_loan_time_data(day_date):
    """
    last_loan_time_data
    customerNo	客户编号
    mon	最近一次贷款时间距离现在的时间长度,单位是月
    statisticDay	统计日期
    """
    read = Read_business()
    ignore_column_list = ['statistic_day', '_id', '_class', 'customerNo', 'statisticDay']
    return read.read_from_mongo(table_name='last_loan_time_data', filters={'statisticDay': int(day_date)},ignore_column_list=ignore_column_list)  # 读取客户评级属性


def read_last_remark_type_customer_data():
    """
    last_remark_type_customer_data		最近90天客户备注类型相关数据
    customerId	客户id
    remarkType	类型
    times	次数
    aggMon	计算月份
    """
    read = Read_business()
    ignore_column_list = ['aggMon', '_id', '_class']
    return read.read_from_mongo(table_name='last_remark_type_customer_data',ignore_column_list=ignore_column_list)  # 读取客户评级属性


def read_last_bus_business():
    """
    id	主键id	关联bus_business_all的business_id
    customerId	客户id
    customerNo	客户编号
    no	商机编号
    description	商机备注
    followTimes	商机跟进次数
    businessStatus	商机状态
    businessOperate	当前状态
    businessLocation	商机库
    wayCode	商机获取方式
    opportunitytypeCode	商机类型
    extensionCode	推广渠道
    originCode	来源渠道
    originType	渠道对应的表单类型
    lastFollowTime	跟进人最后一次跟进时间
    lastLoseTime	最新掉库时间
    lastLoseType	最新掉库方式
    isCost	是否技术成本
    isMind	是否意向资源,0否，1是
    invalidNumber	返无效次数
    allotNumber	分配次数
    addTypeCode	新建类型
    referral	是否转介绍
    vip	是否vip
    lastFollowRecordContent	最后一次备注记录内容
    businessStage	商机当前阶段
    widelyType	外部来源类型
    uploadType	上传类型
    placeCode	区域代码
    """
    read = Read_business()
    ignore_column_list = ['_id','_class','description','lastFollowRecordContent','no','lastFollowTime','lastLoseTime','customerNo']
    return read.read_from_mongo(table_name='last_bus_business',ignore_column_list = ignore_column_list)  # 读取客户评级属性


def read_customer_statistics_ml(day_date):
    """
    customer_statistics_ml		客户数据统计（绿色字段）
    userId	用户id
    userNo	用户编号
    statisticDay	统计日期       格式：yyyyMMdd	根据日期去数据
    coverCharge	历史总服务费用
    refundOrderSign	是非退单  0为未退单，1为退单
    signOderNum	 历史签单数量
    oderStatistics	融资成单的业务类型，每种类型计数	集合   key为业态   value为计数
    """
    read = Read_business()
    ignore_column_list = ['_id', '_class', 'userNo', 'statisticDay', 'oderStatistics']
    return read.read_from_mongo(table_name='customer_statistics_ml', filters={'statisticDay': int(day_date)},ignore_column_list=ignore_column_list)  # 读取客户评级属性


def read_dgg_customer_call(day_date):
    """
    dgg_customer_call		商务通话记录（最近24个月）
    userNo	商务编号
    userId	商务ID
    callTimes	通话次数
    avgCallDuration	平均通话时长
    """
    read = Read_business()
    ignore_column_list = ['userNo','_class','_id','statisticDay','insertTime']
    return read.read_from_mongo(table_name='dgg_customer_call', filters={'statisticDay': int(day_date)},ignore_column_list=ignore_column_list)  # 读取客户评级属性


def get_customer_feature(statistic_day):
    print_to_log('Start Reading Customer')
    try:
        results = list(read_dgg_customer(statistic_day))  # pyMongoDB.curso.curso强转为list
        print_to_log('read_dgg_customer总共有%d条商机数据' % len(results))
        df1 = pd.DataFrame.from_records(results)
        print_to_log(df1.shape)
        print_to_log(df1.info())
        print_to_log(df1.columns)
        df1.rename(columns={'userId': 'customer_id'}, inplace=True)
        df1 = df1[['customer_id', 'meetingCount', 'orderAmount', 'orderCount']]
        df1.dropna(subset=['customer_id'], inplace=True)
        df1['customer_id'] = df1['customer_id'].astype('int')
        df1 = df1.groupby('customer_id') \
            .agg({'orderAmount': np.sum, 'meetingCount': np.sum, 'orderCount': np.sum}) \
            .reset_index()
    except Exception as e:
        print_to_log(e, level=4)

    try:
        print_to_log()
        results = list(read_customer_tccs_entity(statistic_day))
        print_to_log('read_customer_tccs_entity总共有%d条商机数据' % len(results))
        df2 = pd.DataFrame(results)
        print_to_log(df2.shape)
        print_to_log(df2.info())
        print_to_log(df2.columns)
        df2['customer_id'] = df2['customer_id'].astype('int')
        df2 = df2[['customer_id', 'tccs']]
        df2.dropna(inplace=True)
        df2 = df2.groupby('customer_id').agg({'tccs': np.sum}) \
            .reset_index()
    except Exception as e:
        print_to_log(e, level=4)

    try:
        print_to_log()
        results = list(read_last_ninety_customer_data())
        print_to_log('read_last_ninety_customer_data总共有%d条商机数据' % len(results))
        df3 = pd.DataFrame(results)
        print_to_log(df3.shape)
        print_to_log(df3.info())
        print_to_log(df3.columns)
        df3.rename(columns={'customerId': 'customer_id'}, inplace=True)
        df3['customer_id'] = df3['customer_id'].astype('int')
        df3 = df3[['customer_id', 'flagContent', 'flagRemark']]
        # 添加groupby并求比例的需求
        df3 = df3.groupby('customer_id').agg({'flagContent': np.mean, 'flagRemark': np.mean}) \
            .reset_index()
    except Exception as e:
        print_to_log(e, level=4)

    try:
        print_to_log()
        results = list(read_last_loan_time_data(statistic_day))
        print_to_log('read_last_loan_time_data总共有%d条商机数据' % len(results))
        df4 = pd.DataFrame(results)
        print_to_log(df4.shape)
        print_to_log(df4.info())
        print_to_log(df4.columns)
        df4.rename(columns={'customerId': 'customer_id'}, inplace=True)
        df4 = df4[['customer_id', 'mon']]
        df4.dropna(inplace=True)
        df4['customer_id'] = df4['customer_id'].astype('int')
        df4 = df4.groupby('customer_id').agg({'mon': np.max}) \
            .reset_index()
    except Exception as e:
        print_to_log(e, level=4)

    try:
        print_to_log()
        results = list(read_last_remark_type_customer_data())
        print_to_log('read_last_remark_type_customer_data有%d条商机数据' % len(results))
        df5 = pd.DataFrame(results)
        print_to_log(df5.shape)
        print_to_log(df5.info())
        print_to_log(df5.columns)
        df5.rename(columns={'customerId': 'customer_id'}, inplace=True)
        # df5 = df5[['customer_id', 'remarkType', 'times', 'aggMon']]
        df5['customer_id'] = df5['customer_id'].astype('int')
        df5 = df5.groupby('customer_id').agg({'remarkType': pd.Series.mode, 'times': np.sum}) \
            .reset_index()
    except Exception as e:
        print_to_log(e, level=4)

    try:
        print_to_log()
        results = list(read_last_bus_business())
        print_to_log('read_last_bus_business有%d条商机数据' % len(results))
        df6 = pd.DataFrame(results)
        print_to_log(df6.shape)
        print_to_log(df6.info())
        print_to_log(df6.columns)
        df6.rename(columns={'customerId': 'customer_id'}, inplace=True)
        df6['customer_id'] = df6['customer_id'].astype('int')
        df6.drop_duplicates('customer_id',keep='last',inplace=True)
        # no	商机编号 没有重复值
    except Exception as e:
        print_to_log(e, level=4)

    try:
        print_to_log()
        results = list(read_customer_statistics_ml(statistic_day))
        print_to_log('read_customer_statistics_ml有%d条商机数据' % len(results))
        df7 = pd.DataFrame(results)
        print_to_log(df7.shape)
        print_to_log(df7.info())
        print_to_log(df7.columns)
        df7.rename(columns={'userId': 'customer_id'}, inplace=True)
        # df7 = df7[['customer_id', 'mon', 'remarkType', 'times', 'aggMon']]
        df7['customer_id'] = df7['customer_id'].astype('int')
        df7 = df7.groupby('customer_id').agg({'coverCharge': np.sum, 'refundOrderSign': np.mean,
                                              'signOderNum': np.sum}).reset_index()
    except Exception as e:
        print_to_log(e, level=4)

    try:
        print_to_log()
        results = list(read_dgg_customer_call(statistic_day))
        print_to_log('read_dgg_customer_call总共有%d条商务数据' % len(results))
        df8 = pd.DataFrame(results)
        print_to_log(df8.shape)
        print_to_log(df8.info())
        print_to_log(df8.columns)
        df8.rename(columns={'userId': 'customer_id'}, inplace=True)
        df8['customer_id'] = df8['customer_id'].astype('int')
        df8 = df8[['customer_id', 'callTimes', 'avgCallDuration']]
        df8 = df8.groupby('customer_id').agg({'callTimes': np.sum, 'avgCallDuration': np.mean}) \
            .reset_index()
        # df2.dropna(inplace=True)
    except Exception as e:
        print_to_log(e, level=4)

    try:
        print_to_log('合并前各分表数据', df1.shape, df2.shape, df3.shape, df4.shape, df5.shape, df6.shape, df7.shape, df8.shape)
        df = df1
        df_list = [df1, df2, df3, df4, df5, df6, df7, df8]
        for i in range(1, len(df_list)):
            df2 = df_list[i]
            print_to_log(i, len(df))
            if len(df2) > 0:
                df = pd.merge(df, df2, on='customer_id', how='outer')
            else:
                print_to_log('某一张表没有数据 i=%d, len(df)=%d, len(df2)=%d' % (i, len(df), len(df2)))
                df = (df if (len(df) > len(df2)) else df2)
                continue
    except Exception as e:
        print_to_log(e, level=4)


    print_to_log(df.info())
    df.drop_duplicates(subset=['customer_id'], inplace=True)
    print_to_log(df.shape)
    print_to_log('End reading customer')
    df.to_csv(data_path+'business_df.csv', encoding='utf-8', index=0, sep=',')
    return df


if __name__ == '__main__':
    today_date = (datetime.datetime.now()-datetime.timedelta(1)).strftime('%Y%m%d')

    df = get_customer_feature(today_date)
    print_to_log(df.shape)
    print_to_log(df.info())
    print_to_log(df.head())
