#!/usr/bin/env python
# encoding: utf-8
'''
@author: 李颖
@contact: Liying6@dgg.net
@file: ceshi.py
@time: 2019/5/17 9:32
@desc:
'''

import pandas as pd
from loans_drop.recall.itemCF import recommend

score_data = pd.read_csv('../data/score_data.csv', names=['customer_id', 'login_name', 'score'])
recommend_data = dict()
for i in score_data['customer_id']:
    recommend_data.setdefault(i, {})
    a = recommend("%s" % i)
    recommend_data[i] = a
print(recommend_data)
