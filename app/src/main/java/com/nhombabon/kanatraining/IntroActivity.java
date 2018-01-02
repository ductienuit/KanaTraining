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

        AhoyOnboarderCard firstScreen = new AhoyOnboarderCard(getString(R.string.first_title), getString(R.string.first_description), R.drawable.app);
        firstScreen.setBackgroundColor(R.color.black_transparent);
        firstScreen.setTitleColor(R.color.white);
        firstScreen.setDescriptionColor(R.color.grey_200);
        firstScreen.setTitleTextSize(dpToPixels(10, this));
        firstScreen.setDescriptionTextSize(dpToPixels(8, this));
        firstScreen.setIconLayoutParams(256, 256, 40, 0, 0, 40);

        AhoyOnboarderCard secondScreen = new AhoyOnboarderCard(getString(R.string.second_title), getString(R.string.second_description), R.drawable.ic_third);
        secondScreen.setBackgroundColor(R.color.black_transparent);
        secondScreen.setTitleColor(R.color.white);
        secondScreen.setDescriptionColor(R.color.grey_200);
        secondScreen.setTitleTextSize(dpToPixels(10, this));
        secondScreen.setDescriptionTextSize(dpToPixels(8, this));
        secondScreen.setIconLayoutParams(256, 256, 40, 0, 0, 40);

        AhoyOnboarderCard thirdScreen = new AhoyOnboarderCard(getString(R.string.third_title), getString(R.string.third_description), R.drawable.icon_second);
        thirdScreen.setBackgroundColor(R.color.black_transparent);
        thirdScreen.setTitleColor(R.color.white);
        thirdScreen.setDescriptionColor(R.color.grey_200);
        thirdScreen.setTitleTextSize(dpToPixels(10, this));
        thirdScreen.setDescriptionTextSize(dpToPixels(8, this));
        thirdScreen.setIconLayoutParams(256, 256, 40, 0, 0, 40);






        setGradientBackground();

        List<AhoyOnboarderCard> pages = new ArrayList<>();
        pages.add(firstScreen);
        pages.add(secondScreen);
        pages.add(thirdScreen);

        setOnboardPages(pages);

        //setColorBackground(R.color.colorPrimary);

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
