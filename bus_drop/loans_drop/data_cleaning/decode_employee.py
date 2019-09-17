#!/usr/bin/env python
# encoding: utf-8
'''
@author: 罗成
@contact: luocheng@dgg.net
@file: decode_employee.py
@time: 2019-07-08 09:22
@desc:
'''
import os
import sys
sys.path.append(os.environ['PUSHPATH'])
from loans_drop.config.c_define import *


class Decoder(object):
    def __init__(self, d):
        self.s = d
        self.date = d

    def convert_id_card(self, x):
        try:
            return self.date.year - int(str(x)[6:10])
        except Exception as e:
            print_to_log(e, level=4)
            return None


    def convert_entry_date(self, x):
        try:
            return (self.date - x).days
        except Exception as e:
            print_to_log(e, level=4)
            return None


    def biPerformanceDetail(self, a):
        if self.s =='average_performance_amount':
            try:
                per = [aa['performance_amount'] for aa in a]
                per = list(map(float,per))
                try:
                    return sum(per)/len(per)
                except Exception as e:
                    # print_to_log(e, level=4)
                    return 0
            except Exception as e:
                # print_to_log(e, level=4)
                return None
        if self.s == 'average_profit_amount':
            try:
                per = [aa['profit_amount'] for aa in a]
                per = list(map(float, per))
                try:
                    return sum(per)/len(per)
                except Exception as e:
                    # print_to_log(e, level=4)
                    return 0
            except Exception as e:
                # print_to_log(e, level=4)
                return None
        if self.s == 'average_internal_cost':
            try:
                per = [aa['internal_cost'] for aa in a]
                per = list(map(float, per))
                try:
                    return sum(per)/len(per)
                except Exception as e:
                    # print_to_log(e, level=4)
                    return 0
            except Exception as e:
                # print_to_log(e, level=4)
                return None
        if self.s == 'average_external_cost':
            try:
                per = [aa['external_cost'] for aa in a]
                per = list(map(float, per))
                try:
                    return sum(per)/len(per)
                except Exception as e:
                    # print_to_log(e, level=4)
                    return 0
            except Exception as e:
                # print_to_log(e, level=4)
                return None

        if self.s == 'average_payment_amount':
            try:
                per = [aa['payment_amount'] for aa in a]
                per = list(map(float, per))
                try:
                    return sum(per)/len(per)
                except Exception as e:
                    # print_to_log(e, level=4)
                    return 0
            except Exception as e:
                # print_to_log(e, level=4)
                return None

        if self.s == 'average_business_amount':
            try:
                per = [aa['business_amount'] for aa in a]
                per = list(map(float, per))
                try:
                    return sum(per)/len(per)
                except Exception as e:
                    # print_to_log(e, level=4)
                    return 0
            except Exception as e:
                # print_to_log(e, level=4)
                return None
        if self.s == 'average_cost_price':
            try:
                per = [aa['cost_price'] for aa in a]
                per = list(map(float, per))
                try:
                    return sum(per)/len(per)
                except Exception as e:
                    # print_to_log(e, level=4)
                    return 0
            except Exception as e:
                # print_to_log(e, level=4)
                return None


    def dwDCallDaily(self,a):
        if self.s =='average_CALL_0940_1040':
            try:
                per = [aa['CALL_0940_1040'] for aa in a]
                per = list(map(float, per))
                try:
                    return sum(per)/len(per)
                except Exception as e:
                    # print_to_log(e, level=4)
                    return 0
            except Exception as e:
                # print_to_log(e, level=4)
                return None
        if self.s == 'average_CALL_1100_1200':
            try:
                per = [aa['CALL_1100_1200'] for aa in a]
                per = list(map(float, per))
                try:
                    return sum(per)/len(per)
                except Exception as e:
                    # print_to_log(e, level=4)
                    return 0
            except Exception as e:
                # print_to_log(e, level=4)
                return None

        if self.s == 'average_CALL_1400_1500':
            try:
                per = [aa['CALL_1400_1500'] for aa in a]
                per = list(map(float, per))
                try:
                    return sum(per)/len(per)
                except Exception as e:
                    # print_to_log(e, level=4)
                    return 0
            except Exception as e:
                # print_to_log(e, level=4)
                return None
        if self.s == 'average_CALL_1520_1620':
            try:
                per = [aa['CALL_1520_1620'] for aa in a]
                per = list(map(float, per))
                try:
                    return sum(per)/len(per)
                except Exception as e:
                    # print_to_log(e, level=4)
                    return 0
            except Exception as e:
                # print_to_log(e, level=4)
                return None


    def orfOrder(self,a):
        if self.s == 'average_achievement':
            try:
                per = [aa['achievement'] for aa in a]
                per = list(map(float, per))
                try:
                    return sum(per)/len(per)
                except Exception as e:
                    # print_to_log(e, level=4)
                    return 0
            except Exception as e:
                # print_to_log(e, level=4)
                return None
        if self.s == 'average_profit':
            try:
                per = [aa['profit'] for aa in a]
                per = list(map(float, per))
                try:
                    return sum(per)/len(per)
                except Exception as e:
                    # print_to_log(e, level=4)
                    return 0
            except Exception as e:
                # print_to_log(e, level=4)
                return None
        if self.s == 'average_internal_cost_orf':
            try:
                per = [aa['internal_cost'] for aa in a]
                per = list(map(float, per))
                try:
                    return sum(per)/len(per)
                except Exception as e:
                    # print_to_log(e, level=4)
                    return 0
            except Exception as e:
                # print_to_log(e, level=4)
                return None
        if self.s == 'average_external_cost_orf':
            try:
                per = [aa['external_cost'] for aa in a]
                per = list(map(float, per))
                try:
                    return sum(per)/len(per)
                except Exception as e:
                    # print_to_log(e, level=4)
                    return 0
            except Exception as e:
                # print_to_log(e, level=4)
                return None
        if self.s == 'average_assist_achievement':
            try:
                per = [aa['assist_achievement'] for aa in a]
                per = list(map(float, per))
                try:
                    return sum(per)/len(per)
                except Exception as e:
                    # print_to_log(e, level=4)
                    return 0
            except Exception as e:
                # print_to_log(e, level=4)
                return None
        if self.s == 'average_assist_profit':
            try:
                per = [aa['assist_profit'] for aa in a]
                per = list(map(float, per))
                try:
                    return sum(per)/len(per)
                except Exception as e:
                    # print_to_log(e, level=4)
                    return 0
            except Exception as e:
                # print_to_log(e, level=4)
                return None

