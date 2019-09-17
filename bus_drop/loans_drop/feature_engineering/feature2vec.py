#!/usr/bin/env python
# encoding: utf-8
'''
@author: 罗成
@contact: luocheng@dgg.net
@file: feature2vec.py
@time: 2019-05-05 20:02
@desc:
'''
from loans_drop.read_database.employee_MongoDB import get_employee_feature
import pandas as pd
import multiprocessing
from gensim.models import Word2Vec
from loans_drop.config.recommend_push_feature import employee_category_feature


if __name__ == '__main__':
    df = get_employee_feature()
    print(df.head())

    sentence = []
    # 仅限于类别形特征  以及  已经离散化后的连续型特征
    for line in list(df[employee_category_feature].values):
        sentence.append([str(float(l)) for idx, l in enumerate(line)])

    # 训练word2vec模型并生成向量
    print('training...')
    model = Word2Vec(sentence,
                     size=L,
                     window=2,
                     min_count=1,
                     workers=multiprocessing.cpu_count(),
                     iter=10)
    print('outputing...')

    for fea in employee_category_feature:
        values = []
        for line in list(df[fea].values):
            values.append(line)
        values = set(values)
        print(len(values))
        w2v = []
        for i in values:
            a = [i]
            a.extend(model[str(float(i))])
            w2v.append(a)
        out_df = pd.DataFrame(w2v)
