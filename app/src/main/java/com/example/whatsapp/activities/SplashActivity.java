package com.example.whatsapp.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.whatsapp.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        Thread thread = new Thread(){
            @Override
            public void run(){
                try {
                    sleep(2000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        };
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}