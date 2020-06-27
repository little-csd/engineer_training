package com.example.aiins.util

import android.os.Handler

object BasicUtil {
    private lateinit var handler: Handler

    fun init() {
        handler = Handler()
    }

    fun runOnUIThread(r: () -> Unit) {
        handler.post(r)
    }
}