#!usr/bin/env python
# encoding: utf-8
"""
@author: 孙小龙
@contact: sunxiaolong@dgg.net
@file: label_encoder.py
@time: 2019/5/21 下午4:08
@desc:
"""
import pandas as pd
import numpy as np
import os
import _pickle as cPickle


class Label_Encoder:

    def slice_obj_data(self,data):
        """  提取数据类型为object的数据，可能需要填充缺失值，可能是类别型特征需要进行label或one-hot
        Args:
            data:

        Returns:
            数据类型为object的数据子集
            """
        dtypes = data.dtypes
        dtypes = dtypes.reset_index()
        dtypes.columns = ["feature_name", "types"]
        obj_list = list(dtypes.loc[dtypes["types"] == "object", "feature_name"])
        obj_data = data.loc[:, obj_list]
        return obj_data


    def label_encoder_fit(self,data,var_list):
        """
        提认为模拟label_encoder的fit过程，得到fit的字典，要求data为类别型的数据
        Args:
            data:原数据框
            var_list:数据类型为类别型的字段名列表

        Returns:
            每个变量fit的字典
        """
        from sklearn.preprocessing import LabelEncoder
        data = data.loc[:,var_list]
        cols = data.columns.tolist()
        #     cols =["addTypeCode","businessLocation"]
        label_dict = {}
        for col in cols:
            labelencoder = LabelEncoder()
            array = labelencoder.fit_transform(data[col])  # 对每一个字段进行labelencoder
            l1 = list(np.unique(array))  # 得到labelencoder之后的值
            l2 = list(labelencoder.inverse_transform(l1))  # l1对应的值
            dic1 = {}
            for i, j in zip(l1, l2):
                dic1[j] = i
            label_dict[col] = dic1  # 将每个字段的

        return label_dict


    def label_encoder_transform(self, df, var_list, label_dict, Inplace=True):
        """
        提认为模拟label_encoder的fit过程，得到fit的字典，要求data为类别型的数据
        Args:
            df:原数据框
            var_list:数据类型为类别型的字段名列表
            label_dict:字段的fit字典
            Inplace：是否删除原变量
        Returns:
            每个变量fit的字典
        """
        cols = list(df.loc[:, var_list].columns)
        for col in cols:
            try:
                dic = label_dict[col]  # 得到每个字段的label匹配字典
                label = [dic.get(d) for d in df[col]]
                df[col + str("_label_encoder")] = label
            except ValueError:
                print("字段%s没有进行完整的fit，请利用label_label_encoder_fit函数重新进行fit操作" % col)
        if Inplace == Inplace:
            df.drop(var_list, axis=1, inplace=True)
        else:
            pass
        return df


if __name__ =="__main__":
    data = pd.read_csv("../data/shuju.csv")

    label_encoder = Label_Encoder()
    # TODO: 1.测试提取object的字段
    obj = label_encoder.slice_obj_data(data) #1. 找回数据类型为object的对象，这个是需要处理的字段，可能缺失值，可能类别型
    print(obj.head())

    # TODO： 2.label_encoder_fit测试
    label_dict = label_encoder.label_encoder_fit(obj,var_list=obj.columns.tolist()) #2. 手动模拟sklearn的labelencoder的fit过程，得到字典
    print(label_dict)

    # TODO  3. 存pkl文件
    ## 判断是否存在切分点的序列化文件
    label_encoder_pkl_path = "./fe_pickles/label_encoder.pkl"
    if os.path.isfile(label_encoder_pkl_path):
        pass
    else:
        cPickle.dump(label_dict, open(label_encoder_pkl_path, "wb"))  # 存到序列化文件

    # TODO 4. 进行transform
    label_dict = cPickle.load(open(label_encoder_pkl_path,"rb"))
    df = label_encoder.label_encoder_transform(df=obj, var_list=["addTypeCode", "businessLocation"], label_dict=label_dict) #3. 手动模拟sklearn的label_transform的transform过程
    print(df.head())
