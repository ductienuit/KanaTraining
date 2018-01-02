package com.nhombabon.kanatraining.illust;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nhombabon.kanatraining.R;
import com.nhombabon.kanatraining.adapter.TopicAdapter;
import com.nhombabon.kanatraining.models.InforChoose;
import com.nhombabon.kanatraining.models.QuizObject;

import java.util.ArrayList;
import java.util.List;

public class IllustSelectTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illust_select_type);

        findViewById(R.id.quiz_top_button).setVisibility(View.GONE);

        RecyclerView selectedQuizRecyclerView = (RecyclerView)findViewById(R.id.illust_topic);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        selectedQuizRecyclerView.setLayoutManager(gridLayoutManager);
        selectedQuizRecyclerView.setHasFixedSize(true);

        TopicAdapter mAdapter = new TopicAdapter(this, getDataIllust(), R.layout.quiz_topic_layout);
        selectedQuizRecyclerView.setAdapter(mAdapter);
    }

    public void clickNaviBack(View view) {
        finish();
    }

    public void clickQuizTop(View view) {
        finish();
    }

    public List<QuizObject> getDataIllust() {
        List<QuizObject> quizData = new ArrayList<>();

        quizData.add(new QuizObject(R.drawable.ic_illust, "Illustrations one by one"));
        quizData.add(new QuizObject(R.drawable.ic_illust, "Illustrations row by row"));
        return quizData;
    }
}
