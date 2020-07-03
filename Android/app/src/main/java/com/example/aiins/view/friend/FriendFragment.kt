package com.example.aiins.view.friend

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aiins.R
import com.example.aiins.proto.Basic
import com.example.aiins.repository.Repository
import com.example.aiins.view.talk.TalkActivity
import kotlinx.android.synthetic.main.fragment_friend.*

class FriendFragment : Fragment(), Repository.Observer, FriendListAdapter.OnClickListener {

    private val adapter = FriendListAdapter()
    private val handler = Handler()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_friend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(context!!)
        friend_list.layoutManager = linearLayoutManager
        adapter.addListener(this)
        friend_list.adapter = adapter
    }

    override fun onDataSetChange() {
        val ll = ArrayList<Basic.UserData>()
        val key = Repository.getAllFriends()
        for (k in key) {
            ll.add(Repository.findUserData(k)!!)
        }
        handler.post {
            adapter.setData(ll)
        }
        Log.i("FriendFragment", "onDataSetChange: ${ll.size}")
    }

    override fun onClick(id: Int) {
        val intent = Intent(context!!, TalkActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }
}