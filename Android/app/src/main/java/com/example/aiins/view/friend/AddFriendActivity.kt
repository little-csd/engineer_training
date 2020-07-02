package com.example.aiins.view.friend

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.aiins.R
import com.example.aiins.proto.Friend
import com.example.aiins.util.Config
import com.example.aiins.util.NetworkUtil
import kotlinx.android.synthetic.main.activity_add_friend.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class AddFriendActivity : AppCompatActivity() {

    private var uid = 0

    private val searchCallback = object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            processing = false
            runOnUiThread {
                Toast.makeText(this@AddFriendActivity, NET_ERR, Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }

        override fun onResponse(call: Call, response: Response) {
            processing = false
            val res = response.body!!.byteString().toByteArray()
            val rsp = Friend.SearchUserRsp.parseFrom(res)
            runOnUiThread {
                if (rsp.resultCode != 0) {
                    Toast.makeText(this@AddFriendActivity, "User not found", Toast.LENGTH_SHORT).show()
                    return@runOnUiThread
                }
                Toast.makeText(this@AddFriendActivity, "User found!", Toast.LENGTH_SHORT).show()
                uid = rsp.uid
                home_username_add.text = "ID: ${rsp.username}"
                home_nickname_add.text = rsp.nickname
                if (!rsp.icon.isEmpty) {
                    val icon = rsp.icon.toByteArray()
                    home_icon_add.setImageBitmap(BitmapFactory.decodeByteArray(icon, 0, icon.size))
                } else {
                    home_icon_add.setImageResource(R.drawable.user)
                }
                home_card_add.visibility = View.VISIBLE
            }
        }
    }
    private val addCallback = object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            processing = false
            runOnUiThread {
                Toast.makeText(this@AddFriendActivity, NET_ERR, Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }

        override fun onResponse(call: Call, response: Response) {
            processing = false
            runOnUiThread {
                if (response.code == 200) {
                    Toast.makeText(this@AddFriendActivity, "Send request successfully!", Toast.LENGTH_SHORT).show()
                    return@runOnUiThread
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friend)
        setSupportActionBar(toolbar_add_friend)
        init()
    }

    private var processing = false
    private fun init() {
        add_friend_ok.setOnClickListener {
            if (processing) return@setOnClickListener
            val txt = add_friend_search.text.toString()
            if (txt.isEmpty()) {
                Toast.makeText(this@AddFriendActivity, "Text is empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (txt == Config.userData.username) {
                Toast.makeText(this@AddFriendActivity, "Can't add yourself", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            processing = true
            NetworkUtil.friendSearch(txt, searchCallback)
        }
        home_card_add.setOnClickListener {
            if (processing) return@setOnClickListener
            if (uid == 0) {
                Log.i(TAG, "state error")
                return@setOnClickListener
            }
            processing = true
            NetworkUtil.friendAdd(Config.userData.uid, uid, addCallback)
        }
    }

    companion object {
        const val TAG = "AddFriendActivity"
        const val NET_ERR = "Network error!"
    }
}