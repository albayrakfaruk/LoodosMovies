package com.example.faruk.loodosmovies;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faruk.loodosmovies.helper.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class SplashActivity extends AppCompatActivity {
    final int cacheExpiration=0;
    TextView tvCompanyName;
    ImageView ivLoading;
    FirebaseRemoteConfig firebaseRemoteConfig;
    AnimationDrawable animationDrawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initViews();
        if(Common.IsConnected(this)) {
           setLoadingAnimation();
           getDataFromFirebase();
        }else
           Toast.makeText(this, "Check your internet!",Toast.LENGTH_LONG).show();
        }

    public void getDataFromFirebase()
    {
        firebaseRemoteConfig=FirebaseRemoteConfig.getInstance();
        firebaseRemoteConfig.fetch(cacheExpiration).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    firebaseRemoteConfig.activateFetched();
                }
                displayMessage(tvCompanyName,"CompanyName");
                startLoadingAnimation();
                myDelayFunc(3);
            }
        });
    }
    public void myDelayFunc(int delayedTime)
    {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                stopLoadingAnimation();
                startActivity(new Intent(SplashActivity.this,MoviesActivity.class));
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                finish();
            }
        }, delayedTime * 1000);
    }
    private void displayMessage(TextView textView,String tag) {
        textView.setText(firebaseRemoteConfig.getString(tag));
    }
    private void initViews()
    {
        tvCompanyName=(TextView)findViewById(R.id.tvCompanyName);
        ivLoading=(ImageView)findViewById(R.id.ivLoading);
    }
    private void setLoadingAnimation()
    {
        ivLoading.setImageResource(R.drawable.animation_list);
        animationDrawable=(AnimationDrawable)ivLoading.getDrawable();
    }
    private void startLoadingAnimation()
    {
        animationDrawable.start();
    }
    private void stopLoadingAnimation()
    {
        animationDrawable.stop();
    }
}

