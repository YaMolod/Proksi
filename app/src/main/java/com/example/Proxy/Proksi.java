package com.example.Proxy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Proksi extends Activity{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int request, int result, Intent data)
    {


    }
}
