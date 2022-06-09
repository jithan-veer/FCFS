package com.example.fcfs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.NetworkInterface;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN=7000;
    Animation top,bottom;
    ImageView i1;
    TextView t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        top= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottom= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        i1=findViewById(R.id.imageView);
        t1=findViewById(R.id.textView);
        i1.setAnimation(top);
        t1.setAnimation(bottom);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!isconnected()){
                    Intent i = new Intent(getApplicationContext(),nointernet.class);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(getApplicationContext(),Register_Activity.class);
                    Pair[] pairs = new Pair[2];
                    pairs[0] = new Pair<View,String>(i1 , "logo_image");
                    pairs[1] = new Pair<View,String>(t1 , "text_image");
                    ActivityOptions act = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);

                    startActivity(i,act.toBundle());
                    finish();
                }
            }
        },SPLASH_SCREEN);
    }

    private boolean isconnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wifi!=null && wifi.isConnected()) || (mobile!=null && mobile.isConnected())){
            return true;
        }
        else{
            return false;
        }

    }
}