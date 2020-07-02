package com.example.aiins.view.talk

import android.os.Bundle
import android.util.Log
import com.example.aiins.R
import com.example.aiins.util.BaseActivity
import kotlinx.android.synthetic.main.activity_talk.*

class TalkActivity : BaseActivity() {

    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_talk)
        init()
    }

    private fun init() {
        setSupportActionBar(toolbar_talk)
        id = intent.getIntExtra("id", -1)
        Log.i(TAG, "init: $id")
    }

    companion object {
        const val TAG = "TalkActivity"
    }
}