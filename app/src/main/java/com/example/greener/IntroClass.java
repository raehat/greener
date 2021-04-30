package com.example.greener;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;

public class IntroClass extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        if (SaveSharedPreference.getLoggedStatus(getApplicationContext()))
        {
            Intent intent= new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        @SuppressLint("WrongConstant") SharedPreferences sh
                = getSharedPreferences("MySharedPref", MODE_APPEND);
        boolean introDone = sh.getBoolean("introDone", false);

        if (introDone){
            Intent intent= new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        addSlide(AppIntroFragment.newInstance(
                "Welcome...",
                "We are really thankful for you to download this app. You can either skip this intro or bother" +
                        " yourself for this lol!"
        ));
        addSlide(AppIntroFragment.newInstance(
                "...Let's get started!",
                "Well! You can buy or sell stuff using this app! Very nycccc great success!!!"
        ));
        addSlide(AppIntroFragment.newInstance(
                "Last slide lmaoo",
                "You're all done!! Now you can either login or" +
                        " create an account if you're new ehehe)"
        ));
    }
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent intent= new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        // Decide what to do when the user clicks on "Skip"
        finish();
    }

    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Decide what to do when the user clicks on "Done"
        Intent intent= new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}