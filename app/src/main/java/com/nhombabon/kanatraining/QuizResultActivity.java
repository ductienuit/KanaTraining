package com.nhombabon.kanatraining;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class QuizResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);
        setTitle("Quiz Result");

        Button tryAgainButton = (Button)findViewById(R.id.try_again);
        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(QuizResultActivity.this, HomeActivity.class);
                startActivity(homeIntent);
            }
        });

        Button newQuizButton = (Button)findViewById(R.id.new_quiz);
        newQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(QuizResultActivity.this, HomeActivity.class);
                startActivity(homeIntent);
            }
        });
    }
}
