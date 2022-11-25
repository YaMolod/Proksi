package com.example.Proxy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.littleshoot.proxy.*;
import org.littleshoot.proxy.impl.*;

public class Proksi extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HttpProxyServer server =
                DefaultHttpProxyServer.bootstrap()
                        .withPort(8080)
                        .start();
    }

    @Override
    protected void onActivityResult(int request, int result, Intent data)
    {


    }
}
