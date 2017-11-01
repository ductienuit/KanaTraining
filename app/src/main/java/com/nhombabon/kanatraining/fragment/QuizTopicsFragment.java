package com.nhombabon.kanatraining.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhombabon.kanatraining.R;
import com.nhombabon.kanatraining.adapter.TopicAdapter;
import com.nhombabon.kanatraining.models.QuizObject;

import java.util.ArrayList;
import java.util.List;


public class QuizTopicsFragment extends Fragment {


    public QuizTopicsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz_mart, container, false);
        getActivity().setTitle("Quiz Topics");

        RecyclerView selectedQuizRecyclerView = (RecyclerView)view.findViewById(R.id.quiz_topic);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        selectedQuizRecyclerView.setLayoutManager(gridLayoutManager);
        selectedQuizRecyclerView.setHasFixedSize(true);

        TopicAdapter mAdapter = new TopicAdapter(getActivity(), getTestData(), R.layout.quiz_topic_layout);
        selectedQuizRecyclerView.setAdapter(mAdapter);

        return view;
    }

    public List<QuizObject> getTestData() {
        List<QuizObject> testData = new ArrayList<>();
        testData.add(new QuizObject("", "General Knowledge"));
        testData.add(new QuizObject("", "Entertainment"));
        testData.add(new QuizObject("", "History"));
        testData.add(new QuizObject("", "Sports"));
        testData.add(new QuizObject("", "Business"));
        testData.add(new QuizObject("", "Art and Design"));
        testData.add(new QuizObject("", "Computer"));
        testData.add(new QuizObject("", "Movies"));
        return testData;
    }

}
