package com.nhombabon.kanatraining;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AhoyOnboarderActivity {

    private static final String TAG = IntroActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_intro);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        AhoyOnboarderCard firstScreen = new AhoyOnboarderCard(getString(R.string.first_title), getString(R.string.first_description), R.drawable.bookone);
        firstScreen.setBackgroundColor(R.color.black_transparent);
        firstScreen.setTitleColor(R.color.white);
        firstScreen.setDescriptionColor(R.color.grey_200);
        firstScreen.setTitleTextSize(dpToPixels(10, this));
        firstScreen.setDescriptionTextSize(dpToPixels(8, this));
        firstScreen.setIconLayoutParams(256, 256, 40, 0, 0, 40);


        List<AhoyOnboarderCard> pages = new ArrayList<>();
        pages.add(firstScreen);
        pages.add(firstScreen);
        pages.add(firstScreen);

        setOnboardPages(pages);

        setColorBackground(R.color.colorPrimary);

        showNavigationControls(true);

        setInactiveIndicatorColor(R.color.colorAccent);
        setActiveIndicatorColor(R.color.white);

        setFinishButtonTitle("Get Started");

        setFinishButtonDrawableStyle(ContextCompat.getDrawable(this, R.drawable.rounded_button));

    }

    @Override
    public void onStart() {
        super.onStart();

    }


    @Override
    public void onFinishButtonPressed() {
        Intent showSignUpIntent = new Intent(IntroActivity.this, LoginOptionActivity.class);
        startActivity(showSignUpIntent);
    }

}
