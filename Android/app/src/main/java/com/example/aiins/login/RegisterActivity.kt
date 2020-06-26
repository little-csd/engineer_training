package com.example.aiins.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.aiins.BuildConfig
import com.example.aiins.MainActivity
import com.example.aiins.R
import com.example.aiins.proto.Basic
import com.example.aiins.proto.Register
import com.example.aiins.util.*
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class RegisterActivity : BaseActivity(), Callback {

    private var submitting = false
    private var nickname = ""
    private var username = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()
    }

    private fun init() {
        btn_register_confirm.setOnClickListener {
            if (submitting) return@setOnClickListener
            val nickname = input_nickname.text.toString()
            val username = input_username_reg.text.toString()
            val password = input_password_reg.text.toString()
            val password2 = input_password_reg2.text.toString()
            when {
                nickname.isEmpty() -> {
                    Toast.makeText(this, "Nickname should not be empty", Toast.LENGTH_LONG).show()
                }
                username.isEmpty() -> {
                    Toast.makeText(this, "Username should not be empty", Toast.LENGTH_LONG).show()
                }
                password != password2 -> {
                    Toast.makeText(this, "Password not match", Toast.LENGTH_LONG).show()
                }
                password.isEmpty() -> {
                    Toast.makeText(this, "Password should not be empty", Toast.LENGTH_LONG).show()
                }
                else -> {
                    submitting = true
                    val req = Register.RegisterReq.newBuilder()
                    req.nickname = nickname
                    req.username = username
                    req.password = password
                    this.nickname = nickname
                    this.username = username
                    this.password = password
                    runOnUiThread {
                        Toast.makeText(this@RegisterActivity, "Register...", Toast.LENGTH_SHORT).show()
                    }
                    NetworkUtil.login(req.build(), this)
                }
            }
        }
    }

    companion object {
        private const val TAG = "RegisterActivity"
    }

    override fun onFailure(call: Call, e: IOException) {
        runOnUiThread {
            e.printStackTrace()
            Toast.makeText(this@RegisterActivity, "Network error!", Toast.LENGTH_LONG).show()
            submitting = false
        }
    }

    override fun onResponse(call: Call, response: Response) {
        runOnUiThread {
            val res = response.body!!.byteString().toByteArray()
            val rsp = Register.RegisterRsp.parseFrom(res)

            if (rsp.resultCode == 0) {
                Toast.makeText(this@RegisterActivity, "Register successfully!", Toast.LENGTH_SHORT).show()

                val basicUserDataBuilder = Basic.BasicUserData.newBuilder()
                basicUserDataBuilder.nickname = nickname
                basicUserDataBuilder.username = username
                basicUserDataBuilder.password = password
                basicUserDataBuilder.uid = rsp.uid
                Config.userData = basicUserDataBuilder.build()

                if (BuildConfig.DEBUG && FileUtil.checkExistInFiles(Config.getUserDataName(rsp.uid))) {
                    error("Assertion failed")
                }
                FileUtil.writeFileInFiles(Config.userData.toByteArray(), Config.getUserDataName(rsp.uid))

                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } else {
                Toast.makeText(this@RegisterActivity, RspCode.MSG[rsp.resultCode], Toast.LENGTH_SHORT).show()
                submitting = false
            }
        }
    }
}