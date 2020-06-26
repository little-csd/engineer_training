from flask import Flask
from flask import request
import proto.Register_pb2 as Register
import proto.Personal_pb2 as Personal
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
    res = mongo.login_find(req.username)
    
    # 不含昵称则是登录请求
    if req.nickname == '':
        if res is None or req.password != res['password']:
            rsp.resultCode = 2
        else:
            rsp.resultCode = 0
            rsp.uid = res['uid']
    else: # 含有昵称是注册请求
        if res is None:
            rsp.resultCode = 0
            rsp.uid = mongo.login_add(req.username, req.nickname, req.password)
        else:
            rsp.resultCode = 1
    
    return rsp.SerializeToString()

@app.route('/setting', methods=['POST'])
def setting():
    req = Personal.SettingReq()
    data = request.data
    req.ParseFromString(data)
    print(req)

    errCode = 0
    msg = ""
    if req.type == 0:
        req = req.iconReq
        print()
    elif req.type == 1:
        req = req.nicknameReq
        print()
    elif req.type == 2:
        req = req.passwordReq
        print()
    else:
        print("Error")

    rsp = Personal.SettingRsp()
    rsp.resultCode = errCode
    rsp.msg = msg

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
4. /setting 设置信息
5. /friend 添加好友

resultCode:
1: 注册用户名重复
2: 登录密码错误或用户名不存在
'''