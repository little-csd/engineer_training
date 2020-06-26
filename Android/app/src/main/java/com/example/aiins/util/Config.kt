package com.example.aiins.util

import com.example.aiins.proto.Basic

object Config {
    const val host = "http://192.168.31.229:5000"
    const val login = "$host/login"
    lateinit var userData: Basic.BasicUserData
    private const val userDataName = "UserData"

    fun getUserDataName(id: Int): String {
        return "$userDataName.$id"
    }
}