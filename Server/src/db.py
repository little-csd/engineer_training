import pymongo

class Mongo:
    
    def login_find(self, username):
        res = self.login.find_one({'username': username})
        return res
    
    def login_add(self, username, nickname, password):
        self.max_id += 1
        data = {
            'username': username,
            'nickname': nickname,
            'password': password,
            'uid': self.max_id
        }
        self.login.insert_one(data)
        return self.max_id

    def __init__(self):
        self.client = pymongo.MongoClient(host='localhost')
        self.db = self.client.test
        # login part
        self.login = self.db.login
        self.max_id = self.login.find_one(sort=[("uid",-1)])
        if self.max_id is None:
            self.max_id = 0
