#!/usr/bin/env python
# encoding: utf-8
'''
@author: 罗成
@contact: luocheng@dgg.net
@file: business_kafka.py
@time: 2019-05-14 10:51
@desc:
'''
import sys
import os
from kafka import KafkaProducer
from kafka.errors import KafkaError
import json
import re
from kafka import KafkaConsumer
# from loans_drop.recall.itemCF import ItemBasedCF

import time
sys.path.append(os.environ['PUSHPATH'])

from util.time import get_time_stamp13
from loans_drop.read_database.employee_redis import RedisTool
from loans_drop.offline.online_predict import rank
from loans_drop.read_database.kafka_producer import Producer
from loans_drop.config.database import topic_out, kafka_servers, topic_in
from loans_drop.config.c_define import *


ranker = rank()
def read_from_kafka():
    """
    "allocStatus":"alloc_new",
    "areaCode":"BUS_SOR_PLACE_CD",
    "businessId":7807165398043648000,
    "businessType":"BUS_YT_DK",
    "createTime":1557477888000,
    "customerId":7717697800292667392,
    "customerNo":"KH201808142464",
    "id":4154,
    "noteDesc":"",
    "orderStatus":"UN_FLOW",
    "resAllocat":false,
    "resSign":0,
    "resSuccess":0,
    "typeAllocat":"B",
    “userId”:”分配结果需组装，分配到的商务Id”,
    “userNo”:”分配结果需组装，分配到的商务No”,
    “alloctTime”:”分配时间戳，分配结果需组装”
    """
    # itemCF = ItemBasedCF()
    producer = Producer(topic=topic_out)
    consumer = KafkaConsumer(topic_in, group_id='ml', bootstrap_servers=kafka_servers, enable_auto_commit = False)
    # topic = client.topics[topic_in]
    # consumer = topic.get_simple_consumer(consumer_group='test', auto_commit_enable=True, consumer_id='test')
    print_to_log('consumer配置详情:', consumer.config)
    for message in consumer:
        if message is not None:
            print_to_log("'Kafka Start consumer!'")
            start = time.time()
            value = str(message.value, encoding='utf-8')
            try:
                new_business = json.loads(value)
                customerId = new_business['customerId']
                print_to_log(new_business)
            except Exception as e:
                print_to_log(e, level=4)
                print_to_log('kafka解析出错', message, level=4)
                continue


            if customerId=='update_data':
                ranker.update_data()
            else:
                print_to_log('=====Start Pull Employee From Redis=====')
                employee_ids = []
                userId = []
                last_id = []
                try:
                    employees = RedisTool().get_employee_list().employees
                    for employee_dic in employees:
                        if int(employee_dic['userNo']) in ranker.user_list: #
                            employee_ids.append(int(employee_dic['userNo']))
                            userId.append(int(employee_dic['userId']))
                            last_id.append(int(employee_dic['lastBusId']))

                    print_to_log('End Pull Employee:', employee_ids)

                except Exception as e:
                    print_to_log('error: ', e, '没有待选商务', level=4)

                print_to_log('剔除重复推荐的商务')
                for i in range(len(last_id)):
                    if last_id[i] == customerId:
                        last_id.remove(last_id[i])
                        userId.remove(userId[i])
                        employee_ids.remove(employee_ids[i])

                # 判断customerId是否在商机库中,同时待选商务数量是否大于0
                if len(employee_ids)>0:
                    print_to_log('Start Prediction:')

                    try:
                        user_probability = ranker.compute(customerId, employee_ids)
                    except Exception as e:
                        print_to_log('error：模型预测出错，随机选取', e, level=5)
                        user_probability = dict()
                        user_probability['user'] = employee_ids[-1]
                        user_probability['probability'] = 0

                    try:
                        result = user_probability['user']
                        probability = user_probability['probability']
                    except Exception as e:
                        print_to_log('推荐结果解析出错:', e, level=4)
                        result = None
                        probability = 0

                    if result in employee_ids:
                        if float(probability) > 0.0001:
                            new_business['userId'] = userId[employee_ids.index(result)]
                            new_business['userNo'] = result
                            new_business['alloctTime'] = str(get_time_stamp13())
                            new_business['noteDesc'] = '推荐列表最大概率:%s' % str(probability)
                        else:
                            new_business['noteDesc'] = '最大概率:%s,太低，不予推送' % str(probability)
                    else:
                        new_business['noteDesc'] = 'warning:计算结果%s不在待推荐商务列表%s里面' % (str(result), str(employee_ids))

                else:
                    new_business['noteDesc'] = 'warning:商务列表employee_ids为空'

                print_to_log("返回给kafka的数据：")
                print_to_log(new_business)
                try:
                    producer.sendjsondata(json.dumps(new_business))
                except Exception as e:
                    print_to_log('producer.sendjsondata出错', e, level=5)
                print_to_log('总耗时:%.3f'%(time.time()-start))

        try:
            # time.sleep(30) # 用于复现 CommitFailedError: Commit cannot be completed since the group has already rebalanced and assigned the
            consumer.commit()
            print_to_log('Kafka consumer commit success!')
            print() #方便看日志记录
        except Exception as e:
            print_to_log('Kafka consumer commit failed!', e, level=5)
            print()


if __name__ == '__main__':

    read_from_kafka()



