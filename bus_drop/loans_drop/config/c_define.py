#!/usr/bin/env python
# encoding: utf-8
'''
@author: 罗成
@contact: luocheng@dgg.net
@file: c_define.py
@time: 2019-05-31 15:44
@desc: 仿造c语言做的宏定义文件,该文件必须被import * ,此文件任何被置灰的代码都不能删
'''

import sys
import os
import datetime
sys.path.append(os.environ['PUSHPATH'])

from util.log_util.inspect_logging import print_to_log


# curPath = os.path.abspath(os.path.dirname(__file__))
# rootPath = os.path.split(curPath)[0]
# sys.path.append(os.path.split(rootPath)[0])

