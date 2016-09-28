package com.springboardtechsolutions.parlorbeaconshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread startTimer = new Thread(){
            public void run(){
                try{
                    sleep(2500);
                    startActivity(new Intent(MainActivity.this,LoginShop.class));
                    finish();
                }catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        };
        startTimer.start();
    }
}
