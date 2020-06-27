package com.example.aiins.util

import com.example.aiins.proto.Basic

object Config {
    const val host = "http://192.168.101.65:5000"
    const val login = "$host/login"
    const val setting = "$host/setting"
    const val iconName = "icon.png"

    lateinit var userData: Basic.BasicUserData
    private const val userDataName = "UserData"

    const val TYPE_ICON = 0
    const val TYPE_NICKNAME = 1
    const val TYPE_PASSWORD = 2

    fun getUserDataName(id: Int): String {
        return "$userDataName.$id"
    }
}