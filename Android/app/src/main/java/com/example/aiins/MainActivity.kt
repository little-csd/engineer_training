package com.example.aiins

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.aiins.proto.Friend
import com.example.aiins.util.BaseActivity
import com.example.aiins.util.Config
import com.example.aiins.util.FileUtil
import com.example.aiins.util.NetworkUtil
import com.example.aiins.view.AddFriendActivity
import com.example.aiins.view.FinderFragment
import com.example.aiins.view.FriendFragment
import com.example.aiins.view.TalkFragment
import com.example.aiins.view.home.HomeFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.yalantis.ucrop.UCrop
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class MainActivity : BaseActivity() {

    val list = arrayOf(TalkFragment(), FriendFragment(), FinderFragment(), HomeFragment())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        reqPermission()
        Log.i(TAG, "onCreate: ${Config.userData}")
        init()
    }

    private val pullCallback = object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.i(TAG, "onFailure: ")
            e.printStackTrace()
        }

        override fun onResponse(call: Call, response: Response) {
            Log.i(TAG, "onResponse: ")
            val res = response.body!!.byteString().toByteArray()
            val rsp = Friend.PullAddFriendRsp.parseFrom(res)
            for (l in rsp.reqsList) {
                Log.i(TAG, "onResponse: $l")
            }
        }
    }

    private fun init() {
        setSupportActionBar(toolbar)
        viewpager.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                return list[position]
            }
            override fun getItemCount(): Int {
                return list.size
            }
        }
        TabLayoutMediator(bottom_tab, viewpager, true, TabLayoutMediator.TabConfigurationStrategy {
            tab, position ->
            tab.setCustomView(R.layout.tab_icon)
            val img = tab.view.findViewById<ImageView>(R.id.tab_icon_img)
            val txt = tab.view.findViewById<TextView>(R.id.tab_icon_txt)
            when (position) {
                0 -> {
                    img.setImageResource(R.drawable.ic_chat)
                    txt.text = "chat"
                }
                1 -> {
                    img.setImageResource(R.drawable.ic_people)
                    txt.text = "people"
                }
                2 -> {
                    img.setImageResource(R.drawable.ic_search)
                    txt.text = "search"
                }
                3 -> {
                    img.setImageResource(R.drawable.ic_home)
                    txt.text = "center"
                }
            }
        }).attach()
        // TODO: Move to other place
        Log.i(TAG, "init: here")
        NetworkUtil.friendPull(pullCallback)
    }

    fun onClickIcon() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        startActivityForResult(intent, REQ_PICK_PHOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return
        when (requestCode) {
            REQ_PICK_PHOTO -> {
                val uri = FileUtil.getUriInFile(Config.iconName)
//                val options = UCrop.Options()
                UCrop.of(data!!.data!!, uri)
                        .withAspectRatio(1f, 1f)
                        .withMaxResultSize(300, 300)
                        .start(this)
            }
            UCrop.REQUEST_CROP -> {
                val fragment = list[3] as HomeFragment
                fragment.onIconOK()
            }
        }
    }

    private fun reqPermission() {
        val list = ArrayList<String>()
        for (str in PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, str) != PackageManager.PERMISSION_GRANTED) {
                list.add(str)
            }
        }
        if (list.isEmpty()) return
        ActivityCompat.requestPermissions(this, list.toTypedArray(), REQ_PERMISSION_CODE)
    }

    private var lastBack = 0L
    override fun onBackPressed() {
        val back = System.currentTimeMillis()
        if (back - lastBack > BACK_PRESS_INTERVAL) {
            lastBack = back
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show()
        } else {
            finish()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != REQ_PERMISSION_CODE) {
            return
        }
        for (str in grantResults) {
            if (str != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "App can not work", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add_friend -> {
                val intent = Intent(this, AddFriendActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }

    companion object {
        private const val BACK_PRESS_INTERVAL = 1500
        private const val TAG = "MainActivity"
        private const val REQ_PERMISSION_CODE = 0
        private const val REQ_PICK_PHOTO = 1
        private val PERMISSIONS = arrayOf<String>(Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    }
}