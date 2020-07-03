package com.example.aiins.view.talk

import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.aiins.R
import com.example.aiins.proto.MessageOuterClass.Message
import com.example.aiins.repository.Repository
import com.example.aiins.repository.WebRepository
import com.example.aiins.util.Config

class TalkListAdapter: RecyclerView.Adapter<TalkListAdapter.ViewHolder>() {

    private val msgList = ArrayList<Message>()
    private val id = Config.userData.uid
    private var recyclerView: RecyclerView? = null
    private val handler = Handler()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var iconLeft: ImageView = view.findViewById(R.id.talk_icon_left)
        var iconRight: ImageView = view.findViewById(R.id.talk_icon_right)
        var txtLeft: TextView = view.findViewById(R.id.talk_text_left)
        var txtRight: TextView = view.findViewById(R.id.talk_text_right)
        var talkLeft: LinearLayout = view.findViewById(R.id.talk_left)
        var talkRight: LinearLayout = view.findViewById(R.id.talk_right)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.talk_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val msg = msgList[position]
        if (isRight(msg)) {
            holder.talkLeft.visibility = View.GONE
            holder.talkRight.visibility = View.VISIBLE
            setIconOrDefault(id, holder.iconRight)
            holder.txtRight.text = msg.text
        } else {
            holder.talkLeft.visibility = View.VISIBLE
            holder.talkRight.visibility = View.GONE
            setIconOrDefault(msg.src, holder.iconLeft)
            holder.txtLeft.text = msg.text
        }
    }

    override fun getItemCount(): Int {
        return msgList.size
    }

    private fun isRight(msg: Message): Boolean {
        return id == msg.src
    }

    fun addMessage(msg: Message) {
        msgList.add(msg)
        notifyDataSetChanged()
        recyclerView?.scrollToPosition(msgList.size - 1)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    private fun setIconOrDefault(id: Int, img: ImageView) {
        val bitmap = Repository.findIcon(id)
        if (bitmap == null) img.setImageResource(R.drawable.user)
        else img.setImageBitmap(bitmap)
    }
}