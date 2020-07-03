package com.example.aiins.view.post

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aiins.R
import com.example.aiins.proto.MessageOuterClass
import com.example.aiins.view.post.FinderFragment.Companion.TAG

class PostListAdapter : RecyclerView.Adapter<PostListAdapter.ViewHolder>() {

    private var posts = ArrayList<MessageOuterClass.Post>()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var icon: ImageView = view.findViewById(R.id.post_item_icon)
        var name: TextView = view.findViewById(R.id.post_item_name)
        var content: TextView = view.findViewById(R.id.post_item_content)
        var image: ImageView = view.findViewById(R.id.post_item_image)
        var desc: TextView = view.findViewById(R.id.post_item_desc)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.post_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        val icon = post.icon.toByteArray()
        if (icon.isEmpty()) {
            holder.icon.setImageResource(R.drawable.user)
        } else {
            holder.icon.setImageBitmap(BitmapFactory.decodeByteArray(icon, 0, icon.size))
        }
        holder.name.text = "${post.username} (${post.nickname})"
        holder.content.text = post.text
        val img = post.image.toByteArray()
        if (img.isNotEmpty()) {
            holder.image.setImageBitmap(BitmapFactory.decodeByteArray(img, 0, img.size))
        } else {
            holder.image.background = null
        }
        holder.desc.text = post.desc
    }

    fun addPost(data: List<MessageOuterClass.Post>) {
        posts.addAll(data)
        posts.sortByDescending { it.time }
        notifyDataSetChanged()
    }

    fun getNewest(): Int{
        var res = 0
        for (post in posts) {
            if (post.time > res) {
                res = post.time
            }
        }
        return res
    }
}