package com.example.aiins

import android.app.Application
import android.content.Context
import android.net.Network
import com.example.aiins.util.FileUtil
import com.example.aiins.util.NetworkUtil

class AIApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FileUtil.init(this)
        context = this
        NetworkUtil.friendRemove(1, 3, false, NetworkUtil.emptyCallback)
    }


    companion object {
        private const val TAG = "AIApplication"
        lateinit var context: Context
    }
}