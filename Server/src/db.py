import pymongo

class Mongo:
    
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

    def __init__(self):
        self.client = pymongo.MongoClient(host='localhost')
        # 在此处更改 db 即可
        self.db = self.client.test
        # login part
        self.login = self.db.login
        self.max_uid = self.login.find_one(sort=[("uid",-1)])
        if self.max_uid is None:
            self.max_uid = 0
