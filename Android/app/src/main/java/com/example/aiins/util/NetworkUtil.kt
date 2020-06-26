package com.example.aiins.util

import com.example.aiins.proto.Register
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
}