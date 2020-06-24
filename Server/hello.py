from flask import Flask
from flask import request
import proto.Register_pb2 as Register
from src.db import Mongo

app = Flask("AI ins")
mongo = Mongo()

@app.route('/login', methods=['POST'])
def login():
    req = Register.RegisterReq()
    data = request.data
    req.ParseFromString(data)
    print(req)

    rsp = Register.RegisterRsp()
    res = mongo.login_find(req.userName)
    
    # 不含昵称则是登录请求
    if req.nickName == '':
        if res is None or req.password != res['password']:
            rsp.resultCode = 2
        else:
            rsp.resultCode = 0
            rsp.customerId = res['uid']
    else: # 含有昵称是注册请求
        if res is None:
            rsp.resultCode = 0
            rsp.customerId = mongo.login_add(req.userName, req.nickName, req.password)
        else:
            rsp.resultCode = 1
    
    return rsp.SerializeToString()

@app.route('/')
def hello_world():
    return 'Hello World!'


'''
数据库使用 mongodb 存储
各个界面:
0. / 主界面, 暂无功能
1. /login 登录界面, 负责处理登录请求
2. /search 附近的人, 请求时返回
3. /talk 对话, 传入对话信息
4. /setting

resultCode:
1: 注册用户名重复
2: 登录密码错误或用户名不存在
'''