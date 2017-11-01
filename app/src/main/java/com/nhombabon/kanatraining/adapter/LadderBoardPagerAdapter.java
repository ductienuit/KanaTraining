package com.nhombabon.kanatraining.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.nhombabon.kanatraining.fragment.AllTimeTopScoreFragment;
import com.nhombabon.kanatraining.fragment.DailyTopScorerFragment;


public class LadderBoardPagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public LadderBoardPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                AllTimeTopScoreFragment tab1 = new AllTimeTopScoreFragment();
                return tab1;
            case 1:
                DailyTopScorerFragment tab2 = new DailyTopScorerFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
