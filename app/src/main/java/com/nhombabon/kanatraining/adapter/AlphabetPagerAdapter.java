package com.nhombabon.kanatraining.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.nhombabon.kanatraining.fragment.AllTimeTopScoreFragment;
import com.nhombabon.kanatraining.fragment.DailyTopScorerFragment;
import com.nhombabon.kanatraining.fragment.HiraganaFragment;
import com.nhombabon.kanatraining.fragment.KatakanaFragment;


public class AlphabetPagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public AlphabetPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                HiraganaFragment tab1 = new HiraganaFragment();
                return tab1;
            case 1:
                KatakanaFragment tab2 = new KatakanaFragment();
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
