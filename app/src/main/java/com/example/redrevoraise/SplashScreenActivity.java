package com.example.redrevoraise;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (true) {
                    intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                }
//                }else if(!sharedPreferences.getBoolean("loginStatus", false)) {
//                    intent = new Intent(SplashScreenActivity.this, Login.class);
//                } else {
//                    intent = new Intent(SplashScreenActivity.this, MainActivity.class);
//                }
                startActivity(intent);
                finish();
            }
        }, 1000);

    }

}

