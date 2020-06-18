package com.example.aiins;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.example.aiins.net.MWebClient;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.enums.ReadyState;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.ByteBuffer;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;
import dalvik.system.DexFile;
import dalvik.system.InMemoryDexClassLoader;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            final MWebClient client = new MWebClient(new URI("http://192.168.101.65:13245"));
            client.connect();
            Log.i(TAG, "onCreate: " + Thread.currentThread());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!client.getReadyState().equals(ReadyState.OPEN));
                    Log.i(TAG, "run: " + client.isOpen());
                    client.send("Hello");
                    client.send("Hello");
                    client.send("Hello");
                    client.send("Read");
                    client.send("Write");
                    client.send("Byte");
                    client.close();
                }
            }).start();
//            client.send("Hello, server!");
//            client.send(new byte[]{0,1,2});
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    client.close();
//                }
//            }).start();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }
}