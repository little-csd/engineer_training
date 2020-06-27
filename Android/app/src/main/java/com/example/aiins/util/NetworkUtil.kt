package com.example.aiins.util

import com.example.aiins.proto.Personal
import com.example.aiins.proto.Register
import com.google.protobuf.ByteString
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

object NetworkUtil {

    private val okHttpClient = OkHttpClient()
    private const val TAG = "NetworkUtil"

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
}