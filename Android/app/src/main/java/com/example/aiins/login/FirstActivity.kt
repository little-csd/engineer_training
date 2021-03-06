package com.example.aiins.login

import android.content.Intent
import android.os.Bundle
import com.example.aiins.R
import com.example.aiins.util.BaseActivity
import kotlinx.android.synthetic.main.activity_first.*

class FirstActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
        initView()
    }

    private fun initView() {
        btn_login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        btn_register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}