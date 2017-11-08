package com.android.qdbsample;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {
SharedPreferences settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
        settings = getApplicationContext().getSharedPreferences("Loginpref",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = settings.edit();

       final Boolean flag = settings.getBoolean("login", false);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */


            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if (flag == true) {
                    Intent intent = new Intent(SplashActivity.this, DashBoard.class);
                    finish();
                    startActivity(intent);
                }
                else {
                    Intent intent1 = new Intent(SplashActivity.this, LoginActivity.class);
                    finish();
                    startActivity(intent1);
                }


                // close this activity
                finish();
            }
        }, 5000);
    }
}
