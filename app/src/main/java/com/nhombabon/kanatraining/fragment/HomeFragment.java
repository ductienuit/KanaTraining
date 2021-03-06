package com.nhombabon.kanatraining.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nhombabon.kanatraining.IntroActivity;
import com.nhombabon.kanatraining.R;
import com.nhombabon.kanatraining.adapter.QuizAdapter;
import com.nhombabon.kanatraining.models.QuizObject;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment{

    private static final String TAG = HomeFragment.class.getSimpleName();

    private TextView playerName, playerStatus, playerScore;

    private Button logOutButton;

    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle("Home");

        playerName = (TextView)view.findViewById(R.id.player_name);
        playerStatus = (TextView)view.findViewById(R.id.player_status);
        playerScore = (TextView)view.findViewById(R.id.player_score);
        logOutButton = (Button)view.findViewById(R.id.btn_logout);

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(getActivity(), IntroActivity.class));
            }
        });

        RecyclerView selectedQuizRecyclerView = (RecyclerView)view.findViewById(R.id.selected_quizzes);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        selectedQuizRecyclerView.setLayoutManager(gridLayoutManager);
        selectedQuizRecyclerView.setHasFixedSize(true);

        QuizAdapter mAdapter = new QuizAdapter(getActivity(), getTestData(), R.layout.selected_quiz_layout);
        selectedQuizRecyclerView.setAdapter(mAdapter);

        return view;
    }

    public List<QuizObject> getTestData() {
        List<QuizObject> testData = new ArrayList<>();
        testData.add(new QuizObject(1, "General Knowledge"));
        testData.add(new QuizObject(1, "Entertainment"));
        testData.add(new QuizObject(1, "History"));
        testData.add(new QuizObject(1, "Sports"));
        return testData;
    }
}
