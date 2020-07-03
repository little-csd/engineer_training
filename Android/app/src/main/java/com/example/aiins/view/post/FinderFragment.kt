package com.example.aiins.view.post

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aiins.AIApplication
import com.example.aiins.R
import com.example.aiins.proto.MessageOuterClass
import com.example.aiins.repository.Repository
import com.example.aiins.util.NetworkUtil
import kotlinx.android.synthetic.main.fragment_finder.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class FinderFragment : Fragment() {

    private var lastCall = 0L
    private var newestTime = 0
    private val adapter = PostListAdapter()

    private val callback = object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
            Toast.makeText(AIApplication.context, Repository.NET_ERR, Toast.LENGTH_SHORT).show()
            refresh.finishRefresh(false)
        }

        override fun onResponse(call: Call, response: Response) {
            val req = response.body!!.byteString().toByteArray()
            val rsp = MessageOuterClass.PostRsp.parseFrom(req)
            Log.i(TAG, "onResponse: ${rsp.postsList.size}")
            activity!!.runOnUiThread {
                refresh.finishRefresh(true)
                adapter.addPost(rsp.postsList)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_finder, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        refresh.setOnRefreshListener {
            val current = System.currentTimeMillis()
            if (current - lastCall < 3000) { it.finishRefresh(false) }
            lastCall = current
            val req = MessageOuterClass.PostReq.newBuilder()
                    .setType(0)
                    .setTime(adapter.getNewest())
                    .build()
            NetworkUtil.post(req, callback)
        }
        val linearLayoutManager = LinearLayoutManager(activity)
        find_recycler.layoutManager = linearLayoutManager
        find_recycler.adapter = adapter
        val decoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(activity!!.resources.getDrawable(R.drawable.post_divider))
        find_recycler.addItemDecoration(decoration)
    }

    companion object {
        const val TAG = "FinderFragment"
    }
}