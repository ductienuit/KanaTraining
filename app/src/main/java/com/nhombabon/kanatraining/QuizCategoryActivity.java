package com.nhombabon.kanatraining;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nhombabon.kanatraining.adapter.CategoryAdapter;
import com.nhombabon.kanatraining.models.TopicObject;

import java.util.ArrayList;
import java.util.List;

public class QuizCategoryActivity extends AppCompatActivity {

    private static final String TAG = QuizCategoryActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_category);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.quiz_category);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(QuizCategoryActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        CategoryAdapter mAdapter = new CategoryAdapter(QuizCategoryActivity.this, getTestData());
        recyclerView.setAdapter(mAdapter);
    }

    public List<TopicObject> getTestData() {
        List<TopicObject> testData = new ArrayList<>();
        testData.add(new TopicObject("", "Match Logo", "Simple way to match logo"));
        testData.add(new TopicObject("", "Match Logo", "Simple way to match logo"));
        testData.add(new TopicObject("", "Match Logo", "Simple way to match logo"));
        testData.add(new TopicObject("", "Match Logo", "Simple way to match logo"));
        testData.add(new TopicObject("", "Match Logo", "Simple way to match logo"));
        return testData;
    }
}
