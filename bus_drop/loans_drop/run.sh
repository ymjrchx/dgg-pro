#!/bin/bash
echo '**' `date +%H:%M:%S` 'start'
nohup python -u $PUSHPATH/loans_drop/data_cleaning/load_data.py > $PUSHPATH/loans_drop/log/offline.txt 2>&1 &
echo '**' `date +%H:%M:%S` 'start load_data success'
nohup python -u $PUSHPATH/loans_drop/read_database/business_kafka.py > $PUSHPATH/loans_drop/log/online.txt 2>&1 &
echo '**' `date +%H:%M:%S` 'start kafka success'
nohup python -u $PUSHPATH/loans_drop/read_database/kafka_consumer.py > $PUSHPATH/loans_drop/log/kafka.txt 2>&1 &