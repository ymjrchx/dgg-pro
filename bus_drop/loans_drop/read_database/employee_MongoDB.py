#!/usr/bin/env python
# encoding: utf-8
'''
@author: 罗成
@contact: luocheng@dgg.net
@file: employee_MongoDB.py
@time: 2019-05-05 16:54
@desc:
'''
import pymongo
from loans_drop.superclass.read_mongo import Read_From_Mongo
import pandas as pd
from loans_drop.config.path import data_path
from loans_drop.config.c_define import *
from loans_drop.data_cleaning.decode_employee import Decoder
import re
import numpy as np


pd.set_option('display.width', 1000)
# 显示所有列
pd.set_option('display.max_columns', 1000)
# 显示所有行
pd.set_option('display.max_rows', None)
# 设置value的显示长度为100，默认为50
pd.set_option('max_colwidth', 1000)


# yestoday = datetime.datetime.now() - datetime.timedelta(1)
# yestoday = yestoday.strftime('%Y%m%d')

class Read_Employee(Read_From_Mongo):
    def __init__(self):
        super(Read_Employee, self).__init__()

    def read_from_mongo(self,table_name=None, column_list=None, ignore_column_list = None,filters=None):
        # 父类方法不满足时，再去重写父类方法
        return super(Read_Employee, self).read_from_mongo(table_name=table_name, column_list=column_list, filter_dict=filters,ignore_column_list=ignore_column_list)


def read_dgg_business_people(day_date):
    """
    userId	商务数据的mysqlid
    userNo	商务编号
    statisticDay	统计日期yyyyMMdd	根据统计日期查询
    entryDate	入职日期
    realName	真实姓名
    areaCode	地区编码	多个地区编码用|分隔
    businessType	业态编码
    signBillRate	签单率 	最近90天商务签单量/最近90天商务跟进的资源个数
    loanRate	放款率	最近90天商务客户成功借款的笔数/最近90天商务签单量
    performanceRank	日均业绩排名	 一段时间内(最近30天)同一业态的商务平均每天业绩额按从大到小排序后所占的秩    业绩额最大的秩为1，第二大的秩为2，依次类推
    toDoorRate	资源上门率	一段时间(最近30天)内商务跟进的资源中，与客户见面会谈的资源个数所占比例
    avgToDoorCount	 日均上门量	一段时间(最近30天)内商务跟进平均每天与客户见面的次数
    avgAddWechatFriendCount	 日均新增微信好友数	一段时间内(最近30天)商务平均每天新增微信好友的数量
    avgCallCount	 日均电话量	一段时间(最近30天)内商务平均每天接通客户电话的数量   接通包括主叫与被叫
    avgWorkHour	平均工时 	一段时间(最近30天)内商务平均每天工作时长  单位：小时 以上下班打卡时间为准
    avgSignBillDays	平均签单周期	 一段时间内(最近30天)商务完成签订合同的资源中，从接收到资源到系统下单所需的平均时长  单位：天
    signBillCount	签单数量 	 一段时间(最近30天)内商务完成签订合同的数量
    avgRemarkWords	平均备注字数	 一段时间(最近30天)内商务平均每个跟进客户的备注字数
    violationCountRed	红线次数	一段时间(最近30天)内商务违规的次数--红线
    violationCountYellow	黄线次数	一段时间(最近30天)内商务违规的次数--黄线
    behaviorScore	 操行分	当前OA系统商务的操行评分  最近30天
    customerPropertyRate		 一段时间(最近30天)内商务跟进客户所填属性个数总和占客户需填写属性个数总和的比例
    avgIntentCustomerCount	日均意向客户数（陌开）	一段时间(最近30天)内商务平均每天自己发掘的客户数量        以录入系统为准，商务自身资源
    """
    read = Read_Employee()
    ignore_column_list = ['_id', '_class', 'statisticDay', 'realName', 'userId', 'areaCode', 'businessType', 'entryDate']
    return read.read_from_mongo(table_name='dgg_business_people',  filters={'statisticDay': int(day_date)}, ignore_column_list=ignore_column_list)



# def read_behavior_score():
#     """
#     behavior_score		oa操行分
#     score	操行分
#     user_no	客户编号
#     statistic_day	统计日期
#     """
#     read = Read_Employee()
#     ignore_column_list = ['_id','_class','statistic_day']
#     return read.read_from_mongo(table_name='behavior_score', filters={'statistic_day': int(yestoday)},ignore_column_list = ignore_column_list)  # 读取客户评级属性


def read_commerce_interfix():
    """
    userId	商务id
    login_name	登录名
    entry_date	入职时间
    id_card	身份证
    sex	性别
    work_area	工作区域
    rank	 职级
    post	岗位
    dimission_time	离职时间
    locked	帐号是否被锁定:0、正常；1、锁定；2、离职；默认：0、正常',
    """
    read = Read_Employee()
    ignore_column_list = ['work_area', '_id', '_class', 'userId', 'dimission_time', 'entry_date']
    return read.read_from_mongo(table_name='commerce_interfix', ignore_column_list=ignore_column_list)


# def read_bi_performance_detail():
#     """
#     sign_user_id	商务id
#     user_no	工号
#     performance_amount	业绩
#     profit_amount	利润
#     internal_cost	内部成本
#     external_cost	外部成本
#     payment_amount	核款金额
#     business_amount	订单金额
#     order_id    	订单id
#     order_no    	订单编码
#     customer_id   	客户id
#     customer_no  	客户编码
#     business_no   	商机编码
#     payment_id     	核款id
#     payment_no
#     type	核退款 1核款 2退款
#     contract_no   	合同号
#     order_type   	订单类型
#     is_cost 	是否计算成本
#     cost_price    	成本价
#     product_type	产品小业态
#     product_type_parent	产品大业态
#     create_time	创建时间
#     update_time	修改时间
#     performance_month	业绩日
#     performance_date	业绩月
#     """
#     read = Read_Employee()
#     return read.read_from_mongo(table_name='bi_performance_detail')


# def read_bi_performance_detail():
#     """
#     sign_user_id	商务id
#     user_no	工号
#     performance_amount	业绩
#     profit_amount	利润
#     internal_cost	内部成本
#     external_cost	外部成本
#     payment_amount	核款金额
#     business_amount	订单金额
#     order_id    	订单id
#     order_no    	订单编码
#     customer_id   	客户id
#     customer_no  	客户编码
#     business_no   	商机编码
#     payment_id     	核款id
#     payment_no
#     type	核退款 1核款 2退款
#     contract_no   	合同号
#     order_type   	订单类型
#     is_cost 	是否计算成本
#     cost_price    	成本价
#     product_type	产品小业态
#     product_type_parent	产品大业态
#     create_time	创建时间
#     update_time	修改时间
#     performance_month	业绩日
#     performance_date	业绩月
#     """
#     read = Read_Employee()
#     return read.read_from_mongo(table_name='bi_performance_detail')


def get_employee_feature(statistic_day):
    print_to_log('Start reading employee')
    print_to_log(statistic_day)
    dgg_date = datetime.datetime.strptime(str(statistic_day), "%Y%m%d")
    try:
        results = list(read_dgg_business_people(statistic_day))# pyMongoDB.curso.curso强转为list
        print_to_log('read_customer总共有%d条商务数据' % len(results))
        df1 = pd.DataFrame.from_records(results)
        print_to_log(df1.shape)
        print(df1.head())
        print_to_log(df1.columns)
        df1.rename(columns={'userNo': 'login_name'}, inplace=True)
        # df1 = df1[['login_name', 'meetingCount', 'orderAmount', 'orderCount']]
        # df1['login_name'] = df1['login_name'].fillna('NaN')  # 首先填充空值
        # df1 = df1.drop(df1[df1.customer_id == 'NaN'].index.tolist())
        df1['login_name'] = df1['login_name'].astype(object)
    except Exception as e:
        print_to_log(e, level=4)


    # try:
    #     print_to_log()
    #     results = list(read_behavior_score())
    #     print_to_log('read_behavior_score总共有%d条商务数据' % len(results))
    #     df2 = pd.DataFrame(results)
    #     df2.rename(columns={'user_no': 'login_name'}, inplace=True)
    #     df2.groupby('login_name').agg({'score':np.mean})
    #     print_to_log(df2.shape)
    #     print_to_log(df2.head())
    #     print_to_log(df2.columns)
    #     df2['login_name'] = df2['login_name'].astype(object)
    #     df2 = df2[['login_name', 'score']]
    # except Exception as e:
    #     print_to_log(e, level=4)

    try:
        print_to_log()
        results = list(read_commerce_interfix())
        print_to_log('read_commerce_interfix总共有%d条商务数据' % len(results))
        df3 = pd.DataFrame(results)
        print_to_log(df3.shape)
        print(df3.head())
        print_to_log(df3.columns)
        df3['login_name'] = df3['login_name'].astype(object)
        convert = Decoder(dgg_date)
        df3['id_card'] = df3['id_card'].apply(convert.convert_id_card)
        df3.rename(columns = {'id_card':'age'},inplace=True)

    except Exception as e:
        print_to_log(e, level=4)


    print_to_log(df1.shape, df3.shape)
    df = df1
    df_list = [df1, df3]
    print_to_log("合并前各个商务报表shape:", df1.shape, df3.shape)
    for i in range(1, len(df_list)):
        df2 = df_list[i]
        if len(df2) > 0 and len(df) > 0:
            df = pd.merge(df, df2, on='login_name', how='left')
        else:
            print_to_log('某一张表没有数据 i=%d, len(df)=%d, len(df2)=%d' % (i, len(df), len(df2)))
            df = (df if (len(df)>len(df2)) else df2)
            continue
    de = ['average_performance_amount', 'average_profit_amount', 'average_internal_cost', 'average_external_cost',
          'average_payment_amount', 'average_business_amount', 'average_cost_price']

    for d in de:
        decode = Decoder(d)
        df[d] = df['biPerformanceDetail'].apply(decode.biPerformanceDetail)

    de = ['average_CALL_0940_1040', 'average_CALL_1100_1200', 'average_CALL_1400_1500', 'average_CALL_1520_1620']
    for d in de:
        decode = Decoder(d)
        df[d] = df['dwDCallDaily'].apply(decode.dwDCallDaily)

    de = ['average_achievement', 'average_profit', 'average_internal_cost_orf',
          'average_external_cost_orf', 'average_assist_achievement', 'average_assist_profit']
    for d in de:
        decode = Decoder(d)
        df[d] = df['orfOrder'].apply(decode.orfOrder)

    df.drop(['orfOrder', 'dwDCallDaily', 'biPerformanceDetail'], axis=1, inplace=True)
    df.drop_duplicates(subset=['login_name'], inplace=True)
    print_to_log(df.info())
    print_to_log(df.shape)
    print_to_log('End reading employee')
    df.to_csv(data_path+'employee_df.csv', encoding='utf-8', index=0, sep=',')


    return df

if __name__ == '__main__':
    today_date = datetime.datetime.now()
    df = get_employee_feature(today_date.strftime('%Y%m%d'))
    print_to_log(df.columns)
    print_to_log(df.shape)
    print(df.head())