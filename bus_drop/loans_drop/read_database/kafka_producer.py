#!/usr/bin/env python
# encoding: utf-8
'''
@author: 罗成
@contact: luocheng@dgg.net
@file: kafka_producer.py
@time: 2019-05-16 15:45
@desc:
'''
from kafka import KafkaProducer
from kafka.errors import KafkaError
import json
import time
import os
import sys
sys.path.append(os.environ['PUSHPATH'])
from loans_drop.config.c_define import *
from loans_drop.config.database import kafka_servers, topic_in, topic_out


class Producer():
    '''
    使用kafka的生产模块
    '''

    def __init__(self, servers=kafka_servers, topic=None, key=None):
        self.servers = servers
        self.topic = topic
        self.key = key
        self.producer = KafkaProducer(bootstrap_servers=self.servers)

    def sendjsondata(self, params):
        try:
            # parmas_message = json.dumps(params)
            parmas_message = params
            producer = self.producer
            producer.send(self.topic, key=self.key, value=parmas_message.encode('utf-8'))
            producer.flush()
            print_to_log("====kafka发送====")
        except KafkaError as e:
            print_to_log("====kafka发送失败====", e, level=4)


def test():
    '''
    测试consumer和producer
    :return:
    '''
    # 测试生产模块
    producer = Producer(kafka_servers, topic_in)
    for i in range(1000000000000):
        params = 'test---' + str(i)
        print(params)
        producer.sendjsondata(params)
        time.sleep(1)


if __name__ == '__main__':
    test()
    import os
    print(os.uname)
