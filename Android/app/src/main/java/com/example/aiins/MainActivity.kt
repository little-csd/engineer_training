package com.example.aiins

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.aiins.util.BaseActivity
import com.example.aiins.util.Config
import com.example.aiins.view.FinderFragment
import com.example.aiins.view.FriendFragment
import com.example.aiins.view.HomeFragment
import com.example.aiins.view.TalkFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "onCreate: ${Config.userData}")
        init()
    }

    private fun init() {
        val list = arrayOf(TalkFragment(), FriendFragment(), FinderFragment(), HomeFragment())
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

    companion object {
        private const val BACK_PRESS_INTERVAL = 1500
        private const val TAG = "MainActivity"
    }
}