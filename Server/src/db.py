import pymongo

class Mongo:
    
    # 登录注册部分
    def login_find(self, username):
        res = self.login.find_one({'username': username})
        return res
    
    def login_add(self, username, nickname, password):
        uid = self.max_uid = self.max_uid + 1
        data = {
            'username': username,
            'nickname': nickname,
            'password': password,
            'uid': uid
        }
        self.login.insert_one(data)
        return uid

    # 设置部分
    def setting_find(self, uid):
        res = self.login.find_one({'uid': uid})
        return res

    def setting_reset(self, find_res, item, data):
        uid = find_res['uid']
        find_res[item] = data
        cond = {'uid': uid}
        res = self.login.update_one(cond, {'$set': find_res})

    def __init__(self):
        self.client = pymongo.MongoClient(host='localhost')
        # 在此处更改 db 即可
        self.db = self.client.test
        # login part
        self.login = self.db.login
        self.max_uid = self.login.find_one(sort=[("uid",-1)])
        if self.max_uid is None:
            self.max_uid = 0

# login 部分: 包含每个用户的用户名,昵称,密码,uid 等信息