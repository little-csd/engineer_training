package com.example.aiins.view.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.aiins.MainActivity
import com.example.aiins.R
import com.example.aiins.proto.Basic
import com.example.aiins.proto.Personal
import com.example.aiins.util.Config
import com.example.aiins.util.FileUtil
import com.example.aiins.util.NetworkUtil
import kotlinx.android.synthetic.main.fragment_personal.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class HomeFragment : Fragment() {

    private var nickname = ""
    private var password = ""

    private val iconCallback = object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
            activity!!.runOnUiThread {
                Toast.makeText(activity, NET_ERR, Toast.LENGTH_SHORT).show()
            }
        }

        override fun onResponse(call: Call, response: Response) {
            val res = response.body!!.byteString().toByteArray()
            val rsp = Personal.SettingRsp.parseFrom(res)
            Log.i(TAG, "onResponse: $rsp")
            activity!!.runOnUiThread {
                if (rsp.resultCode != 0) {
                    Toast.makeText(activity, rsp.msg, Toast.LENGTH_SHORT).show()
                } else {
                    val bitmap = BitmapFactory.decodeFile(FileUtil.getPathInFile(Config.iconName))
                    if (bitmap == null) {
                        Toast.makeText(activity, "Unknown error", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(activity, "Set icon successfully!", Toast.LENGTH_SHORT).show()
                        home_icon.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }
    private val nicknameCallback = object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
            activity!!.runOnUiThread {
                Toast.makeText(activity, NET_ERR, Toast.LENGTH_SHORT).show()
            }
        }

        override fun onResponse(call: Call, response: Response) {
            val res = response.body!!.byteString().toByteArray()
            val rsp = Personal.SettingRsp.parseFrom(res)
            Log.i(TAG, "onResponse: $rsp")
            activity!!.runOnUiThread {
                if (rsp.resultCode != 0) {
                    Toast.makeText(activity, rsp.msg, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, "Change successfully!", Toast.LENGTH_SHORT).show()
                    val userData = Config.userData
                    val newUserData = Basic.BasicUserData.newBuilder()
                            .setNickname(nickname)
                            .setPassword(userData.password)
                            .setUid(userData.uid)
                            .setUsername(userData.username)
                            .build()
                    Config.userData = newUserData
                    FileUtil.writeFileInFiles(Config.userData.toByteArray(), Config.getUserDataName(userData.uid))
                    home_nickname.text = nickname
                }
            }
        }
    }
    private val passwordCallback = object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
            activity!!.runOnUiThread {
                Toast.makeText(activity, NET_ERR, Toast.LENGTH_SHORT).show()
            }
        }

        override fun onResponse(call: Call, response: Response) {
            val res = response.body!!.byteString().toByteArray()
            val rsp = Personal.SettingRsp.parseFrom(res)
            Log.i(TAG, "onResponse: $rsp")
            activity!!.runOnUiThread {
                if (rsp.resultCode != 0) {
                    Toast.makeText(activity, rsp.msg, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, "Change successfully!", Toast.LENGTH_SHORT).show()
                    val userData = Config.userData
                    val newUserData = Basic.BasicUserData.newBuilder()
                            .setNickname(userData.nickname)
                            .setPassword(password)
                            .setUid(userData.uid)
                            .setUsername(userData.username)
                            .build()
                    Config.userData = newUserData
                    FileUtil.writeFileInFiles(Config.userData.toByteArray(), Config.getUserDataName(userData.uid))
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_personal, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initIcon()
        initNickname()
        initPassword()
        home_username.text = "ID: ${Config.userData.username}"
    }

    private fun initIcon() {
        val bytes = FileUtil.readFileInFiles(Config.iconName)
        home_icon.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.size))
        home_icon.setOnClickListener {
            val activity = activity as MainActivity
            activity.onClickIcon()
        }
    }

    private fun initNickname() {
        home_nickname.text = Config.userData.nickname
        home_nickname.setOnClickListener {
            val et = EditText(context)
            et.setSingleLine()
            et.hint = "Input New Nickname"
            AlertDialog.Builder(context)
                    .setTitle("Change Nickname")
                    .setView(et)
                    .setPositiveButton("OK", DialogInterface.OnClickListener {
                        _, _ ->
                        val txt = et.text.toString()
                        if (txt.isEmpty()) {
                            Toast.makeText(context, FORMAT_ERR, Toast.LENGTH_SHORT).show()
                            return@OnClickListener
                        }
                        nickname = txt
                        NetworkUtil.settingNickname(nickname, nicknameCallback)
                    })
                    .setNegativeButton("Cancel") { _, _ -> }
                    .create()
                    .show()
        }
    }

    private fun initPassword() {
        home_password.setOnClickListener {
            val view = View.inflate(context, R.layout.reset_pwd, null)
            val ed1 = view.findViewById<EditText>(R.id.reset_pwd_ed1)
            val ed2 = view.findViewById<EditText>(R.id.reset_pwd_ed2)
            val ed3 = view.findViewById<EditText>(R.id.reset_pwd_ed3)
            AlertDialog.Builder(context)
                    .setTitle("Reset password")
                    .setView(view)
                    .setPositiveButton("OK") { _, _ ->
                        checkAndReset(ed1.text.toString(), ed2.text.toString(), ed3.text.toString())
                    }
                    .setNegativeButton("Cancel") { _, _ -> }
                    .create()
                    .show()
        }
    }

    private fun checkAndReset(old: String, new1: String, new2: String) {
//        Log.i(TAG, "checkAndReset: $old $new1 $new2")
        if (old.isEmpty() || new1.isEmpty() || new2.isEmpty()) {
            Toast.makeText(context, FORMAT_ERR, Toast.LENGTH_SHORT).show()
            return
        }
        if (new1 != new2) {
            Toast.makeText(context, "New password not equal", Toast.LENGTH_SHORT).show()
            return
        }
        if (old != Config.userData.password) {
            Toast.makeText(context, "Wrong password", Toast.LENGTH_SHORT).show()
            return
        }
        password = new1
        NetworkUtil.settingPassword(old, new1, passwordCallback)
    }

    fun onIconOK() {
        val bytes = FileUtil.readFileInFiles(Config.iconName)
        NetworkUtil.settingIcon(bytes, iconCallback)
    }

    companion object {
        const val TAG = "HomeFragment"
        const val NET_ERR = "Network error!"
        const val FORMAT_ERR = "Format error!"
    }
}