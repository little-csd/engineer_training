from flask import Flask
from flask import request
import proto.Register_pb2 as Register
import proto.Personal_pb2 as Personal
import proto.Friend_pb2 as Friend
from src.db import Mongo

app = Flask("AI ins")
mongo = Mongo()

# 注册登录相关部分页面与处理函数
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


# 用户设置相关部分页面与处理函数
@app.route('/setting', methods=['POST'])
def setting():
    req = Personal.SettingReq()
    data = request.data
    req.ParseFromString(data)

    errCode = 0
    msg = ""
    if req.type == 0:
        req = req.iconReq
        errCode,msg = process_iconreq(req)
    elif req.type == 1:
        req = req.nicknameReq
        errCode,msg = process_nicknamereq(req)
    elif req.type == 2:
        req = req.passwordReq
        errCode,msg = process_password(req)
    else:
        errCode = 3
        msg = "Unknown type"

    rsp = Personal.SettingRsp()
    rsp.resultCode = errCode
    rsp.msg = msg
    print(rsp)
    return rsp.SerializeToString()

def process_iconreq(req):
    print('process_iconreq')
    uid = req.uid
    data = req.icon
    res = mongo.setting_find(uid)
    if res is None:
        return 4,'uid not found'
    mongo.setting_reset(res, 'icon', data)
    return 0,'ok'

def process_nicknamereq(req):
    print('process_nicknamereq')
    uid = req.uid
    name = req.nickname
    res = mongo.setting_find(uid)
    if res is None:
        return 4,'uid not found'
    mongo.setting_reset(res, 'nickname', name)
    return 0,'ok'

# password 检查交给本地
def process_password(req):
    print('process_password')
    uid = req.uid
    new = req.new
    res = mongo.setting_find(uid)
    if res is None:
        return 4,'uid not found'
    mongo.setting_reset(res, 'password', new)
    return 0,'ok'

# 添加好友相关
@app.route('/friend', methods=['POST'])
def friend():
    req = Friend.FriendReq()
    data = request.data
    req.ParseFromString(data)
    if req.type == 0:
        req = req.searchUserReq
        return process_search_user(req)
    elif req.type == 1:
        req = req.addFriendReq
        print('process_friend_add')
        mongo.friend_add(req.src, req.dst)
        return ''
    elif req.type == 2:
        req = req.pullFriendReq
        return process_pull_friend_req(req)
    else:
        print('unknown error')
        return ''

def process_search_user(req):
    print('process_search_user')
    name = req.username
    res = mongo.login_find(name)
    rsp = Friend.SearchUserRsp()
    if res is None:
        rsp.resultCode = 1
    else:
        rsp.resultCode = 0
        rsp.nickname = res['nickname']
        rsp.username = res['username']
        rsp.uid = res['uid']
        if 'icon' in res.keys():
            rsp.icon = res['icon']
    return rsp.SerializeToString()

def process_pull_friend_req(req):
    print('process_pull_friend_req')
    uid = req.uid
    l1 = mongo.friend_find_remove_src(uid, True)
    l2 = mongo.friend_find_remove_dst(uid, False)
    rsp = Friend.PullAddFriendRsp()
    l = l1 + l2
    for r in l:
        _r = rsp.reqs.add()
        _r.src = r['src']
        _r.dst = r['dst']
        _r.isAccept = r['accept']
    return rsp.SerializeToString()

# 查询用户信息
@app.route('/user')
def user():
    return ''

# 最近的信息
@app.route('/search')
def search():
    return ''

@app.route('/')
def hello_world():
    return 'Welcome to AI Ins application'

'''
数据库使用 mongodb 存储
各个界面:
0. / 主界面, 暂无功能
1. /login 登录界面, 负责处理登录请求
2. /search 附近的人, 请求时返回
3. /setting 设置信息
4. /friend 添加好友
5. /user 查询用户信息

resultCode:
1: 注册用户名重复
2: 登录密码错误或用户名不存在
3: setting 请求 type 不合法
4: setting uid not found
5. friend type error
'''