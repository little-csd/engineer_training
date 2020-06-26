package com.example.aiins.util

object RspCode {
    const val OK = 0
    const val DUPLICATED_USERNAME = 1
    const val WRONG_PASSWORD = 2

    val MSG = arrayOf("OK", "Username Registered", "Wrong password or Username not found")
}