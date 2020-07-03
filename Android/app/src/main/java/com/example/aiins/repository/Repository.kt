package com.example.aiins.repository

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import com.example.aiins.AIApplication
import com.example.aiins.proto.Basic
import com.example.aiins.proto.Friend
import com.example.aiins.util.Config
import com.example.aiins.util.NetworkUtil
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.ArrayList

object Repository {

    const val TAG = "Repository"
    const val NET_ERR = "Network Error!"
    private val users = ConcurrentHashMap<Int, Basic.UserData>()
    private val icons = ConcurrentHashMap<Int, Bitmap>()
    private var onAddFriendReq : OnAddFriendReq? = null
    private val observers = ArrayList<Observer>()

    /**
     * 获取共三种情况:
     * 我发出去, 并且已被接受
     * 发给我的, 并且已被接受
     * 发给我的, 还未接受
     */
    private val pullCallback = object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
            Toast.makeText(AIApplication.context, NET_ERR, Toast.LENGTH_SHORT).show()
        }

        override fun onResponse(call: Call, response: Response) {
            val res = response.body!!.byteString().toByteArray()
            val rsp = Friend.PullAddFriendRsp.parseFrom(res)
            Log.i(TAG, "onResponse: $rsp")
            val id = Config.userData.uid
            val friends = ArrayList<Int>()
            val list = ArrayList<Int>()
            for (l in rsp.reqsList) {
                if (l.isAccept) {
                    if (l.src == id && !users.containsKey(l.dst)) friends.add(l.dst)
                    else if (l.dst == id && !users.containsKey(l.src)) friends.add(l.src)
                } else if (l.dst == id){
                    list.add(l.src)
                } else {
                    Log.e(TAG, "onResponse: error with rsp: $l")
                }
            }
            if (friends.isNotEmpty()) {
                NetworkUtil.userGet(friends, userCallback)
                Log.i(TAG, "onResponse: get ${friends.size} friends")
            }
            if (list.isNotEmpty()) {
                NetworkUtil.userGet(list, userAddCallback)
                Log.i(TAG, "onResponse: receive ${list.size} friends requests")
            }
        }
    }
    private val userCallback = object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
            Toast.makeText(AIApplication.context, NET_ERR, Toast.LENGTH_SHORT).show()
        }

        override fun onResponse(call: Call, response: Response) {
            val res = response.body!!.byteString().toByteArray()
            val rsp = Basic.UserDataRsp.parseFrom(res).userDataList
            for (r in rsp) {
                users[r.uid] = r
                if (!r.icon.isEmpty) {
                    val bytes = r.icon.toByteArray()
                    icons[r.uid] = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                }
            }
            Log.i(TAG, "onResponse: total ${users.size} friends")
            for (o in observers) {
                o.onDataSetChange()
            }
        }
    }
    private val userAddCallback = object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
            Toast.makeText(AIApplication.context, NET_ERR, Toast.LENGTH_SHORT).show()
        }

        override fun onResponse(call: Call, response: Response) {
            val res = response.body!!.byteString().toByteArray()
            val rsp = Basic.UserDataRsp.parseFrom(res).userDataList
            if (onAddFriendReq != null) {
                if (onAddFriendReq is Activity) {
                    val activity = onAddFriendReq as Activity
                    activity.runOnUiThread {
                        onAddFriendReq!!.onAdd(rsp, rsp.size)
                    }
                } else {
                    Log.i(TAG, "onResponse: not activity")
                }
            }
        }
    }

    fun init() {
//        Thread(this).start()
    }

    // init 前添加所有 observer
    fun pullFriend() {
        NetworkUtil.friendPull(pullCallback)
    }

    fun getAllFriends(): Set<Int> {
        return users.keys
    }

    fun findUserData(id: Int): Basic.UserData? {
        return users[id]
    }

    fun findIcon(id: Int): Bitmap? {
        return icons[id]
    }

    fun addFriendListener(onAddFriendReq: OnAddFriendReq) {
        this.onAddFriendReq = onAddFriendReq
    }

    fun addObserver(observer: Observer) {
        observers.add(observer)
    }

    fun removeObserver(observer: Observer) {
        observers.remove(observer)
    }

    interface OnAddFriendReq {
        fun onAdd(list: List<Basic.UserData>, len: Int)
    }

    interface Observer {
        fun onDataSetChange()
    }
}