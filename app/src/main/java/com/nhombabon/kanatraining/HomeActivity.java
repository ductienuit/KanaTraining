package com.nhombabon.kanatraining;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.nhombabon.kanatraining.fragment.AlphabetFragment;
import com.nhombabon.kanatraining.fragment.HomeFragment;
import com.nhombabon.kanatraining.fragment.LadderBoardFragment;
import com.nhombabon.kanatraining.fragment.QuizTopicsFragment;
import com.nhombabon.kanatraining.fragment.SettingsFragment;
import com.nhombabon.kanatraining.models.Common;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();

    private Fragment selectedFragment = null;
    private int mHomeType;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.quiz_mart:
                        selectedFragment = new QuizTopicsFragment();
                        break;
                    case R.id.home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.ladder_board:
                        selectedFragment = new LadderBoardFragment();
                        break;
                    case R.id.settings:
                        selectedFragment = new SettingsFragment();
                        break;
                    case R.id.alphabet:
                        selectedFragment= new AlphabetFragment();
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, selectedFragment);
                transaction.commit();
                return true;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);


        Common common;
        try {
            common = (Common) getApplication();
        } catch (Exception e) {
            e.printStackTrace();
            common = (Common) getApplication();
        }
        if (common.getmCharDataList() == null) {
            Log.e("common.mCharDataList", "null");
            common.init();
        }


        this.mHomeType = intent.getIntExtra(AppConfig.SELECTED_HOME, 0);

        switch (mHomeType)
        {
            case 0:
            {
                selectedFragment = new QuizTopicsFragment();
               navigation.setSelectedItemId(R.id.quiz_mart);
                break;
            }
            case 3:
            {
                selectedFragment = new AlphabetFragment();
                navigation.setSelectedItemId(R.id.alphabet);
                break;
            }
        }

        FragmentTransaction transactions = getSupportFragmentManager().beginTransaction();
        transactions.replace(R.id.content, selectedFragment);
        transactions.commit();


        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
