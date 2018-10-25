package com.mansourappdevelopment.androidapp.encdec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplachScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EasySplashScreen easySplashScreen = new EasySplashScreen(SplachScreenActivity.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(2000)
                .withBackgroundResource(R.drawable.splach_screen_gradient)
                //.withHeaderText("Header")
                .withFooterText("Stay Safe, Stay Encrypted")
                //.withBeforeLogoText("before logo")
                //.withAfterLogoText("after logo")
                .withLogo(R.drawable.logo);

        //easySplashScreen.getHeaderTextView().setTextColor(getResources().getColor(R.color.github_white));
        //easySplashScreen.getAfterLogoTextView().setTextColor(getResources().getColor(R.color.github_white));
        //easySplashScreen.getBeforeLogoTextView().setTextColor(getResources().getColor(R.color.github_white));
        easySplashScreen.getFooterTextView().setTextColor(getResources().getColor(R.color.github_white));
        View view = easySplashScreen.create();
        setContentView(view);
    }
}
