package com.nhombabon.kanatraining.Quiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhombabon.kanatraining.AppBaseActivity;
import com.nhombabon.kanatraining.AppConfig;
import com.nhombabon.kanatraining.Common;
import com.nhombabon.kanatraining.QuizHomeActivity;
import com.nhombabon.kanatraining.R;
import com.nhombabon.kanatraining.models.InforChoose;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;


public class QuizAnswerActivity extends AppBaseActivity {

    private View mBaseView;
    private ImageView mBlurView;
    private View mContinueView;
    private TextView mDescView;
    private TextView mEnView;
    private Animation mFadeInAnim;
    private Animation mFadeOutAnim;
    private View mImageBaseView;
    private ImageView mImageView;
    private TextView mJpView;
    private String mNowChar;
    private int mNowIndex;
    private int[] mPointArray;
    private int mQuizType;
    private ArrayList<String> mQuizWordList;
    private ArrayList<String> mTargetList;
    private int mTime;

    protected void onDestroy() {
        this.mTime = 0;
        this.mQuizType = 0;
        this.mTargetList = null;
        this.mQuizWordList = null;
        this.mNowIndex = 0;
        this.mNowChar = null;
        this.mBaseView = null;
        this.mJpView = null;
        this.mEnView = null;
        try {
            this.mImageView.setImageBitmap(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mImageView = null;
        this.mImageBaseView = null;
        this.mDescView = null;
        this.mContinueView = null;
        try {
            this.mBlurView.setImageBitmap(null);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        this.mBlurView = null;
        this.mFadeInAnim = null;
        this.mFadeOutAnim = null;
        super.onDestroy();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_answer);
        this.mFadeInAnim = loadAnimation(R.anim.fade_in);
        this.mFadeOutAnim = loadAnimation(R.anim.fade_out);
        this.mBaseView = findViewById(R.id.quiz_answer_view);
        this.mJpView = (TextView) findViewById(R.id.text_type_jp);
        this.mEnView = (TextView) findViewById(R.id.text_type_en);
        this.mImageView = (ImageView) findViewById(R.id.change_illust);
        this.mImageBaseView = findViewById(R.id.change_illust_base);
        this.mDescView = (TextView) findViewById(R.id.text_type_catch);
        this.mContinueView = findViewById(R.id.pop_quiz_top);
        this.mBlurView = (ImageView) findViewById(R.id.pop_ans_blur_image);
        findViewById(R.id.navigation_back_button).setVisibility(View.GONE);
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, (TextView) findViewById(R.id.change_title));
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, (TextView) findViewById(R.id.answer_next_button));
        fontChange(AppConfig.FONT_NAME_LETTER, (TextView) findViewById(R.id.text_type_catch));
        fontChange(AppConfig.FONT_NAME_LETTER, (TextView) findViewById(R.id.text_type_en));
        fontChange(AppConfig.FONT_NAME_KYOKASHO, (TextView) findViewById(R.id.text_type_jp));
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, (TextView) findViewById(R.id.quiz_stop_txt));
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, (TextView) findViewById(R.id.btn_no));
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, (TextView) findViewById(R.id.btn_yes));
    }

    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
        this.mTime = intent.getIntExtra(AppConfig.RESULT_TIME, 0);
        this.mPointArray = intent.getIntArrayExtra(AppConfig.RESULT_POINT_LIST);
        this.mQuizType = intent.getIntExtra(AppConfig.RESULT_QUIZ_TYPE, 0);
        this.mTargetList = intent.getStringArrayListExtra(AppConfig.RESULT_ALL_TARGET_LIST);
        this.mQuizWordList = intent.getStringArrayListExtra(AppConfig.RESULT_QUIZ_WORD_LIST);
        TextView barTitleView = (TextView) findViewById(R.id.change_title);
        switch (this.mQuizType) {
            case 0:
                barTitleView.setText(getResources().getText(R.string.QuizAnswer_ViewName1).toString());
                break;
            case 1:
                barTitleView.setText(getResources().getText(R.string.QuizAnswer_ViewName2).toString());
                break;
            case 2:
                barTitleView.setText(getResources().getText(R.string.QuizAnswer_ViewName3).toString());
                break;
        }
        calcNowIndex();
        this.mNowChar = (String) this.mQuizWordList.get(this.mNowIndex);
        adjustTitle();
        adjustChar();
    }

    private void calcNowIndex() {
        this.mNowIndex = -1;
        for (int i = 0; i < this.mPointArray.length; i++) {
            if (this.mPointArray[i] == 0) {
                this.mNowIndex = i - 1;
                break;
            }
        }
        if (this.mNowIndex < 0) {
            this.mNowIndex = 9;
        }
    }

    public void adjustTitle() {
        TextView titleView = (TextView) findViewById(R.id.change_title);
        if(InforChoose.getChooseKana()==0)
        {
            switch (this.mQuizType) {
                case 0:
                    titleView.setText(getResources().getString(R.string.QuizQuestion1_ViewName));
                    return;
                case 1:
                    titleView.setText(getResources().getString(R.string.QuizQuestion2_ViewName));
                    return;
                case 2:
                    titleView.setText(getResources().getString(R.string.QuizQuestion3_ViewName));
                    return;
                case 3:
                    titleView.setText(getResources().getString(R.string.QuizQuestion4_ViewName));
                    return;
                default:
                    return;
            }
        }
        else
        {
            switch (this.mQuizType) {
                case 0:
                    titleView.setText(getResources().getString(R.string.QuizQuestion5_ViewName));
                    return;
                case 1:
                    titleView.setText(getResources().getString(R.string.QuizQuestion6_ViewName));
                    return;
                case 2:
                    titleView.setText(getResources().getString(R.string.QuizQuestion7_ViewName));
                    return;
                case 3:
                    titleView.setText(getResources().getString(R.string.QuizQuestion8_ViewName));
                    return;
                default:
                    return;
            }
        }
    }

    private void adjustChar() {
        Common common;
        try {
            common = (Common) getApplication();
        } catch (Exception e) {
            e.printStackTrace();
            common = (Common) getApplication();
        }
        if (common.getmCharDataList() == null) {
            Log.e("common.mCharDataList", "null");
            common.init();
        }
        String[] data = (String[]) common.getCharDataList(this.mNowChar);


        this.mBaseView.setBackgroundColor(colorWithHex(data[6]));
        int column = Integer.parseInt(data[1]);
        loadResBgImage(String.format("bg_cycle_answer_s%02d", new Object[]{Integer.valueOf(column)}), this.mJpView);
        loadResBgImage(String.format("bg_cycle_answer_s%02d", new Object[]{Integer.valueOf(column)}), this.mEnView);
        loadResBgImage(String.format("bg_cycle_answer%02d", new Object[]{Integer.valueOf(column)}), this.mImageBaseView);
        this.mJpView.setText(data[4]);
        this.mEnView.setText(data[3]);
        if (data[3].equals("i")) {
            fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, this.mEnView);
        }


        if (InforChoose.getChooseKana()==0)
            loadAssetImage("hiragana/ch_img/" + data[2] + "_1.png", this.mImageView);
        else
            loadAssetImage("katakana/ch_img/" + data[2] + "_1.png", this.mImageView);


        this.mDescView.setText(spanString(data[5], data[1]));
    }


    public void ClickAnswerNext(View v) {
        if (this.mPointArray[9] != 0) {
            moveToEndActivity();
        } else {
            moveToNextActivity();
        }
    }

    private void moveToNextActivity() {
        Intent it = new Intent();
        it.putExtra(AppConfig.RESULT_TIME, this.mTime);
        it.putExtra(AppConfig.RESULT_POINT_LIST, this.mPointArray);
        it.putExtra(AppConfig.RESULT_QUIZ_TYPE, this.mQuizType);
        it.putExtra(AppConfig.RESULT_ALL_TARGET_LIST, this.mTargetList);
        it.putExtra(AppConfig.RESULT_QUIZ_WORD_LIST, this.mQuizWordList);
        it.setClass(this, QuizMainActivity.class);
        it.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
    }

    //Chưa có quizresult activity
    private void moveToEndActivity() {
        Intent it = new Intent();
        it.putExtra(AppConfig.RESULT_TIME, this.mTime);
        it.putExtra(AppConfig.RESULT_POINT_LIST, this.mPointArray);
        it.putExtra(AppConfig.RESULT_QUIZ_TYPE, this.mQuizType);
        it.putExtra(AppConfig.RESULT_ALL_TARGET_LIST, this.mTargetList);
        it.putExtra(AppConfig.RESULT_QUIZ_WORD_LIST, this.mQuizWordList);
        it.setClass(this, QuizResultActivity.class);
        it.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
    }

    public void clickNaviBack(View view) {
        finish();
    }

    public void clickQuizTop(View v) {
        showContinueView(true);
    }


    public void clickTopNo(View v) {
        showContinueView(false);
    }

    public void clickTopYes(View v) {
        Intent it = new Intent();
        it.setClass(this, QuizHomeActivity.class);
        it.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
    }

    private void showContinueView(boolean show) {
        createBlurImage(getViewBitmap(this.mBaseView), this.mBlurView);
        this.mContinueView.setVisibility(View.VISIBLE);
        if (show) {
            this.mFadeInAnim.setDuration((long) 250);
            this.mFadeInAnim.setFillAfter(true);
            this.mContinueView.startAnimation(this.mFadeInAnim);
            return;
        }
        this.mFadeOutAnim.setDuration((long) 250);
        this.mFadeOutAnim.setFillAfter(true);
        this.mContinueView.startAnimation(this.mFadeOutAnim);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == 0) {
            switch (event.getKeyCode()) {
                case 4:
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    public void onAnimationStart(Animation animation) {
    }

    public void onAnimationEnd(Animation animation) {
        if (animation.equals(this.mFadeOutAnim)) {
            this.mContinueView.setVisibility(View.GONE);
            this.mContinueView.clearAnimation();
        } else if (animation.equals(this.mFadeInAnim)) {
            this.mContinueView.clearAnimation();
        }
    }

    public void onAnimationRepeat(Animation animation) {
    }
}
