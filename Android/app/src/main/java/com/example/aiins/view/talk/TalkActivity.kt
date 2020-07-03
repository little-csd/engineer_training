package com.example.aiins.view.talk

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aiins.R
import com.example.aiins.proto.MessageOuterClass
import com.example.aiins.repository.Repository
import com.example.aiins.repository.WebRepository
import com.example.aiins.util.BaseActivity
import com.example.aiins.util.Config
import com.example.aiins.util.NetworkUtil
import kotlinx.android.synthetic.main.activity_talk.*
import java.lang.StringBuilder
import java.util.EnumSet.range

class TalkActivity : BaseActivity(), WebRepository.Observer {

    private var id = 0
    private val adapter = TalkListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_talk)
        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        WebRepository.removeObserver(this)
    }

    private fun init() {
        id = intent.getIntExtra("id", -1)
        Log.i(TAG, "init: $id")

        val src = Config.userData
        val dst = Repository.findUserData(id)

        setSupportActionBar(toolbar_talk)
        toolbar_talk.setNavigationOnClickListener { finish() }
        toolbar_talk.title = dst!!.nickname

        talk_friend_ok.setOnClickListener {
            val txt = talk_friend_text.text.toString()
            if (txt.isEmpty()) return@setOnClickListener
            talk_friend_text.text.clear()

            val msg = MessageOuterClass.Message.newBuilder()
                    .setSrc(Config.userData.uid)
                    .setDst(id)
                    .setText(txt)
                    .setTime((System.currentTimeMillis()/1000).toInt())
                    .build()
            adapter.addMessage(msg)
            Repository.addMessage(msg)
            WebRepository.send(msg.toByteArray())
        }

        for (msg in Repository.getMessage(id)) {
            adapter.addMessage(msg)
        }
        talk_list.layoutManager = LinearLayoutManager(this)
        talk_list.adapter = adapter
        WebRepository.addObserver(this)
        test()
    }

    override fun onMessage(msg: MessageOuterClass.Message) {
        if (msg.src == id || msg.dst == id) {
            runOnUiThread {
                adapter.addMessage(msg)
            }
        }
    }

    private fun get(count: Int): String {
        val str = "l"
        val builder = StringBuilder()
        for (i in 0 until count) {
            builder.append(str)
        }
        return builder.toString()
    }

    private fun test() {
        Thread {
            var count = 0
            var src = 0
            var dst = 0
            while (true) {
                Thread.sleep(500)
                if (count % 2 == 0) {
                    src = Config.userData.uid
                    dst = id
                } else {
                    src = id
                    dst = Config.userData.uid
                }
                count += 1
                val msg = MessageOuterClass.Message.newBuilder()
                        .setSrc(src)
                        .setDst(dst)
                        .setText(get(count))
                        .build()
                runOnUiThread {
                    adapter.addMessage(msg)
                    Thread.sleep(100)
                }
            }
        }.start()
    }

    companion object {
        const val TAG = "TalkActivity"
    }
}