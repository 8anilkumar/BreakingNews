package com.anilkumarnishad.breakingnews.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.anilkumarnishad.breakingnews.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                final SharedPreferences sharedPreferences = getSharedPreferences("USER_CREDENTIALS", Context.MODE_PRIVATE);
                final String user = sharedPreferences.getString("USEREMAIL", "");
                if (user == null || user.length() == 0) {
                    Intent intent= new Intent(SplashScreen.this, Login.class);
                    startActivity(intent);
                    finish();

                }else{
                    Intent intent= new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        },4000);
    }
}