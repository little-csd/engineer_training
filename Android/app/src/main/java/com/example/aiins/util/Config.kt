package com.example.aiins.util

import com.example.aiins.proto.Basic

object Config {
    private const val host = "http://192.168.101.65:5000"
    const val login = "$host/login"
    const val setting = "$host/setting"
    const val friend = "$host/friend"
    const val user = "$host/user"
    const val post = "$host/post"
    const val message = "$host/message"
    const val socket = "ws://192.168.101.65:5000"

    lateinit var userData: Basic.BasicUserData

    const val TYPE_ICON = 0
    const val TYPE_NICKNAME = 1
    const val TYPE_PASSWORD = 2

    const val TYPE_SEARCH = 0
    const val TYPE_ADD = 1
    const val TYPE_PULL = 2
    const val TYPE_REMOVE = 3

    const val POST_IMAGE = "post.png"

    fun getUserDataName(id: Int): String {
        return "UserData.$id"
    }

    fun getIconDataName(id: Int): String {
        return "icon$id.png"
    }
}