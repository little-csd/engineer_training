package com.example.aiins.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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

    private val callback = object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
            Toast.makeText(AIApplication.context, Repository.NET_ERR, Toast.LENGTH_SHORT).show()
        }

        override fun onResponse(call: Call, response: Response) {
            val req = response.body!!.byteString().toByteArray()
            val rsp = MessageOuterClass.PostRsp.parseFrom(req)
            for (post in rsp.postsList) {
                Log.i(TAG, "onResponse: ${post.text}")
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_finder, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        refresh.setOnRefreshListener {
            val req = MessageOuterClass.PostReq.newBuilder()
                    .setType(0)
                    .setTime(10)
                    .build()
            NetworkUtil.post(req, callback)
            it.finishRefresh(true)
        }
    }

    companion object {
        const val TAG = "FinderFragment"
    }
}