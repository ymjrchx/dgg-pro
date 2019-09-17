# 数据和代码分离，手敲路径容易出错且不易维护，用base_dir + 相对路径的方式

import os


# base_dir = os.getcwd()
# print(base_dir)
# d = path.dirname(__file__)  #返回当前文件所在的目录
# abspath = path.abspath(d) #返回d所在目录规范的绝对路径
# 数据文件路径
# data_path = os.path.abspath(os.path.dirname(base_dir) + os.path.sep + ".") + '/data/'
# config_path = os.path.abspath(os.path.dirname(base_dir) + os.path.sep + ".") + '/config/'

print(os.environ['PUSHPATH'] )
data_path = os.environ['PUSHPATH'] + '/loans_drop/data/'
log_path = os.environ['PUSHPATH'] + '/loans_drop/log/'
config_path = os.environ['PUSHPATH'] + '/loans_drop/config/'
model_path = os.environ['PUSHPATH'] + '/loans_drop/model/'
print(data_path)
print(log_path)