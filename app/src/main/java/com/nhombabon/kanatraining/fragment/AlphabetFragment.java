package com.nhombabon.kanatraining.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhombabon.kanatraining.R;
import com.nhombabon.kanatraining.adapter.AlphabetPagerAdapter;

public class AlphabetFragment extends Fragment {


    public AlphabetFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alphabet, container, false);
        getActivity().setTitle("Alphabet");

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout_alphabet);
        tabLayout.addTab(tabLayout.newTab().setText("Hiragana"));
        tabLayout.addTab(tabLayout.newTab().setText("Katakana"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager_alphabet);
        final AlphabetPagerAdapter adapter = new AlphabetPagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

}
