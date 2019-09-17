#!/usr/bin/env python
# encoding: utf-8
'''
@author: 罗成
@contact: luocheng@dgg.net
@file: kafka_consumer.py
@time: 2019-05-16 13:35
@desc:
'''
#!/usr/bin/env python
# -*- coding: utf-8 -*-
from kafka import KafkaConsumer
from kafka.errors import KafkaError
import time
import datetime
import json
import sys
import os
sys.path.append(os.environ['PUSHPATH'])
from loans_drop.config.database import kafka_servers, topic_in, topic_out
from loans_drop.config.c_define import *


class Kafka_consumer():
    '''
    使用Kafka—python的消费模块
    '''

    def __init__(self, kafkatopic, groupid):
        self.kafkatopic = kafkatopic
        self.groupid = groupid
        self.consumer = KafkaConsumer(self.kafkatopic, group_id=self.groupid,
                                      bootstrap_servers=kafka_servers)

    def consume_data(self):
        try:
            for message in self.consumer:
                # print json.loads(message.value)
                yield message
        except KeyboardInterrupt as e:
            print(e)


def get_kafka_data():
    '''
    测试consumer和producer
    :return:
    '''
    # 测试消费模块
    # 消费模块的返回格式为ConsumerRecord(topic=u'ranktest', partition=0, offset=202, timestamp=None,
    # \timestamp_type=None, key=None, value='"{abetst}:{null}---0"', checksum=-1868164195,
    # \serialized_key_size=-1, serialized_value_size=21)
    client = Kafka_consumer(topic_out, 'test-python-test')
    consumer = client.consume_data()
    print(datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"), '=========', consumer)
    for message in consumer:
        if message is not None:
            print(datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"), "====开始测试kafka预测结果====")
            value = str(message.value, encoding='utf-8')
            new_business = json.loads(value)
            print(datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"), new_business)
        try:
            client.consumer.commit()
        except Exception as e:
            print_to_log(e, level=5)



if __name__ == '__main__':
    get_kafka_data()
