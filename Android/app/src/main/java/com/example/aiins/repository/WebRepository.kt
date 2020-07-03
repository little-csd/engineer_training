package com.example.aiins.repository

import android.util.Log
import com.example.aiins.MainActivity
import com.example.aiins.proto.MessageOuterClass
import com.example.aiins.util.Config
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI
import java.nio.ByteBuffer

object WebRepository : WebSocketClient(URI.create(Config.socket)) {

    const val TAG = "WebClient"
    private val observers = ArrayList<Observer>()

    override fun onOpen(handshakedata: ServerHandshake?) {
        Log.i(TAG, "onOpen: $handshakedata")
        Repository.pullMessage()
        send(Config.userData.uid.toString())
    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        Log.i(TAG, "onClose: $reason")
    }

    override fun onMessage(message: String?) {
        Log.i(TAG, "onMessage: $message")
    }

    override fun onMessage(bytes: ByteBuffer?) {
        Log.i(TAG, "onMessage: ")
        val msg = MessageOuterClass.Message.parseFrom(bytes)
        for (observer in observers) {
            observer.onMessage(msg)
        }
        Repository.addMessage(msg)
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
        observers.add(observer)
    }

    fun removeObserver(observer: Observer) {
        observers.remove(observer)
    }

    interface Observer {
        fun onMessage(msg: MessageOuterClass.Message)
    }
}