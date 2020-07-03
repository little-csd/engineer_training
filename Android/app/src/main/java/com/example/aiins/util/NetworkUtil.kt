package com.example.aiins.util

import android.util.Log
import com.example.aiins.proto.*
import com.google.protobuf.ByteString
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

object NetworkUtil {

    private val okHttpClient = OkHttpClient()
    private const val TAG = "NetworkUtil"
    val emptyCallback = object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.i(TAG, "onFailure: ")
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.code != 200) {
                Log.i(TAG, "onResponse: failed")
            } else {
                Log.i(TAG, "onResponse: success")
            }
        }
    }

    fun login(req: Register.RegisterReq, callback: Callback) {
        val request = Request.Builder()
                .url(Config.login)
                .post(req.toByteArray().toRequestBody())
                .build()
        val call = okHttpClient.newCall(request)
        call.enqueue(callback)
    }

    private fun setting(req: Personal.SettingReq, callback: Callback) {
        val request = Request.Builder()
                .url(Config.setting)
                .post(req.toByteArray().toRequestBody())
                .build()
        val call = okHttpClient.newCall(request)
        call.enqueue(callback)
    }

    fun settingNickname(str: String, callback: Callback) {
        val req = Personal.SetNicknameReq.newBuilder()
                .setUid(Config.userData.uid)
                .setNickname(str)
                .build()
        val req2 = Personal.SettingReq.newBuilder()
                .setType(Config.TYPE_NICKNAME)
                .setNicknameReq(req)
                .build()
        setting(req2, callback)
    }

    fun settingPassword(old: String, new: String, callback: Callback) {
        val req = Personal.SetPasswordReq.newBuilder()
                .setUid(Config.userData.uid)
                .setOld(old)
                .setNew(new)
                .build()
        val req2 = Personal.SettingReq.newBuilder()
                .setType(Config.TYPE_PASSWORD)
                .setPasswordReq(req)
                .build()
        setting(req2, callback)
    }

    fun settingIcon(bytes: ByteArray, callback: Callback) {
        val req = Personal.SetIconReq.newBuilder()
                .setUid(Config.userData.uid)
                .setIcon(ByteString.copyFrom(bytes))
                .build()
        val req2 = Personal.SettingReq.newBuilder()
                .setType(Config.TYPE_ICON)
                .setIconReq(req)
                .build()
        setting(req2, callback)
    }

    private fun friend(req: Friend.FriendReq, callback: Callback) {
        val request = Request.Builder()
                .url(Config.friend)
                .post(req.toByteArray().toRequestBody())
                .build()
        val call = okHttpClient.newCall(request)
        call.enqueue(callback)
    }

    fun friendSearch(name: String, callback: Callback) {
        val req = Friend.SearchUserReq.newBuilder()
                .setUsername(name)
                .build()
        val req2 = Friend.FriendReq.newBuilder()
                .setType(Config.TYPE_SEARCH)
                .setSearchUserReq(req)
                .build()
        friend(req2, callback)
    }

    fun friendAdd(src: Int, dst: Int, callback: Callback) {
        val req = Friend.AddFriendReq.newBuilder()
                .setSrc(src)
                .setDst(dst)
                .setIsAccept(false)
                .build()
        val req2 = Friend.FriendReq.newBuilder()
                .setType(Config.TYPE_ADD)
                .setAddFriendReq(req)
                .build()
        friend(req2, callback)
    }

    fun friendPull(callback: Callback) {
        val req = Friend.PullAddFriendReq.newBuilder()
                .setUid(Config.userData.uid)
                .build()
        val req2 = Friend.FriendReq.newBuilder()
                .setType(Config.TYPE_PULL)
                .setPullAddFriendReq(req)
                .build()
        friend(req2, callback)
    }

    fun friendRemove(src: Int, dst: Int, isAccept: Boolean, callback: Callback) {
        val req = Friend.RemoveFriendReq.newBuilder()
                .setSrc(src)
                .setDst(dst)
                .setIsAccept(isAccept)
                .build()
        val req2 = Friend.FriendReq.newBuilder()
                .setType(Config.TYPE_REMOVE)
                .setRemoveFriendReq(req)
                .build()
        friend(req2, callback)
    }

    fun userGet(list: List<Int>, callback: Callback) {
        val req = Basic.UserDataReq.newBuilder()
                .addAllUid(list)
                .build()
        val request = Request.Builder()
                .url(Config.user)
                .post(req.toByteArray().toRequestBody())
                .build()
        val call = okHttpClient.newCall(request)
        call.enqueue(callback)
    }

    fun messageReq(uid: Int, time: Int, callback: Callback) {
        val req = MessageOuterClass.MessageReq.newBuilder()
                .setUid(uid)
                .setTime(time)
                .build()
        val request = Request.Builder()
                .url(Config.message)
                .post(req.toByteArray().toRequestBody())
                .build()
        val call = okHttpClient.newCall(request)
        call.enqueue(callback)
    }
}