package com.example.aiins.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.aiins.MainActivity
import com.example.aiins.R
import com.example.aiins.proto.Basic
import com.example.aiins.proto.Register
import com.example.aiins.util.*
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class LoginActivity : BaseActivity(), Callback {

    private var submitting = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    private fun init() {
        btn_login_confirm.setOnClickListener {
            if (submitting) return@setOnClickListener
            val username = input_username.text.toString()
            val password = input_password.text.toString()

            when {
                username.isEmpty() -> {
                    Toast.makeText(this, "Username should not be empty", Toast.LENGTH_LONG).show()
                }
                password.isEmpty() -> {
                    Toast.makeText(this, "Password should not be empty", Toast.LENGTH_LONG).show()
                }
                else -> {
                    submitting = true
                    val req = Register.RegisterReq.newBuilder()
                    req.username = username
                    req.password = password
                    runOnUiThread {
                        Toast.makeText(this@LoginActivity, "Login...", Toast.LENGTH_SHORT).show()
                    }
                    NetworkUtil.login(req.build(), this)
                }
            }
        }
    }

    companion object {
        private const val TAG = "LoginActivity"
    }

    override fun onFailure(call: Call, e: IOException) {
        runOnUiThread {
            e.printStackTrace()
            Toast.makeText(this@LoginActivity, "Network error!", Toast.LENGTH_LONG).show()
            submitting = false
        }
    }

    override fun onResponse(call: Call, response: Response) {
        val res = response.body!!.byteString().toByteArray()
        val rsp = Register.RegisterRsp.parseFrom(res)

        runOnUiThread {
            if (rsp.resultCode == 0) {
                val data = FileUtil.readFileInFiles(Config.getUserDataName(rsp.uid))
                Config.userData = Basic.BasicUserData.parseFrom(data)
                Log.i(TAG, "LoginActivity: onResponse: create userdata ${Config.userData}")

                Toast.makeText(this@LoginActivity, "Login successfully!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } else {
                Toast.makeText(this@LoginActivity, RspCode.MSG[rsp.resultCode], Toast.LENGTH_SHORT).show()
                submitting = false
            }
        }
    }
}