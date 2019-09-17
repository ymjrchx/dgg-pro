#!/usr/bin/env python
# encoding: utf-8
'''
@author: 罗成
@contact: luocheng@dgg.net
@file: recommend_push_feature.py
@time: 2019-04-29 11:29
@desc:
'''

continues_columns = ['avgAddWechatFriendCount', 'avgCallCount', 'avgIntentCustomerCount', 'avgRemarkWords', 'avgSignBillDays',
                    'avgToDoorCount', 'avgWorkHour', 'behaviorScore', 'loanRate', 'performanceRank', 'signBillCount',
                     'signBillRate', 'toDoorRate', 'age', 'work_age', 'average_performance_amount', 'average_profit_amount',
                     'average_internal_cost', 'average_external_cost', 'average_payment_amount', 'average_business_amount',
                     'average_cost_price', 'average_CALL_0940_1040', 'average_CALL_1100_1200', 'average_CALL_1400_1500',
                     'average_CALL_1520_1620', 'average_achievement', 'average_profit', 'average_internal_cost_orf',
                     'average_external_cost_orf', 'average_assist_achievement', 'average_assist_profit', 'orderAmount',
                     'meetingCount', 'orderCount', 'tccs', 'flagContent', 'flagRemark', 'mon', 'times', 'allotNumber',
                     'followTimes', 'invalidNumber', 'coverCharge', 'signOderNum', 'callTimes', 'avgCallDuration']

category_columns = ['locked', 'rank', 'sex', 'remarkType', 'addTypeCode', 'businessLocation', 'businessOperate', 'businessStage',
                    'businessStatus', 'extensionCode', 'isCost', 'isMind', 'lastLoseType', 'opportunitytypeCode', 'originCode',
                    'originType', 'placeCode', 'referral', 'uploadType', 'vip', 'wayCode', 'widelyType', 'refundOrderSign']