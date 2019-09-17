#!/usr/bin/env python
# encoding: utf-8
'''
@author: 李欢
@contact: lihuan@dgg.net
@file: DeepFM_model.py
@time: 2019/4/29 10:15
@desc: 基于Tensorflow的DeepFM模型
'''
import tensorflow as tf
import numpy as np
import sklearn.metrics as metrics

class DeepFM():
    '''
    基于tensorflow的DeepFM模型
    数据预处理时，将所有连续变量进行区间缩放，所有分类变量下的所有类别进行排序编码0，1，2，....n
        例如: 类别变量有两个，一个是性别，一个是区域，性别有2类（男，女），区域有3类（A,B,C），则数据中[男，女，A,B,C]五个值的编码即为[0,1,2,3,4]
    采用DeepFM类初始化模型，参数如下：
        category_index            : 数据中类别型变量的位置，整数列表，例如[0,1,2,4,6,7]
        continues_index           : 数据中数值型变量的位置，整数列表，例如[3,5,8,9,10]
        category_embedding_range  : 数据中所有类别型变量的所有类别的数量。
                                      例如: 类别变量有两个，一个是性别，一个是区域，性别有2类（男，女），区域有3类（A,B,C），则该值为2 + 3 = 5
        embedding_size            : 对类别变量进行嵌入操作时的向量维度
        hidden_dims               : DNN模块的隐层神经元数量，为整数的列表，例如[256,128,64,32]，列表的维度为DNN层数，对应的数值为每层神经元数量
        out_dim                   : 模型输出的维度，二分类任务为1，多分类任务为类别数量
        dropout                   : 每层网络的Drop率，与hidden_dims相对应，例如[0.5,0.5,0.5,0.5]代表有4层网络，每层的drop率分别为0.5,0.5,0.5,0.5
        training                  : 为True代表模型处于训练状态，为False代表模型处于预测状态
    采用.train()函数进行训练，参数设置如下:
        x_train, x_val, y_train, y_val : 训练集与测试集数据，需全部转换成numpy.array格式
        batch_size                     : 每一次训练获取的数据批次数量，数据量不大时，可以设置为len(train_data)
        epoch                          : 迭代次数
        loss_type                      : 若为'focal_loss'代表对正负样本平衡后的交叉熵，否则为默认的交叉熵损失
                                            (可根据预测目标进一步修改loss_function)
        save_path                      : 模型权重文件保存路径
    采用.predict_prob()函数进行预测，参数设置如下:
        x_input                        : 预测输入数据，格式为numpy.array()
        batch_size                     : 若预测数据量过大，可分批次进行预测，默认为None
        weight_path                    : 模型权重文件路径
        输出:
            预测概率，若为二分类，可通过model.predict_prob(data_test, batch_size=None)>0.5转换成预测的类别
    训练步骤:
    1. 初始化模型: model = DeepFM(...,training=Trun)
    2. 模型训练: model.train(...)
    预测步骤：
    1. 初始化模型: model = DeepFM(...,training=False)
    2. 模型训练: model.predict_prob(test_data)
    '''
    def __init__(self, category_index, continues_index, category_embedding_range,
                 embedding_size=10, hidden_dims=[32, 32], out_dim=5, dropout=[0.5, 0.5], training=True):
        self.category_index = category_index
        self.continues_index = continues_index
        continues_lenth = len(continues_index)
        category_lenth = len(category_index)
        self.attention = tf.get_variable('attention',shape = (continues_lenth+category_lenth,1),trainable=True)
        with tf.name_scope('inputs'):
            self.continues_inputs = tf.placeholder(dtype=tf.float32, name='continues_inputs')
            self.category_inputs = tf.placeholder(dtype=tf.int32, name='category_inputs')
            self.target = tf.placeholder(dtype=tf.float32, name='target')
        # fm计算模块
        with tf.name_scope('fm'):
            # 线性计算部分
            with tf.name_scope('fm_first_order'):
                # 定义离散变量embedding参数以及连续变量weights矩阵
                self.fm_first_embedding_weights = tf.get_variable("fm_first_embedding_weights",
                                                                  shape=(category_embedding_range, out_dim),
                                                                  trainable=True)
                self.fm_first_continues_weights = tf.get_variable("fm_first_continues_weights",
                                                                  shape=(continues_lenth, out_dim), trainable=True)
                self.first_order_bias = tf.get_variable('fm_bias', shape=(out_dim), trainable=True)
                # 进行线性计算
                self.first_order_embedding = tf.nn.embedding_lookup(self.fm_first_embedding_weights,
                                                                    self.category_inputs, name='first_order_embeddings')
                self.first_order_continues = tf.multiply(tf.expand_dims(self.continues_inputs,-1), self.fm_first_continues_weights,name='first_order_continues')
                self.first_order_continues_conbine = tf.concat([self.first_order_embedding,self.first_order_continues],1,name='first_order_conbine_features')
                self.fm_first_order_attention = tf.multiply(self.first_order_continues_conbine,self.attention,name='first_attention_block')

                self.first_order = tf.add(tf.reduce_sum(self.fm_first_order_attention, 1), self.first_order_bias,
                                          name='fm_first_order')
            # 二阶特征交叉计算部分
            with tf.name_scope('fm_second_order'):
                self.fm_second_embedding_weights = tf.get_variable("fm_second_embedding_weights",
                                                                   shape=(category_embedding_range, embedding_size),
                                                                   trainable=True)
                self.fm_second_continues_weights = tf.get_variable("fm_second_continues_weights",
                                                                   shape=(continues_lenth, embedding_size),
                                                                   trainable=True)
                self.second_order_embedding = tf.nn.embedding_lookup(self.fm_second_embedding_weights,
                                                                     self.category_inputs,
                                                                     name='second_order_embeddings')
                self.second_order_continues = tf.multiply(tf.expand_dims(self.continues_inputs, -1),
                                                          self.fm_second_continues_weights,
                                                          name='second_order_continues')
                self.fm_interaction_conbine = tf.concat([self.second_order_embedding,
                                                         self.second_order_continues], 1,
                                                        name='second_order_conbine_features')
                self.fm_interaction_conbine_attention = tf.multiply(self.fm_interaction_conbine , self.attention,name='_second_attention_block')
                self.fm_interaction_terms = 0.5 * tf.subtract(tf.square(tf.reduce_sum(self.fm_interaction_conbine_attention, 1)),
                                                              tf.reduce_sum(tf.square(self.fm_interaction_conbine_attention), 1),
                                                              name='fm_interaction_order')    # xy = ((x+y)`2 - (x`2+y`2))/2
                self.second_order = tf.layers.dense(tf.reshape(self.fm_interaction_terms, shape=(-1, embedding_size)),
                                                    out_dim, use_bias=False, name='fm_second_order')
            # 将一阶结果和二阶结果相加，得到fm部分的输出
            self.fm_part = tf.add(self.first_order, self.second_order, name='fm_part_output')
        # dnn计算模块
        with tf.name_scope('dnn'):
            self.deep = tf.reshape(self.fm_interaction_conbine_attention,
                                   shape=(-1, (category_lenth + continues_lenth) * embedding_size),
                                   name='dnn_inputs')  # 将FM二阶特征用作dnn的输入
            for dim, drop in zip(hidden_dims, dropout):
                self.deep = tf.layers.dense(self.deep, dim)
                self.deep = tf.layers.batch_normalization(self.deep, training=training)
                self.deep = tf.nn.relu(self.deep)
                self.deep = tf.layers.dropout(self.deep, drop, training=training)
            self.deep_part = tf.layers.dense(self.deep, out_dim, use_bias=False, name='deep_part')
        # 将fm模块和dnn模块的计算结果相加，得到最终输出
        with tf.name_scope('out_puts'):
            self.outputs = tf.add(self.deep_part, self.fm_part, name='outputs')
            self.predict = tf.sigmoid(self.outputs) > 0.5
            self.pre_prob = tf.sigmoid(self.outputs)
        with tf.name_scope('loss'):
            self.loss = tf.reduce_sum(tf.nn.sigmoid_cross_entropy_with_logits(labels=self.target, logits=self.outputs))
            self.focal_loss = tf.reduce_sum(
                tf.nn.sigmoid_cross_entropy_with_logits(labels=self.target, logits=self.outputs) * (
                        0.7 * self.target * (1 - tf.sigmoid(self.outputs)) ** 2 + 0.3 * (1 - self.target) * (
                    tf.sigmoid(self.outputs)) ** 2))

    def train(self, x_train, x_val, y_train, y_val, batch_size, epoch=300, loss_type='focal_loss',save_path = r'./model_weights/final_model.ckpt'):
        train_lenth = len(x_train)
        val_lenth = len(x_val)
        print('training DeepDM with {} data,batch size = {}, epoch = {} and {} val_data'.format(train_lenth, batch_size,
                                                                                                epoch, val_lenth))
        if loss_type == 'focal_loss':
            loss = self.focal_loss
        else:
            loss = self.loss
        val_loss = []
        lr = tf.Variable(0.1,trainable=False)
        step = tf.Variable(0,trainable=False)
        optimizer = tf.train.AdamOptimizer(lr)
        update_ops = tf.get_collection(tf.GraphKeys.UPDATE_OPS)
        with tf.control_dependencies(update_ops):  # 保证train_op在update_ops执行之后再执行。
            train_op = optimizer.minimize(loss, global_step=step)
        init = tf.global_variables_initializer()
        with tf.Session() as sess:
            sess.run(init)
            ajust = 0
            for i in range(epoch):
                val_loss_epoch = []
                # 生成训练及测试数据的batches
                a = list(range(train_lenth))
                np.random.shuffle(a)
                b = list(range(val_lenth))
                steps = train_lenth // batch_size
                val_batch = val_lenth // steps
                sample_train = [a[i * batch_size:(i + 1) * batch_size] for i in range(steps)]
                sample_val = [b[i * val_batch:(i + 1) * val_batch] for i in range(steps)]
                j = 0
                for t, v in zip(sample_train, sample_val):
                    j = j + 1
                    x_train_batch = x_train[t, :]
                    y_train_batch = y_train[t, :]
                    x_val_batch = x_val[v, :]
                    y_val_batch = y_val[v, :]
                    _,lr_rate, train_loss_batch = sess.run((train_op, lr, loss),
                                                   feed_dict={
                                                       self.continues_inputs: x_train_batch[:, self.continues_index],
                                                       self.category_inputs: x_train_batch[:, self.category_index],
                                                       self.target: y_train_batch})
                    val_loss_batch, pred = sess.run((loss, self.predict),
                                                    feed_dict={
                                                        self.continues_inputs: x_val_batch[:, self.continues_index],
                                                        self.category_inputs: x_val_batch[:, self.category_index],
                                                        self.target: y_val_batch})
                    f1 = metrics.f1_score(y_val_batch.reshape(-1), pred.reshape(-1))
                    recall = metrics.recall_score(y_val_batch.reshape(-1), pred.reshape(-1))
                    precision = metrics.precision_score(y_val_batch.reshape(-1), pred.reshape(-1))
                    val_loss_epoch.append(val_loss_batch)
                    print(
                        'epoch {}/{}:  {}/{}: train loss = {:.4f}    val_loss = {:.4f}   recal = {:.4f}   f1_score = {:.4f}  precision = {:.4f}'.format(
                            i + 1, epoch, j, steps, train_loss_batch,
                            val_loss_batch, recall, f1, precision))
                val_loss.append(sum(val_loss_epoch) / len(val_loss_epoch))
                ajust += 1
                if len(val_loss) > 15 and val_loss[-1] >= val_loss[-15] and ajust >= 15:
                    ajust = 0
                    print('Epoch   {}: reducing learning rate from {} to {}'.format(i + 1, lr_rate, lr_rate * 0.1))
                    lr = lr * 0.1
                elif lr_rate <1e-8:
                    print('earl_stop.')
                    break
            saver = tf.train.Saver(var_list=tf.global_variables())
            saver.save(sess, save_path)
            print("Training model finished. Final model weights has been saved into 'final_model.ckpt'.")
            return recall,f1,precision

    def predict_prob(self, x_input, batch_size=None,weight_path = r'./model_weights/final_model.ckpt'):
        saver = tf.train.Saver(tf.global_variables())
        input_len = len(x_input)
        with tf.Session() as sess:
            saver.restore(sess, weight_path)
            if batch_size == None:
                out_list = sess.run(self.pre_prob,feed_dict={self.category_inputs:x_input[:,self.category_index],
                                   self.continues_inputs:x_input[:,self.continues_index]}).reshape(-1)
            else:
                out_list = []
                i = 0
                while (i + 1) * batch_size < input_len:
                    x = x_input[i * batch_size:(i + 1) * batch_size, :]
                    out_list += list(sess.run(self.pre_prob, feed_dict={
                        self.continues_inputs: x[:,self.continues_index],
                        self.category_inputs: x[:,self.category_index]
                    }).reshape(-1))
                    i +=1
                x = x_input[i * batch_size:,:]
                out_list += list(sess.run(self.pre_prob, feed_dict={
                    self.continues_inputs: x[:,self.continues_index],
                    self.category_inputs: x[:,self.category_index]
                }).reshape(-1))
        return np.array(out_list)