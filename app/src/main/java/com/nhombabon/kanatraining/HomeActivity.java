package com.nhombabon.kanatraining;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.nhombabon.kanatraining.fragment.HomeFragment;
import com.nhombabon.kanatraining.fragment.LadderBoardFragment;
import com.nhombabon.kanatraining.fragment.QuizTopicsFragment;
import com.nhombabon.kanatraining.fragment.SettingsFragment;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();

    private Fragment selectedFragment = null;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content, selectedFragment);
            transaction.commit();
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        selectedFragment = new QuizTopicsFragment();
        FragmentTransaction transactions = getSupportFragmentManager().beginTransaction();
        transactions.replace(R.id.content, selectedFragment);
        transactions.commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
