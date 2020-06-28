package com.example.aiins

import android.app.Application
import android.util.Log
import com.example.aiins.util.BasicUtil
import com.example.aiins.util.FileUtil

class AIApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FileUtil.init(this)
        BasicUtil.init()
    }


    companion object {
        private const val TAG = "AIApplication"
    }
}