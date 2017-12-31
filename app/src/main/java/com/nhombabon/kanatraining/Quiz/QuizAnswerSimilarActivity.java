package com.nhombabon.kanatraining.Quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nhombabon.kanatraining.AppBaseActivity;
import com.nhombabon.kanatraining.AppConfig;
import com.nhombabon.kanatraining.HomeActivity;
import com.nhombabon.kanatraining.models.Common;
import com.nhombabon.kanatraining.R;
import com.nhombabon.kanatraining.models.InforChoose;

import java.util.ArrayList;

public class QuizAnswerSimilarActivity extends AppBaseActivity {
    private RelativeLayout mBaseView;
    private ImageView mBlurView;
    private ArrayList<String> mChoiceList;
    private View mContinueView;
    private Animation mFadeInAnim;
    private Animation mFadeOutAnim;
    private View mFirstBaseView;
    private TextView mFirstCatchView;
    private View mFirstImageBaseView;
    private ImageView mFirstImageView;
    private TextView mFirstTextEnView;
    private TextView mFirstTextJpView;
    private String mNowChar;
    private int mNowIndex;
    private int[] mPointArray;
    private int mQuizType;
    private ArrayList<String> mQuizWordList;
    private View mSecondBaseView;
    private TextView mSecondCatchView;
    private View mSecondImageBaseView;
    private ImageView mSecondImageView;
    private TextView mSecondTextEnView;
    private TextView mSecondTextJpView;
    private ArrayList<String> mTargetList;
    private View mThirdBaseView;
    private TextView mThirdCatchView;
    private View mThirdImageBaseView;
    private ImageView mThirdImageView;
    private TextView mThirdTextEnView;
    private TextView mThirdTextJpView;
    private int mTime;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_answer_similar);
        this.mFadeInAnim = loadAnimation(R.anim.fade_in);
        this.mFadeOutAnim = loadAnimation(R.anim.fade_out);
        this.mContinueView = findViewById(R.id.pop_quiz_top);
        this.mFirstBaseView = findViewById(R.id.quiz_answer_sim_first);
        this.mSecondBaseView = findViewById(R.id.quiz_answer_sim_second);
        this.mThirdBaseView = findViewById(R.id.quiz_answer_sim_third);
        findViewById(R.id.quiz_top_button).setVisibility(View.VISIBLE);
        this.mFirstTextEnView = (TextView) this.mFirstBaseView.findViewById(R.id.text_type_en);
        this.mFirstCatchView = (TextView) this.mFirstBaseView.findViewById(R.id.text_type_catch);
        this.mFirstImageBaseView = this.mFirstBaseView.findViewById(R.id.quiz_answer_base_imgbase);
        this.mFirstImageView = (ImageView) this.mFirstBaseView.findViewById(R.id.quiz_answer_base_image);
        this.mFirstTextJpView = (TextView) this.mFirstBaseView.findViewById(R.id.text_type_jp);
        this.mSecondTextEnView = (TextView) this.mSecondBaseView.findViewById(R.id.text_type_en);
        this.mSecondCatchView = (TextView) this.mSecondBaseView.findViewById(R.id.text_type_catch);
        this.mSecondImageBaseView = this.mSecondBaseView.findViewById(R.id.quiz_answer_base_imgbase);
        this.mSecondImageView = (ImageView) this.mSecondBaseView.findViewById(R.id.quiz_answer_base_image);
        this.mSecondTextJpView = (TextView) this.mSecondBaseView.findViewById(R.id.text_type_jp);
        this.mThirdTextEnView = (TextView) this.mThirdBaseView.findViewById(R.id.text_type_en);
        this.mThirdCatchView = (TextView) this.mThirdBaseView.findViewById(R.id.text_type_catch);
        this.mThirdImageBaseView = this.mThirdBaseView.findViewById(R.id.quiz_answer_base_imgbase);
        this.mThirdImageView = (ImageView) this.mThirdBaseView.findViewById(R.id.quiz_answer_base_image);
        this.mThirdTextJpView = (TextView) this.mThirdBaseView.findViewById(R.id.text_type_jp);
        this.mBaseView = (RelativeLayout) findViewById(R.id.quiz_answer_view);
        this.mBlurView = (ImageView) findViewById(R.id.pop_ans_sim_blur_image);
        findViewById(R.id.navigation_back_button).setVisibility(View.GONE);
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, (TextView) findViewById(R.id.change_title));
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, (TextView) findViewById(R.id.answer_next_button));
        fontChange(AppConfig.FONT_NAME_LETTER, this.mFirstCatchView);
        fontChange(AppConfig.FONT_NAME_LETTER, this.mSecondCatchView);
        fontChange(AppConfig.FONT_NAME_LETTER, this.mThirdCatchView);
        fontChange(AppConfig.FONT_NAME_LETTER, this.mFirstTextEnView);
        fontChange(AppConfig.FONT_NAME_LETTER, this.mSecondTextEnView);
        fontChange(AppConfig.FONT_NAME_LETTER, this.mThirdTextEnView);
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, (TextView) findViewById(R.id.quiz_stop_txt));
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, (TextView) findViewById(R.id.btn_no));
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, (TextView) findViewById(R.id.btn_yes));
    }

    public void onResume() {
        String[] data;
        String bgname;
        int column;
        super.onResume();
        Intent intent = getIntent();
        this.mTime = intent.getIntExtra(AppConfig.RESULT_TIME, 0);
        this.mPointArray = intent.getIntArrayExtra(AppConfig.RESULT_POINT_LIST);
        this.mQuizType = intent.getIntExtra(AppConfig.RESULT_QUIZ_TYPE, 0);
        this.mTargetList = intent.getStringArrayListExtra(AppConfig.RESULT_ALL_TARGET_LIST);
        this.mQuizWordList = intent.getStringArrayListExtra(AppConfig.RESULT_QUIZ_WORD_LIST);
        this.mChoiceList = intent.getStringArrayListExtra(AppConfig.RESULT_CHOICE_LIST);
        ((TextView) findViewById(R.id.change_title)).setText(getResources().getString(R.string.QuizQuestion4_ViewName));



        Common common = (Common) getApplication();
        calcNowIndex();
        this.mNowChar = (String) this.mQuizWordList.get(this.mNowIndex);
        if (this.mChoiceList.size() > 0) {
            data = (String[]) common.getCharDataList(this.mChoiceList.get(0));
            this.mFirstTextEnView.setText(data[3]);
            this.mFirstCatchView.setText(spanString(data[5], "0"));
            column = Integer.parseInt(data[1]);
            bgname = String.format("bg_circle%02d_m", new Object[]{Integer.valueOf(column)});


            if (data[4].equalsIgnoreCase("ん") || data[4].equalsIgnoreCase("ン")) {
                bgname = "bg_circle07_m";
            }


            loadResBgImage(bgname, this.mFirstImageBaseView);

            if(InforChoose.getChooseKana()==0)
                 loadAssetImage(String.format("hiragana/ch_img/%s_1.png", new Object[]{data[2]}), this.mFirstImageView);
            else
                loadAssetImage(String.format("katakana/ch_img/%s_1.png", new Object[]{data[2]}), this.mFirstImageView);

            this.mFirstTextJpView.setText(data[4]);
            fontChange(AppConfig.FONT_NAME_KYOKASHO, this.mFirstTextJpView);
            if (data[3].equals("i")) {
                fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, this.mFirstTextEnView);
            }
            if (this.mNowChar.equalsIgnoreCase(data[4])) {
                this.mFirstBaseView.setBackgroundResource(R.color.pink);
            }
        }
        if (common.getmCharDataList().size() > 0) {
            if (this.mChoiceList.size() > 1) {
                data = (String[]) common.getCharDataList(this.mChoiceList.get(1));
                this.mSecondTextEnView.setText(data[3]);
                this.mSecondCatchView.setText(spanString(data[5], "0"));
                column = Integer.parseInt(data[1]);
                bgname = String.format("bg_circle%02d_m", new Object[]{Integer.valueOf(column)});


                if (data[4].equalsIgnoreCase("ん") || data[4].equalsIgnoreCase("ン")) {
                    bgname = "bg_circle07_m";
                }



                loadResBgImage(bgname, this.mSecondImageBaseView);
                if(InforChoose.getChooseKana()==0)
                    loadAssetImage(String.format("hiragana/ch_img/%s_1.png", new Object[]{data[2]}), this.mSecondImageView);
                else
                    loadAssetImage(String.format("katakana/ch_img/%s_1.png", new Object[]{data[2]}), this.mSecondImageView);


                this.mSecondTextJpView.setText(data[4]);
                fontChange(AppConfig.FONT_NAME_KYOKASHO, this.mSecondTextJpView);
                if (data[3].equals("i")) {
                    fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, this.mSecondTextEnView);
                }
                if (this.mNowChar.equalsIgnoreCase(data[4])) {
                    this.mSecondBaseView.setBackgroundResource(R.color.pink);
                }
            }
            if (this.mChoiceList.size() > 2) {
                data = (String[]) common.getCharDataList(this.mChoiceList.get(2));
                this.mThirdTextEnView.setText(data[3]);
                this.mThirdCatchView.setText(spanString(data[5], "0"));
                column = Integer.parseInt(data[1]);
                bgname = String.format("bg_circle%02d_m", new Object[]{Integer.valueOf(column)});


                if (data[4].equalsIgnoreCase("ん") || data[4].equalsIgnoreCase("ン")) {
                    bgname = "bg_circle07_m";
                }


                loadResBgImage(bgname, this.mThirdImageBaseView);


                if(InforChoose.getChooseKana()==0)
                    loadAssetImage(String.format("hiragana/ch_img/%s_1.png", new Object[]{data[2]}), this.mThirdImageView);
                else
                    loadAssetImage(String.format("katakana/ch_img/%s_1.png", new Object[]{data[2]}), this.mThirdImageView);




                this.mThirdTextJpView.setText(data[4]);
                this.mThirdBaseView.setVisibility(View.GONE);
                fontChange(AppConfig.FONT_NAME_KYOKASHO, this.mThirdTextJpView);
                if (data[3].equals("i")) {
                    fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, this.mThirdTextEnView);
                }
                if (this.mNowChar.equalsIgnoreCase(data[4])) {
                    this.mThirdBaseView.setBackgroundResource(R.color.pink);
                    return;
                }
                return;
            }
            this.mThirdBaseView.setVisibility(View.GONE);
        }
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
        it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it);
    }

    private void moveToEndActivity() {
        Intent it = new Intent();
        it.putExtra(AppConfig.RESULT_TIME, this.mTime);
        it.putExtra(AppConfig.RESULT_POINT_LIST, this.mPointArray);
        it.putExtra(AppConfig.RESULT_QUIZ_TYPE, this.mQuizType);
        it.putExtra(AppConfig.RESULT_ALL_TARGET_LIST, this.mTargetList);
        it.putExtra(AppConfig.RESULT_QUIZ_WORD_LIST, this.mQuizWordList);
        it.setClass(this, QuizResultActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
        it.setClass(this, HomeActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
