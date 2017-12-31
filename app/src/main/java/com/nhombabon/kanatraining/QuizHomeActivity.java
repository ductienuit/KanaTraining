package com.nhombabon.kanatraining;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class QuizHomeActivity extends AppCompatActivity {

    private static final String TAG = QuizHomeActivity.class.getSimpleName();

    private Button answerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_home);

//        answerButton = (Button)findViewById(R.id.answer_four);
//        answerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent result = new Intent(QuizHomeActivity.this, QuizResultActivity.class);
//                startActivity(result);
//            }
//        });
    }
}
