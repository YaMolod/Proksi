package com.example.Proxy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;


import org.littleshoot.proxy.*;
import org.littleshoot.proxy.impl.*;

import java.net.InetSocketAddress;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

public class Proksi extends Activity{

    HttpProxyServer server;
    ToggleButton serverBtn;
    EditText editIP;
    EditText editPort;
    private final String answer = "";
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editIP = findViewById(R.id.editTextTextPersonName);
        editPort = findViewById(R.id.editTextTextPersonName2);

        serverBtn = (ToggleButton) findViewById(R.id.toggleButton);
        serverBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                    startServer(editIP.getText().toString(),Integer.parseInt(editPort.getText().toString()));
                else
                    server.stop();
            }
        });
    }
    @Override
    public void onDestroy()
    {
        server.stop();
        super.onDestroy();
    }

    public void startServer(String ip,int port)
    {
        InetSocketAddress socket = new InetSocketAddress(ip,port);
        server =
                DefaultHttpProxyServer.bootstrap()
                        .withAllowRequestToOriginServer(false)
                        .withAllowLocalOnly(false)
                        .withAddress(socket)
                        .withFiltersSource(new HttpFiltersSourceAdapter() {
                            public HttpFilters filterRequest(HttpRequest originalRequest, ChannelHandlerContext ctx) {
                                return new HttpFiltersAdapter(originalRequest) {
                                    @Override
                                    public HttpResponse clientToProxyRequest(HttpObject httpObject) {
                                        Log.d("Log", "clientToProxy:" + httpObject.toString());
                                        return null;
                                    }
                                    @Override
                                    public void proxyToServerRequestSending() {
                                        // TODO: implement your filtering here
                                        Log.d("Log", "proxyToServerRequestSending:");
                                    }
                                    @Override
                                    public void proxyToServerRequestSent() {
                                        // TODO: implement your filtering here
                                        Log.d("Log", "proxyToServerRequestSent:");
                                    }
                                    @Override
                                    public HttpObject serverToProxyResponse(HttpObject httpObject) {
                                        // TODO: implement your filtering here
                                        Log.d("Log", "serverToProxy:" + httpObject.toString());
                                        return httpObject;
                                    }
                                    @Override
                                    public void serverToProxyResponseReceiving() {
                                        // TODO: implement your filtering here
                                        Log.d("Log", "serverToProxyResponseReceiving:");
                                    }
                                    @Override
                                    public HttpObject  proxyToClientResponse(HttpObject httpObject) {
                                        Log.d("Log", "proxyToClient:" + httpObject.toString());
                                        return httpObject;
                                    }
                                    @Override
                                    public void serverToProxyResponseReceived() {
                                        // TODO: implement your filtering here
                                        Log.d("Log", "serverToProxyResponseReceiving:");
                                    }
                                    @Override
                                    public void serverToProxyResponseTimedOut() {
                                        // TODO: implement your filtering here
                                        Log.d("Log", "serverToProxyResponseTimedOut:");
                                    }
                                    @Override
                                    public HttpResponse proxyToServerRequest(HttpObject httpObject) {
                                        // TODO: implement your filtering here
                                        Log.d("Log", "proxyToServer:" + httpObject.toString());
                                        return null;
                                    }
                                    @Override
                                    public InetSocketAddress proxyToServerResolutionStarted(String resolvingServerHostAndPort) {
                                        Log.d("Log", "proxyToServerResolutionStarted:" + resolvingServerHostAndPort);
                                        return null;
                                    }
                                    @Override
                                    public void proxyToServerConnectionStarted() {
                                        Log.d("Log", "proxyToServerConnectionStarted:");
                                    }
                                    @Override
                                    public void proxyToServerConnectionFailed() {
                                        Log.d("Log", "proxyToServerConnectionFailed:");
                                    }
                                    @Override
                                    public void proxyToServerResolutionFailed(String hostAndPort) {
                                        Log.d("Log", "proxyToServerResolutionFailed:" + hostAndPort);
                                    }
                                    public void proxyToServerConnectionSucceeded(ChannelHandlerContext serverCtx) {
                                        // TODO: implement your filtering here
                                        Log.d("Log", "ConnectionSucceeded:" + serverCtx.toString());
                                    }
                                };
                            }
                        })
                        .start();
    }

    @Override
    protected void onActivityResult(int request, int result, Intent data)
    {

    }
}
