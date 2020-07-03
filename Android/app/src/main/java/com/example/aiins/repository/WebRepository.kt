package com.example.aiins.repository

import android.util.Log
import android.widget.Toast
import com.example.aiins.MainActivity
import com.example.aiins.proto.MessageOuterClass
import com.example.aiins.repository.Repository.NET_ERR
import com.example.aiins.util.Config
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import java.nio.ByteBuffer
import kotlin.system.exitProcess

object WebRepository : WebSocketClient(URI.create(Config.socket)) {

    const val TAG = "WebClient"
    lateinit var context: MainActivity
    private var observer: Observer? = null

    override fun onOpen(handshakedata: ServerHandshake?) {
        Log.i(TAG, "onOpen: $handshakedata")
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        Log.i(TAG, "onClose: $reason")
    }

    override fun onMessage(message: String?) {
        Log.i(TAG, "onMessage: $message")
    }

    override fun onMessage(bytes: ByteBuffer?) {
        val msg = MessageOuterClass.Message.parseFrom(bytes)
        observer?.onMessage(msg)
    }

    override fun onError(ex: Exception?) {
        ex?.printStackTrace()
    }

    override fun send(data: ByteArray?) {
        if (!isOpen) {
            connect()
            return
        }
        super.send(data)
    }

    fun addObserver(observer: Observer) {
        this.observer = observer
    }

    fun removeObserver() {
        this.observer = null
    }

    interface Observer {
        fun onMessage(msg: MessageOuterClass.Message)
    }
}