package com.nhombabon.kanatraining;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;
import com.nhombabon.kanatraining.illust.IllustMainActivity;
import com.nhombabon.kanatraining.models.InforChoose;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AhoyOnboarderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Tạo singleton các giá trị âm thanh, lựa chọn bảng chữ cái, điểm sau mỗi lần để dễ truy cập
        InforChoose.getInstance();


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

        showNavigationControls(false);

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
        Intent showSignUpIntent = new Intent(MainActivity.this, LoginOptionActivity.class);
        startActivity(showSignUpIntent);
    }

}
