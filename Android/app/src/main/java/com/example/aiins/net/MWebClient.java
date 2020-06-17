package com.example.aiins.net;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;

public class MWebClient extends WebSocketClient {

    private static final String TAG = "MWebClient";

    public MWebClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        Log.i(TAG, "onOpen: " + handshakedata.getHttpStatusMessage());
        send("Hello World!");
    }

    @Override
    public void onMessage(String message) {
        Log.i(TAG, "onMessage: " + message);
    }

    @Override
    public void onMessage(ByteBuffer bytes) {
        super.onMessage(bytes);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onError(Exception ex) {

    }
}
