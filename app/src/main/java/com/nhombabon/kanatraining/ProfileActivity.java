package com.nhombabon.kanatraining;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = ProfileActivity.class.getSimpleName();

    private TextView tvProgressTwo;
    private RoundCornerProgressBar progressTwo;

    private Button quizStartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setTitle("Home");

        quizStartButton = (Button)findViewById(R.id.start_quiz);
        quizStartButton.setOnClickListener(this);

        progressTwo = (RoundCornerProgressBar) findViewById(R.id.progress_1);
        progressTwo.setProgressBackgroundColor(getResources().getColor(R.color.custom_progress_background));
        updateProgressTwoColor();

        tvProgressTwo = (TextView)findViewById(R.id.progressCount);
        updateTextProgressTwo();

        findViewById(R.id.button_progress_two_decrease).setOnClickListener(this);
        findViewById(R.id.button_progress_two_increase).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_progress_two_decrease) {
            decreaseProgressTwo();
        } else if (id == R.id.button_progress_two_increase) {
            increaseProgressTwo();
        }else if(id == R.id.start_quiz){
            Intent startQuizIntent = new Intent(ProfileActivity.this, HelpActivity.class);
            startActivity(startQuizIntent);
        }
    }

    private void updateProgressTwoColor() {
        float progress = progressTwo.getProgress();
        if(progress <= 3) {
            progressTwo.setProgressColor(getResources().getColor(R.color.custom_progress_red_progress));
        } else if(progress > 3 && progress <= 6) {
            progressTwo.setProgressColor(getResources().getColor(R.color.custom_progress_orange_progress));
        } else if(progress > 6) {
            progressTwo.setProgressColor(getResources().getColor(R.color.custom_progress_green_progress));
        }
    }

    private void increaseProgressTwo() {
        progressTwo.setProgress(progressTwo.getProgress() + 1);
        updateProgressTwoColor();
        updateTextProgressTwo();
    }

    private void decreaseProgressTwo() {
        progressTwo.setProgress(progressTwo.getProgress() - 1);
        updateProgressTwoColor();
        updateTextProgressTwo();
    }

    private void updateTextProgressTwo() {
        tvProgressTwo.setText(String.valueOf((int) progressTwo.getProgress()));
    }
}
