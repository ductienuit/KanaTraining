package com.nhombabon.kanatraining;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseUser;
import com.nhombabon.kanatraining.models.InforChoose;
import com.nhombabon.kanatraining.utils.BaseActivity;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Tạo singleton các giá trị âm thanh, lựa chọn bảng chữ cái, điểm sau mỗi lần để dễ truy cập
        InforChoose.getInstance();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }

    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            startActivity(new Intent(this, IntroActivity.class));
        } else {
            startActivity(new Intent(this, IntroActivity.class));
        }
    }

}
