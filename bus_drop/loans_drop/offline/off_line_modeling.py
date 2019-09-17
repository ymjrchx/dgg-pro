#!/usr/bin/env python
# encoding: utf-8
'''
@author: Li Huan
@contact: lihuan@dgg.net
@file: off_line_modeling.py
@time: 2019/5/21 15:47
@desc: 离线建模程序
'''
import os
import pandas as pd
import numpy as np
from loans_drop.config.feature import *
from loans_drop.config.c_define import *
import pickle
from sklearn.linear_model import LogisticRegression
from sklearn import metrics
from sklearn.externals import joblib
from loans_drop.config.path import data_path, model_path
import pickle
import xgboost

from hyperopt import hp, fmin, tpe, STATUS_OK, Trials,partial


def train_model(total_data,bus_data,user_data):
    # 提取label并删除其他多余信息
    print_to_log('==========数据预处理==============')
    total_data['label'] = total_data['product_amount'].apply(lambda x:1 if x>=0 else 0)
    total_data.drop(['customer_id', 'login_name', 'product_amount', 'salary_score'
                     , 'followStart', 'followEnd', 'content', 'post'], axis=1, inplace=True)

    total_data_po = total_data.loc[total_data['label']==1]
    total_data_ne = total_data.loc[total_data['label']==0]
    #控制负样本与正样本的比例为10:1
    samples = total_data_po.shape[0]*5
    total_data_ne_sample = total_data_ne.sample(samples)
    # 重新生成训练数据
    total_data_sample = pd.concat((total_data_ne_sample,total_data_po),axis=0)
    total_data_sample = total_data_sample.sample(frac=1)  # 打乱顺序
    # 离散型变量,如果某一取值的数量超过了95%，则舍弃该变量
    remove = set()
    miss_value = []
    print_to_log('处理类别型变量')
    for k in category_columns:
        if k in total_data_sample.keys():
            counts = total_data_sample[k].value_counts()
            total_num = sum(counts)
            for v in counts:
                if v/total_num>=0.95:
                    print_to_log(k)
                    print_to_log(counts)
                    remove.add(k)
        else:
            miss_value.append(k)
    # 连续型变量,如果其下四分位数=中位数=上四分位数，则舍弃该变量
    print_to_log('处理连续型变量')
    for k in continues_columns:
        if k in total_data_sample.keys():
            total_data_sample[k] = total_data_sample[k].astype('float')
            button = total_data_sample[k].min()
            top = total_data_sample[k].max()
            if button == top:
                remove.add(k)
                print_to_log(k, 0)
            else:
                var = total_data_sample[k].apply(lambda x: (x - button) / (top - button)).var()
                if var < 0.003:
                    remove.add(k)
                    print_to_log(k,var)
        else:
            miss_value.append(k)
    # 去掉缺失率大于80%的变量
    print_to_log('处理缺失率较大的变量')
    for k in total_data_sample.keys():
        miss = total_data[k].isna().sum()/total_data.shape[0]
        if miss>0.8:
            remove.add(k)
            print_to_log(k,':',miss)
    # 去掉商机地区信息
    remove.add('placeCode')
    remove.add('businessOperate')
    remove.add('businessStage')
    remove.add('businessStatus')
    # 保存剔除变量

    user_remove = []
    bus_remove = []
    for k in remove:
        if k in user_data.keys():
            user_remove.append(k)
        else:
            bus_remove.append(k)
    total_data_sample.drop(list(remove), axis=1, inplace=True)
    user_data.drop(user_remove, axis=1, inplace=True)
    bus_data.drop(bus_remove, axis=1, inplace=True)
    # 添加冷启动数据
    def cold_start_decode(k):
        if k in continues_columns:
            return 0
        elif k in category_columns:
            return 'None'
        elif k == 'customer_id':
            return -1
        else:
            return 'None'
    cold_start = {}
    for k in user_data.keys():
        cold_start[k] = cold_start_decode(k)
    user_data.append(cold_start,ignore_index=True)
    if len(miss_value)>0:
        print_to_log('数据字段缺失，请检查数据库',miss_value)

    #连续变量用0填充,类别型变量全部用‘None’填充
    def fillna(df):
        for k in df.keys():
            if k in continues_columns:
                df[k] = df[k].fillna(0)
            else:
                df[k] = df[k].fillna('None')

    fillna(total_data_sample)
    fillna(user_data)
    fillna(bus_data)
    continues_dict = {}
    category_dict = {}
    for k in bus_data.keys():
        if k in continues_columns:
            continues_dict[k] = (bus_data[k].max(), bus_data[k].min())
        elif k in category_columns:
            bus_data[k] = bus_data[k].apply(lambda x: str(x).split('.')[0])
            category_dict[k] = list(bus_data[k].unique())
            if 'None' not in category_dict[k]:
                category_dict[k].append('None')
            bus_data[k] = bus_data[k].apply(lambda x: category_dict[k].index(x))

    for k in user_data.keys():
        if k in continues_columns:
            continues_dict[k] = (user_data[k].max(), user_data[k].min())
        elif k in category_columns:
            user_data[k] = user_data[k].apply(lambda x: str(x).split('.')[0])
            category_dict[k] = list(user_data[k].unique())
            if 'None' not in category_dict[k]:
                category_dict[k].append('None')
            user_data[k] = user_data[k].apply(lambda x: category_dict[k].index(x))

    # 对连续变量进行区间缩放并存放中间值,对类别型变量进行独热编码并存放中间值
    for k in total_data_sample.keys():
        # if k in continues_dict.keys():
            # total_data_sample[k] = total_data_sample[k].apply(lambda x:(x-continues_dict[k][0])/(continues_dict[k][1])-continues_dict[k][0])
        if k in category_dict.keys():
            # 直接编码

            total_data_sample[k] = total_data_sample[k].apply(lambda x:category_dict[k].index(str(x).split('.')[0]))

            # for v in category_dict[k]:
            #     total_data_sample[k+'_'+str(v)] = total_data_sample[k].apply(lambda x:1 if x ==v else 0)
            # total_data_sample.drop(k,axis=1,inplace=True)

    # 建模并进行训练
    label = total_data_sample['label']
    total_data_sample.drop('label',axis = 1,inplace=True)
    lenth = total_data_sample.shape[0]

    train_data = total_data_sample[:int(0.8*lenth)]
    train_label = label[:int(0.8*lenth)]
    test_data = total_data_sample[int(0.8*lenth):]
    test_label = label[int(0.8*lenth):]
    print_to_log('==========模型训练==============')
    print(train_data.info())
    print(train_data.head())

    def objective(space):
        model = xgboost.XGBClassifier(
            max_depth=int(space['max_depth']),
            n_estimators=int(space['n_estimators']),
            subsample=space['subsample'],
            colsample_bytree=space['colsample_bytree'],
            learning_rate=space['learning_rate'],
            reg_alpha=space['reg_alpha'],
            nthread=4
        )
        model.fit(train_data, train_label)
        score = metrics.f1_score(test_label, model.predict(test_data))
        print_to_log('score: {}'.format(score))
        return {'loss': 1 - score, 'status': STATUS_OK}

    space = {
        'max_depth': hp.quniform('max_depth', 2, 20, 1),
        'n_estimators': hp.quniform('n_estimators', 100, 500, 1),
        'subsample': hp.uniform('subsample', 0.8, 1),
        'colsample_bytree': hp.uniform('colsample_bytree', 0.1, 1),
        'learning_rate': hp.uniform('learning_rate', 0.01, 0.1),
        'reg_alpha': hp.uniform('reg_alpha', 0.1, 1),
    }

    algo = partial(tpe.suggest, n_startup_jobs=4)
    trials = Trials()
    best = fmin(fn=objective,
                space=space,
                algo=algo,
                max_evals=20,
                trials=trials)
    print_to_log(best)
    model = xgboost.XGBClassifier(
        max_depth = int(best['max_depth']),
        n_estimators = int(best['n_estimators']),
        subsample = best['subsample'],
        colsample_bytree = best['colsample_bytree'],
        learning_rate = best['learning_rate'],
        reg_alpha = best['reg_alpha'],
        nthread = 4)
    model.fit(train_data,train_label)
    pred = model.predict(test_data)
    print_to_log('==========训练完成==============')
    print_to_log('模型得分: ')
    print_to_log('recall:',metrics.recall_score(test_label,pred))
    print_to_log('precision:',metrics.precision_score(test_label,pred))
    print_to_log('f1_score:',metrics.f1_score(test_label,pred))
    print_to_log('auc_score:',metrics.roc_auc_score(test_label,pred))
    print_to_log(len(total_data_sample.keys()))

    try:
        with open(model_path + 'remove.pk', 'wb') as f:
            pickle.dump(remove, f)
        with open(model_path + 'continues_dict.pk', 'wb') as f:
            pickle.dump(continues_dict, f)
        with open(model_path + 'category_dict.pk', 'wb') as f:
            pickle.dump(category_dict, f)
        joblib.dump(model, model_path+'model.m')
        user_data.to_csv(data_path + 'employee_feature.csv', encoding='utf-8', index=0, sep=',')
        bus_data.to_csv(data_path + 'business_feature.csv', encoding='utf-8', index=0, sep=',')
        print_to_log('所有数据保存完毕')
    except Exception as e:
        print_to_log(e, level=4)
        print_to_log('保存文件失败，检查文件路径')


if __name__ == '__main__':
    total_data = pd.read_csv(data_path + 'total_data20190711.csv')
    bus_data = pd.read_csv(data_path + r'business_df.csv')
    user_data = pd.read_csv(data_path + r'employee_df.csv')
    train_model(total_data,bus_data,user_data)



