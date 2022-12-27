package com.example.Proxy;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import android.app.AlertDialog;
import android.content.DialogInterface;

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
                else if (server != null)
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

    public int startServer(String ip,int port)
    {
        InetSocketAddress socket = null;
        HttpProxyServerBootstrap bootstrap = null;
        try {
            socket = new InetSocketAddress(ip, port);
            bootstrap =
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
                                            Log.d("Log", "proxyToServerRequestSending:");
                                        }

                                        @Override
                                        public void proxyToServerRequestSent() {
                                            Log.d("Log", "proxyToServerRequestSent:");
                                        }

                                        @Override
                                        public HttpObject serverToProxyResponse(HttpObject httpObject) {
                                            Log.d("Log", "serverToProxy:" + httpObject.toString());
                                            return httpObject;
                                        }

                                        @Override
                                        public void serverToProxyResponseReceiving() {
                                            Log.d("Log", "serverToProxyResponseReceiving:");
                                        }

                                        @Override
                                        public HttpObject proxyToClientResponse(HttpObject httpObject) {
                                            Log.d("Log", "proxyToClient:" + httpObject.toString());
                                            return httpObject;
                                        }

                                        @Override
                                        public void serverToProxyResponseReceived() {
                                            Log.d("Log", "serverToProxyResponseReceiving:");
                                        }

                                        @Override
                                        public void serverToProxyResponseTimedOut() {
                                            Log.d("Log", "serverToProxyResponseTimedOut:");
                                        }

                                        @Override
                                        public HttpResponse proxyToServerRequest(HttpObject httpObject) {
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
                                            Log.d("Log", "ConnectionSucceeded:" + serverCtx.toString());
                                        }
                                    };
                                }
                            });
            server = bootstrap.start();
        }
        catch (RuntimeException e)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Ошибка");
            builder.setMessage("Проверьте данные!");
            builder.setCancelable(true);
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    serverBtn.setChecked(false);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            return -1;
        }
        return 1;
    }
}