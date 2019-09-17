#!/usr/bin/env python
# encoding: utf-8
'''
@author: 罗成
@contact: luocheng@dgg.net
@file: lgb_train.py
@time: 2019-07-10 15:49
@desc: https://cloud.tencent.com/developer/article/1459838
'''
import lightgbm as lgb
from sklearn import metrics
from sklearn.model_selection import train_test_split
import numpy as np
from sklearn.externals import joblib
import pandas as pd
import matplotlib.pyplot as plt


class LightGBM(object):
    def __init__(self):
        self.X = None
        self.model = None

    def train(self, classifications='binary', save_path=None, dataframe=None):
        train_length = int(len(dataframe) * 0.7)
        train_data = dataframe[:train_length]
        test = dataframe[train_length:]
        train_data.drop(['login_name', 'customer_id'], axis=1)

        print('train.label.value_counts:', train_data.shape)
        print(train_data['label'].value_counts())
        print('test.label.value_counts:', test.shape)
        print(test['label'].value_counts())

        print("数据拆分")
        train, val = train_test_split(train_data, test_size=0.2, random_state=21)

        print("训练集")
        y = train.label  # 训练集标签
        X = train.drop(['label'], axis=1)  # 训练集特征矩阵

        print("验证集")
        val_y = val.label  # 验证集标签
        val_X = val.drop(['label'], axis=1)  # 验证集特征矩阵

        print("测试集")
        test_Y = np.array(test.label.values.tolist()).astype(int)
        test_X = test.drop(['label', 'login_name', 'customer_id'], axis=1)  # 线下测试特征矩阵
        lgb_train = lgb.Dataset(X, y, free_raw_data=False)
        lgb_eval = lgb.Dataset(val_X, val_y, reference=lgb_train, free_raw_data=False)
        self.X = X

        params = {'boosting_type': 'gbdt',
                  'boosting': 'dart',
                  'num_class': 1 if classifications=='binary' else 3,
                  'objective': 'binary' if classifications=='binary' else 'multiclass',
                  'metric': 'binary_logloss' if classifications=='binary' else 'multi_logloss', # multi_logloss binary_logloss

                  'learning_rate': 0.01,
                  'num_leaves': 25,
                  'max_depth': 7,

                  'max_bin': 10,
                  'min_data_in_leaf': 8,

                  'feature_fraction': 0.6,
                  'bagging_fraction': 1,
                  'bagging_freq': 0,

                  'lambda_l1': 0.1,
                  'lambda_l2': 0.2,
                  'min_split_gain': 0
                  }

        print("Start training LightGBM...")
        model = lgb.train(params, lgb_train, num_boost_round=2000, valid_sets=lgb_eval, early_stopping_rounds=30)  # 早停系数

        print('begin test:')
        preds = model.predict(test_X, num_iteration=model.best_iteration)  # 输出概率
        if classifications == 'binary':
            test['probability'] = preds
            preds = [1 if i > 0.5 else 0 for i in preds]
            print('recall_score:', metrics.recall_score(test_Y, preds))
            print('log_loss:', metrics.log_loss(test_Y, preds))
        else:
            test['probability'] = [','.join([str(i) for i in pred_list]) for pred_list in preds]
            preds = [np.argmax(pred_list) for pred_list in preds]
        print('accuracy_score:', metrics.accuracy_score(test_Y, preds))
        print()
        print(metrics.classification_report(test_Y, preds))
        joblib.dump(model, save_path+'gbm.pkl')
        index = [preds[i] != test_Y[i] for i in range(len(preds))]
        test['label_pred'] = preds
        test[index].to_csv(save_path + 'train_result.csv', index=False)
        self.model = model

    def feature_evaluation(self):
        dataframe = pd.DataFrame(self.X.columns.tolist(), columns=['feature'])
        dataframe['importance'] = list(self.model.feature_importance())
        dataframe = dataframe.sort_values(by='importance', ascending=False)
        print(dataframe)
        plt.figure(figsize=(180, 90))
        lgb.plot_importance(self.model, max_num_features=40)
        plt.title("Featurertances")
        plt.show()
