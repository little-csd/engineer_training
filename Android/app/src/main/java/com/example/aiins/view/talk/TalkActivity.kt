package com.example.aiins.view.talk

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aiins.R
import com.example.aiins.proto.MessageOuterClass
import com.example.aiins.repository.Repository
import com.example.aiins.util.BaseActivity
import com.example.aiins.util.Config
import kotlinx.android.synthetic.main.activity_talk.*

class TalkActivity : BaseActivity() {

    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_talk)
        init()
    }

    private fun init() {
        id = intent.getIntExtra("id", -1)
        Log.i(TAG, "init: $id")

        val src = Config.userData
        val dst = Repository.findUserData(id)

        setSupportActionBar(toolbar_talk)
        toolbar_talk.setNavigationOnClickListener { finish() }
        toolbar_talk.title = dst!!.nickname

        val adapter = TalkListAdapter()
        talk_list.layoutManager = LinearLayoutManager(this)
        talk_list.adapter = adapter
        Thread {
            var count = 0
            var src = 0
            var dst = 0
            while (true) {
                Thread.sleep(500)
                if (count == 0) {
                    src = Config.userData.uid
                    dst = id
                } else {
                    src = id
                    dst = Config.userData.uid
                }
                count = (count + 1) % 2
                val msg = MessageOuterClass.Message.newBuilder()
                        .setSrc(src)
                        .setDst(dst)
                        .setText("Hello")
                        .build()
                runOnUiThread {
                    adapter.addMessage(msg)
                }
            }
        }.start()
    }

    companion object {
        const val TAG = "TalkActivity"
    }
}