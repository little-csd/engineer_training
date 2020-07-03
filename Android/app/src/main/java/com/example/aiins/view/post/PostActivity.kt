package com.example.aiins.view.post

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.aiins.R
import com.example.aiins.proto.MessageOuterClass
import com.example.aiins.repository.Repository
import com.example.aiins.util.BaseActivity
import com.example.aiins.util.Config
import com.example.aiins.util.FileUtil
import com.example.aiins.util.NetworkUtil
import com.google.protobuf.ByteString
import com.yalantis.ucrop.UCrop
import kotlinx.android.synthetic.main.activity_post.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class PostActivity : BaseActivity() {

    private var lastImage: ByteArray? = null
    private var last2Image: ByteArray? = null
    private val postCallback = object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
            runOnUiThread {
                Toast.makeText(this@PostActivity, Repository.NET_ERR, Toast.LENGTH_SHORT).show()
            }
        }

        override fun onResponse(call: Call, response: Response) {
            runOnUiThread {
                Toast.makeText(this@PostActivity, "Post successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        setSupportActionBar(toolbar_post)
        lastImage = null
        last2Image = null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_post, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_post_send -> {
                Log.i("PostActivity", "onOptionsItemSelected: ${mode_normal.isChecked}")
                val id = Config.userData.uid
                val txt = post_text.text.toString()
                if (txt.isEmpty()) {
                    Toast.makeText(this, "Text should not be empty!", Toast.LENGTH_SHORT).show()
                    return true
                }
                Log.i(TAG, "onOptionsItemSelected: $txt")
                if (mode_normal.isChecked) {
                    val builder = MessageOuterClass.PostReq.newBuilder()
                            .setType(MODE_NORMAL)
                            .setUid(id)
                            .setTime((System.currentTimeMillis()/1000).toInt())
                            .setText(txt)
                    if (lastImage != null) {
                        NetworkUtil.post(builder.setImg1(ByteString.copyFrom(lastImage)).build(),
                                postCallback)
                    } else {
                        NetworkUtil.post(builder.build(), postCallback)
                    }
                    finish()
                } else if (mode_transfer.isChecked) {
                    if (last2Image == null) {
                        Toast.makeText(this, "At least two pictures are required!", Toast.LENGTH_SHORT).show()
                        return true
                    }
                    val req = MessageOuterClass.PostReq.newBuilder()
                            .setType(MODE_TRANSFER)
                            .setUid(id)
                            .setTime((System.currentTimeMillis()/1000).toInt())
                            .setText(txt)
                            .setImg1(ByteString.copyFrom(lastImage))
                            .setImg2(ByteString.copyFrom(last2Image))
                            .build()
                    NetworkUtil.post(req, postCallback)
                    finish()
                } else if (mode_2text.isChecked) {
                    if (lastImage == null) {
                        Toast.makeText(this, "At least one picture is required!", Toast.LENGTH_SHORT).show()
                        return true
                    }
                    val req = MessageOuterClass.PostReq.newBuilder()
                            .setType(MODE_TO_TEXT)
                            .setUid(id)
                            .setTime((System.currentTimeMillis()/1000).toInt())
                            .setText(txt)
                            .setImg1(ByteString.copyFrom(lastImage))
                            .build()
                    NetworkUtil.post(req, postCallback)
                    finish()
                } else {
                    Toast.makeText(this, "At least choose one type", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.action_post_image -> {
                val intent = Intent(Intent.ACTION_PICK)
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                startActivityForResult(intent, REQ_PICK_PHOTO)
            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return
        when (requestCode) {
            REQ_PICK_PHOTO -> {
                val uri = FileUtil.getUriInFile(Config.POST_IMAGE)
                UCrop.of(data!!.data!!, uri)
                        .withMaxResultSize(500, 500)
                        .start(this)
            }
            UCrop.REQUEST_CROP -> {
                last2Image = lastImage
                lastImage = FileUtil.readFileInFiles(Config.POST_IMAGE)
            }
        }
    }

    companion object {
        const val TAG = "PostActivity"
        const val REQ_PICK_PHOTO = 1
        const val MODE_NORMAL = 1
        const val MODE_TRANSFER = 2
        const val MODE_TO_TEXT = 3
    }
}