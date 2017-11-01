package com.nhombabon.kanatraining.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhombabon.kanatraining.R;
import com.nhombabon.kanatraining.adapter.LadderBoardAdapter;
import com.nhombabon.kanatraining.models.LadderProfile;

import java.util.ArrayList;


public class AllTimeTopScoreFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String TAG = AllTimeTopScoreFragment.class.getSimpleName();


    public AllTimeTopScoreFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_time_top_score, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new LadderBoardAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        return view;
    }

    private ArrayList<LadderProfile> getDataSet() {
        ArrayList results = new ArrayList<>();
        for (int index = 0; index < 20; index++) {
            LadderProfile obj = new LadderProfile(R.drawable.pp, "Name " + index, 10 + index);
            results.add(index, obj);
        }
        return results;
    }

}
