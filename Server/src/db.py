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

    def friend_add(self, src, dst):
        res = self.friend.find_one({'src': src, 'dst': dst})
        if res is not None:
            return
        data = {
            'src': src,
            'dst': dst,
            'accept': False
        }
        self.friend.insert_one(data)
        print('insert data {}'.format(data))
    
    def friend_find_remove_src(self, src, accept):
        l = []
        cond = {'src': src, 'accept': accept}
        results = self.friend.find(cond)
        for result in results:
            l.append(result)
        res = self.friend.remove(cond)
        print(res)
        return l

    def friend_find_remove_dst(self, dst, accept):
        l = []
        cond = {'dst': dst, 'accept': accept}
        results = self.friend.find(cond)
        for result in results:
            l.append(result)
        res = self.friend.remove(cond)
        print(res)
        return l

    def __init__(self):
        self.client = pymongo.MongoClient(host='localhost')
        # 在此处更改 db 即可
        self.db = self.client.test
        # login part
        self.login = self.db.login
        # friend request part
        self.friend = self.db.friend
        m = self.login.find_one(sort=[("uid",-1)])
        if m is None:
            self.max_uid = 0
        else:
            self.max_uid = m['uid']

# login 部分: 包含每个用户的用户名,昵称,密码,uid 等信息