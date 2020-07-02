package com.example.aiins.view.friend

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aiins.R
import com.example.aiins.proto.Basic
import com.example.aiins.repository.Repository

class FriendListAdapter : RecyclerView.Adapter<FriendListAdapter.ViewHolder>() {

    private var list = ArrayList<Basic.UserData>()
    private var listener: OnClickListener? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var icon: ImageView = view.findViewById(R.id.friend_list_icon)
        var txt: TextView = view.findViewById(R.id.friend_list_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.friend_item, parent, false)
        val holder = ViewHolder(view)
        holder.txt.setOnClickListener {
            val id = list[holder.adapterPosition].uid
            listener?.onClick(id)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        val icon = Repository.findIcon(data.uid)
        if (icon == null) holder.icon.setImageResource(R.drawable.user)
        else holder.icon.setImageBitmap(icon)
        holder.txt.text = data.nickname
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(list: ArrayList<Basic.UserData>) {
        Log.i("Fragment", "setData: ${list.size}")
        this.list = list
        notifyDataSetChanged()
    }

    interface OnClickListener {
        fun onClick(id: Int)
    }

    fun addListener(listener: OnClickListener) {
        this.listener = listener
    }
}