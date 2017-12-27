package com.nhombabon.kanatraining.Quiz;

import android.content.Intent;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nhombabon.kanatraining.AppBaseActivity;
import com.nhombabon.kanatraining.AppConfig;
import com.nhombabon.kanatraining.Common;
import com.nhombabon.kanatraining.MainActivity;
import com.nhombabon.kanatraining.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class QuizMainActivity extends AppBaseActivity implements AnimationListener, OnTouchListener {
    private RelativeLayout mBaseView;
    private boolean mCanAnswer;
    private View mChoiceArea;
    private View mChoiceButton1;
    private View mChoiceButton2;
    private View mChoiceButton3;
    private TextView mChoiceInner1;
    private TextView mChoiceInner2;
    private TextView mChoiceInner3;
    private ArrayList<String> mChoiceList;
    private View mContinueView;
    private View mCorrectView;
    private boolean mCountable;
    private View mCoverView;
    private int mCutTime;
    private Animation mFadeInAnim;
    private Animation mFadeOutAnim;
    private ImageView mHintBlurView;
    private TextView mHintDescView;
    private ImageView mHintImageView;
    private View mHintView;
    private boolean mIsMoving;
    private String mLastAnswerChar;
    private ImageView mMainListenView;
    private TextView mMainView;
    private int mMissCount;
    private View mMissView;
    private String mNowChar;
    private int mNowIndex;
    private View mNowStarView;
    private int[] mPointArray;
    private int mQuizType;
    private ArrayList<String> mQuizWordList;
    private Animation mScaleDownAnim;
    private Animation mScaleUpAnim;
    private int mSeSoundId;
    private SoundPool mSeSoundPool;
    private int mSoundId;
    private SoundPool mSoundPool;
    private ArrayList<String> mTargetList;
    private int mTime;
    private TextView mTimeView;
    private Timer mTimer = null;
    private ImageView mTopBlurView;
    private int mZanTime;



    class C01421 implements OnCompletionListener {
        C01421() {
        }

        public void onCompletion(MediaPlayer mp) {
            mp.release();
        }
    }

    class C01432 extends TimerTask {
        C01432() {
        }

        public void run() {
            QuizMainActivity.this.loadVoice();
        }
    }

    class C01443 implements OnCompletionListener {
        C01443() {
        }

        public void onCompletion(MediaPlayer mp) {
            mp.release();
        }
    }

    class C01454 implements Runnable {
        C01454() {
        }

        public void run() {
            QuizMainActivity.this.correctEnd();
        }
    }

    class C01465 implements OnCompletionListener {
        C01465() {
        }

        public void onCompletion(MediaPlayer mp) {
            mp.release();
        }
    }

    class gameCountTask extends TimerTask {

        class C01481 implements Runnable {
            C01481() {
            }

            public void run() {
                if (QuizMainActivity.this.mCountable) {
                    QuizMainActivity.this.gameTimeCount();
                }
            }
        }

        gameCountTask() {
        }

        public void run() {
            QuizMainActivity.this.mHandler.post(new C01481());
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_main);
        this.mNowIndex = 0;
        this.mNowChar = "";
        this.mMissCount = 0;
        this.mCountable = false;
        setCanAnswer(true);
        this.mLastAnswerChar = "";
        this.mChoiceList = new ArrayList();
        this.mBaseView = (RelativeLayout) findViewById(R.id.quiz_base_view);
        this.mContinueView = findViewById(R.id.pop_quiz_top);
        this.mCorrectView = findViewById(R.id.img_quiz_correct);
        this.mMissView = findViewById(R.id.img_quiz_miss);
        this.mChoiceArea = findViewById(R.id.choice_area);
        this.mHintView = findViewById(R.id.pop_hint);
        this.mHintImageView = (ImageView) findViewById(R.id.hint_illust);
        this.mHintDescView = (TextView) findViewById(R.id.hint_catch);
        this.mChoiceInner1 = (TextView) findViewById(R.id.left_questions_button);
        this.mChoiceInner2 = (TextView) findViewById(R.id.right_questions_button);
        this.mChoiceInner3 = (TextView) findViewById(R.id.bottom_questions_button);
        this.mChoiceButton1 = findViewById(R.id.left_questions_button_base);
        this.mChoiceButton2 = findViewById(R.id.right_questions_button_base);
        this.mChoiceButton3 = findViewById(R.id.bottom_questions_button_base);
        this.mMainView = (TextView) findViewById(R.id.questions_area_jp);
        this.mMainListenView = (ImageView) findViewById(R.id.questions_listen);
        this.mTimeView = (TextView) findViewById(R.id.time_measurement);
        this.mCoverView = findViewById(R.id.quiz_main_cover);
        this.mCoverView.setVisibility(View.GONE);
        this.mHintBlurView = (ImageView) findViewById(R.id.pop_hint_blur_image);
        this.mTopBlurView = (ImageView) findViewById(R.id.pop_top_blur_image);
        this.mFadeInAnim = loadAnimation(R.anim.fade_in);
        this.mFadeOutAnim = loadAnimation(R.anim.fade_out);
        this.mScaleUpAnim = loadAnimation(R.anim.scale_up);
        this.mScaleDownAnim = loadAnimation(R.anim.scale_down);
        this.mChoiceButton1.setOnTouchListener(this);
        this.mChoiceButton2.setOnTouchListener(this);
        this.mChoiceButton3.setOnTouchListener(this);
        this.mChoiceInner1.setScaleX(0.9f);
        this.mChoiceInner1.setScaleY(0.9f);
        this.mChoiceInner2.setScaleX(0.9f);
        this.mChoiceInner2.setScaleY(0.9f);
        this.mChoiceInner3.setScaleX(0.9f);
        this.mChoiceInner3.setScaleY(0.9f);
        findViewById(R.id.quiz_top_button).setVisibility(View.VISIBLE);
        findViewById(R.id.navigation_back_button).setVisibility(View.GONE);
        this.mSoundId = -1;
        this.mSeSoundId = -1;
        Intent intent = getIntent();
        this.mQuizType = intent.getIntExtra(AppConfig.RESULT_QUIZ_TYPE, 0);
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, (TextView) findViewById(R.id.change_title));
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, (TextView) findViewById(R.id.time_title));
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, (TextView) findViewById(R.id.time_measurement));
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, (TextView) findViewById(R.id.question_number));
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, (TextView) findViewById(R.id.hint_catch));
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, (TextView) findViewById(R.id.quiz_stop_txt));
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, (TextView) findViewById(R.id.btn_no));
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, (TextView) findViewById(R.id.btn_yes));
        this.mTime = intent.getIntExtra(AppConfig.RESULT_TIME, 0);
        this.mPointArray = intent.getIntArrayExtra(AppConfig.RESULT_POINT_LIST);
        this.mQuizType = intent.getIntExtra(AppConfig.RESULT_QUIZ_TYPE, 0);
        this.mTargetList = intent.getStringArrayListExtra(AppConfig.RESULT_ALL_TARGET_LIST);
        this.mQuizWordList = intent.getStringArrayListExtra(AppConfig.RESULT_QUIZ_WORD_LIST);
    }

    public void onResume() {
        super.onResume();
        Log.i("", "QuizMainActivity - onResume");
        this.mIsMoving = false;
        this.mCorrectView.setVisibility(View.GONE);
        this.mMissView.setVisibility(View.GONE);
        loadResBgImage("btn_cycle02", this.mChoiceInner1);
        loadResBgImage("btn_cycle02", this.mChoiceInner2);
        loadResBgImage("btn_cycle02", this.mChoiceInner3);
        this.mChoiceInner1.setVisibility(View.VISIBLE);
        this.mChoiceInner2.setVisibility(View.VISIBLE);
        this.mChoiceInner3.setVisibility(View.VISIBLE);
        this.mCutTime = this.mTime / 60;
        this.mZanTime = this.mTime - (this.mCutTime * 60);
        this.mTimeView.setText(String.format("%d.%02d", new Object[]{this.mCutTime, this.mZanTime}));
        startTimer();
        View hintButton = findViewById(R.id.quiz_hint_button);
              /*0 is for VISIBLE
        4 is for INVISIBLE
        8 is for GONE*/
        if (this.mQuizType == 3) {
            hintButton.setVisibility(View.GONE);
        } else {
            hintButton.setVisibility(View.VISIBLE);
        }
        calcNowIndex();
        this.mNowChar = (String) this.mQuizWordList.get(this.mNowIndex);
        adjustTitle();
        adjustChar();
        this.mCountable = true;
        this.mNowStarView = (ImageView) findViewById(R.id.quiz_star_area).findViewWithTag(String.format("%d", new Object[]{Integer.valueOf(this.mNowIndex)}));
        Common common = (Common) getApplication();
        String[] data = (String[]) common.mCharDataList.get(this.mNowChar);
        String romaji1 = "";
        String romaji2 = "";
        String romaji3 = "";
        if (this.mChoiceList.size() > 0) {
            romaji1 = ((String[]) common.mCharDataList.get(this.mChoiceList.get(0)))[3];
        }
        if (this.mChoiceList.size() > 1) {
            romaji2 = ((String[]) common.mCharDataList.get(this.mChoiceList.get(1)))[3];
        }
        if (this.mChoiceList.size() > 2) {
            romaji3 = ((String[]) common.mCharDataList.get(this.mChoiceList.get(2)))[3];
        }
        switch (this.mQuizType) {
            case 0:
                fontChange(AppConfig.FONT_NAME_KYOKASHO, (TextView) findViewById(R.id.questions_area_jp));
                fontChange(AppConfig.FONT_NAME_LETTER, (TextView) findViewById(R.id.left_questions_button));
                fontChange(AppConfig.FONT_NAME_LETTER, (TextView) findViewById(R.id.right_questions_button));
                fontChange(AppConfig.FONT_NAME_LETTER, (TextView) findViewById(R.id.bottom_questions_button));
                if (romaji1.equals("i")) {
                    fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, (TextView) findViewById(R.id.left_questions_button));
                }
                if (romaji2.equals("i")) {
                    fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, (TextView) findViewById(R.id.right_questions_button));
                }
                if (romaji3.equals("i")) {
                    fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, (TextView) findViewById(R.id.bottom_questions_button));
                    return;
                }
                return;
            case 1:
            case 2:
            case 3:
                if (data[3].equals("i")) {
                    fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, this.mMainView);
                } else {
                    fontChange(AppConfig.FONT_NAME_LETTER, this.mMainView);
                }
                fontChange(AppConfig.FONT_NAME_KYOKASHO, (TextView) findViewById(R.id.left_questions_button));
                fontChange(AppConfig.FONT_NAME_KYOKASHO, (TextView) findViewById(R.id.right_questions_button));
                fontChange(AppConfig.FONT_NAME_KYOKASHO, (TextView) findViewById(R.id.bottom_questions_button));
                return;
            default:
                return;
        }
    }

    public void onPause() {
        super.onPause();
        stopTimer();
    }

    public void adjustTitle() {
        TextView titleView = (TextView) findViewById(R.id.change_title);
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

    public void startTimer() {
        stopTimer();
        this.mTimer = new Timer();
        this.mTimer.scheduleAtFixedRate(new gameCountTask(), 1, 1000);
    }

    public void stopTimer() {
        if (this.mTimer != null) {
            this.mTimer.cancel();
            this.mTimer = null;
        }
    }

    private void calcNowIndex() {
        for (int i = 0; i < this.mPointArray.length; i++) {
            if (this.mPointArray[i] == 0) {
                this.mNowIndex = i;
                return;
            }
        }
    }

    private void adjustStar() {
        View starArea = findViewById(R.id.quiz_star_area);
        for (int i = 0; i < 10; i++) {
            ImageView star = (ImageView) starArea.findViewWithTag(String.format("%d", new Object[]{Integer.valueOf(i)}));
            switch (this.mPointArray[i]) {
                case -1:
                case 0:
                    loadResImage("ic_star_normal", star);
                    break;
                case 1:
                    loadResImage("ic_star_active", star);
                    break;
                default:
                    break;
            }
        }
    }

    private void loadVoice() {
        String[] data = (String[]) ((Common) getApplication()).mCharDataList.get(this.mNowChar);
        if (this.mQuizType == 2) {
            String filename = "hiragana/voice/" + data[2] + ".ogg";
            Log.i("", "Voice File: " + filename);
            AssetManager am = getAssets();
            this.mSoundId = -1;
            MediaPlayer player = getAssetsMediaFile(getResources(), filename);
            player.setOnCompletionListener(new C01421());
            player.start();
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
        if (common.mCharDataList == null) {
            Log.e("common.mCharDataList", "null");
            common.init();
        }
        String[] data = (String[]) common.mCharDataList.get(this.mNowChar);
        adjustStar();
        makeChoiceList();
        new Timer().schedule(new C01432(), 1000);
        ((TextView) findViewById(R.id.question_number)).setText(String.format("%d/%d", new Object[]{Integer.valueOf(this.mNowIndex + 1), Integer.valueOf(10)}));
        switch (this.mQuizType) {
            case 0:
                this.mMainView.setText(this.mNowChar);
                this.mMainView.setVisibility(View.VISIBLE);
                this.mMainListenView.setVisibility(View.GONE);
                break;
            case 1:
            case 3:
                if (data[3].equalsIgnoreCase("N")) {
                    this.mMainView.setText(String.format(" %s ", new Object[]{data[3]}));
                } else {
                    this.mMainView.setText(data[3]);
                }
                this.mMainView.setVisibility(View.VISIBLE);
                this.mMainListenView.setVisibility(View.GONE);
                break;
            case 2:
                this.mMainView.setVisibility(View.GONE);
                this.mMainListenView.setVisibility(View.VISIBLE);
                break;
        }
        int column = Integer.parseInt(data[1]);
        switch (this.mQuizType) {
            case 0:
                loadAssetImage("hiragana/ch_img/" + data[2] + "_1.png", this.mHintImageView);
                this.mHintImageView.setVisibility(View.VISIBLE);
                loadResBgImage(String.format("bg_cycle%02d", new Object[]{Integer.valueOf(column)}), this.mHintImageView);
                this.mHintDescView.setVisibility(View.GONE);
                break;
            case 1:
            case 2:
                Log.i("", "CHOOSE " + data[5]);
                this.mHintDescView.setText(spanString(data[5], data[1]));
                this.mHintDescView.setVisibility(View.VISIBLE);
                loadResBgImage(String.format("bg_cycle%02d", new Object[]{Integer.valueOf(column)}), this.mHintDescView);
                this.mHintImageView.setVisibility(View.GONE);
                break;
        }
        TextView firstView = (TextView) findViewById(R.id.left_questions_button);
        TextView secondView = (TextView) findViewById(R.id.right_questions_button);
        TextView thirdView = (TextView) findViewById(R.id.bottom_questions_button);
        if (this.mChoiceList.size() > 0) {
            Log.i("", "Choice List1 " + ((String) this.mChoiceList.get(0)));
        }
        if (this.mChoiceList.size() > 1) {
            Log.i("", "Choice List2 " + ((String) this.mChoiceList.get(1)));
        }
        if (this.mChoiceList.size() > 2) {
            Log.i("", "Choice List3 " + ((String) this.mChoiceList.get(2)));
        }
        if (this.mChoiceList.size() >= 1) {
            String[] data1 = (String[]) common.mCharDataList.get(this.mChoiceList.get(0));
            switch (this.mQuizType) {
                case 0:
                    firstView.setText(data1[3]);
                    break;
                case 1:
                case 2:
                case 3:
                    firstView.setText(data1[4]);
                    break;
            }
        }
        if (this.mChoiceList.size() >= 2) {
            String[] data2 = (String[]) common.mCharDataList.get(this.mChoiceList.get(1));
            switch (this.mQuizType) {
                case 0:
                    secondView.setText(data2[3]);
                    break;
                case 1:
                case 2:
                case 3:
                    secondView.setText(data2[4]);
                    break;
            }
        }
        if (this.mChoiceList.size() >= 3) {
            String[] data3 = (String[]) common.mCharDataList.get(this.mChoiceList.get(2));
            switch (this.mQuizType) {
                case 0:
                    thirdView.setText(data3[3]);
                    break;
                case 1:
                case 2:
                case 3:
                    thirdView.setText(data3[4]);
                    break;
            }
            this.mChoiceButton3.setVisibility(View.VISIBLE);
            return;
        }
        this.mChoiceButton3.setVisibility(View.INVISIBLE);
    }

    private void makeChoiceList() {
        Common common = (Common) getApplication();
        String[] data = (String[]) common.mCharDataList.get(this.mNowChar);
        this.mChoiceList.clear();
        int i;
        if (this.mQuizType == 3) {
            String nearWord = data[7];
            String[] words = nearWord.split("/");
            Log.i("", "Near Word " + nearWord);
            for (Object indexOf : words) {
                int index = common.mCharEnList.indexOf(indexOf);
                if (index >= 0) {
                    this.mChoiceList.add((String) common.mCharList.get(index));
                }
            }
        } else {
            this.mChoiceList.add(this.mNowChar);
            ArrayList<String> copyTargetList = new ArrayList(this.mTargetList);
            String word2 = null;
            String word3 = null;
            Collections.shuffle(copyTargetList);
            for (i = 0; i < copyTargetList.size(); i++) {
                if (!((String) copyTargetList.get(i)).equals(this.mNowChar)) {
                    if (word2 == null) {
                        word2 = (String) copyTargetList.get(i);
                    } else if (word3 == null) {
                        word3 = (String) copyTargetList.get(i);
                    }
                }
            }
            if (word2 != null) {
                this.mChoiceList.add(word2);
            }
            if (word3 != null) {
                this.mChoiceList.add(word3);
            }
        }
        Collections.shuffle(this.mChoiceList);
    }

    public void clickCover(View v) {
        int point = Integer.parseInt((String) v.getTag());
        Log.e("", "clickCover point:" + point + " correct:" + 1);
        if (point == 1) {
            Log.e("", "correctEnd");
            correctEnd();
            return;
        }
        missEnd(this.mLastAnswerChar);
    }

    public void clickChoice(View v) {
        TextView inner = null;
        AssetManager am;
        MediaPlayer player;

        switch (Integer.parseInt((String) v.getTag())) {
            case 0:
                inner = this.mChoiceInner1;
                break;
            case 1:
                inner = this.mChoiceInner2;
                break;
            case 2:
                inner = this.mChoiceInner3;
                break;
        }
        loadResBgImage("btn_cycle01", inner);
        setCanAnswer(false);
        this.mCountable = false;
        final String answerChar = (String) this.mChoiceList.get(Integer.parseInt((String) v.getTag()));
        this.mLastAnswerChar = answerChar;
        if (answerChar.equals(this.mNowChar)) {
            this.mCoverView.setTag(Integer.toString(1));
            this.mCoverView.setVisibility(View.VISIBLE);
            this.mCorrectView.setVisibility(View.VISIBLE);
            startStarScaleUp();
            this.mPointArray[this.mNowIndex] = 1;
            am = getAssets();
            this.mSeSoundId = -1;
            player = getAssetsMediaFile(getResources(), "hiragana/music/crrect.ogg");
            player.setOnCompletionListener(new C01443());
            player.start();
            this.mHandler.postDelayed(new C01454(), 1000);
            return;
        }
        this.mCoverView.setTag(Integer.toString(-1));
        this.mCoverView.setVisibility(View.VISIBLE);
        this.mMissView.setVisibility(View.VISIBLE);
        this.mMissCount++;
        am = getAssets();
        player = getAssetsMediaFile(getResources(), "hiragana/music/miss.ogg");
        player.setOnCompletionListener(new C01465());
        player.start();
        this.mHandler.postDelayed(new Runnable() {
            public void run() {
                QuizMainActivity.this.missEnd(answerChar);
            }
        }, 1000);
    }

    private void correctEnd() {
        moveToNextActivity();
    }

    private void missEnd(String prevAnswerChar) {
        int prevNum = this.mChoiceList.indexOf(prevAnswerChar);
        if (this.mChoiceList.size() - this.mMissCount > 1) {
            this.mChoiceArea.findViewWithTag(Integer.toString(prevNum)).setVisibility(View.INVISIBLE);
            setCanAnswer(true);
            this.mCountable = true;
            this.mCoverView.setVisibility(View.GONE);
            this.mMissView.setVisibility(View.GONE);
            return;
        }
        this.mPointArray[this.mNowIndex] = -1;
        this.mCoverView.setVisibility(View.GONE);
        moveToNextActivity();
    }

    private void moveToNextActivity() {
        if (!this.mIsMoving) {
            this.mIsMoving = true;
            Intent it = new Intent();
            it.putExtra(AppConfig.RESULT_TIME, this.mTime);
            it.putExtra(AppConfig.RESULT_POINT_LIST, this.mPointArray);
            it.putExtra(AppConfig.RESULT_QUIZ_TYPE, this.mQuizType);
            it.putExtra(AppConfig.RESULT_ALL_TARGET_LIST, this.mTargetList);
            it.putExtra(AppConfig.RESULT_QUIZ_WORD_LIST, this.mQuizWordList);
            if (this.mQuizType == 3) {
                it.putExtra(AppConfig.RESULT_CHOICE_LIST, this.mChoiceList);
                it.setClass(this,MainActivity.class);
                Log.i("QuizAnswerSimilar","Loi o day");
            } else {
                it.setClass(this, QuizAnswerActivity.class);
            }
            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(it);
            dealloc();
        }
    }

    public void clickQuizTop(View v) {
        this.mCountable = false;
        showContinueView(true);
    }

    public void clickTopNo(View v) {
        showContinueView(false);
    }



    private void setCanAnswer(boolean canAnswer) {
        this.mCanAnswer = canAnswer;
        TextView secondView = (TextView) findViewById(R.id.right_questions_button);
        TextView thirdView = (TextView) findViewById(R.id.bottom_questions_button);
        ((TextView) findViewById(R.id.left_questions_button)).setEnabled(this.mCanAnswer);
        secondView.setEnabled(this.mCanAnswer);
        thirdView.setEnabled(this.mCanAnswer);
    }

    private void gameTimeCount() {
        if (this.mCountable) {
            this.mTime++;
            this.mCutTime = this.mTime / 60;
            this.mZanTime = this.mTime - (this.mCutTime * 60);
            this.mTimeView.setText(String.format("%d:%02d", this.mCutTime, this.mZanTime));
        }
    }

    private void startStarScaleUp() {
        loadResImage("ic_star_active", (ImageView) this.mNowStarView);
        this.mScaleUpAnim.setDuration(300);
        this.mScaleUpAnim.setFillAfter(true);
        this.mNowStarView.startAnimation(this.mScaleUpAnim);
    }

    private void startStarScaleDown() {
        this.mScaleDownAnim.setDuration(300);
        this.mScaleDownAnim.setFillAfter(true);
        this.mNowStarView.startAnimation(this.mScaleDownAnim);
    }

    public void clickTopYes(View v) {
        Intent it = new Intent();
        //it.setClass(this, QuizTopActivity.class);
        it.setClass(this, MainActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        it.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
        startActivity(it);
        dealloc();
    }

    public void clickQuizHint(View v) {
        this.mCountable = false;
        showHintView(true);
    }

    public void clickHintView(View v) {
        showHintView(false);
    }

    private void showHintView(boolean show) {
        createBlurImage(getViewBitmap(this.mBaseView), this.mHintBlurView);
        this.mHintView.setVisibility(View.VISIBLE);
        if (show) {
            this.mFadeInAnim.setDuration((long) 250);
            this.mFadeInAnim.setFillAfter(true);
            this.mHintView.startAnimation(this.mFadeInAnim);
            return;
        }
        this.mFadeOutAnim.setDuration((long) 250);
        this.mFadeOutAnim.setFillAfter(true);
        this.mHintView.startAnimation(this.mFadeOutAnim);
    }

    private void showContinueView(boolean show) {
        createBlurImage(getViewBitmap(this.mBaseView), this.mTopBlurView);
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

    public void ClickQuestionsButton(View v) {
        if (this.mCanAnswer) {
            clickChoice(v);
        }
    }

    public void clickNaviBack(View view) {
        finish();
    }

    public void clickQuizVoice(View v) {
        loadVoice();
    }


    public void onAnimationStart(Animation animation) {
    }

    public void onAnimationEnd(Animation animation) {
        if (animation.equals(this.mFadeOutAnim)) {
            this.mContinueView.setVisibility(View.GONE);
            this.mHintView.setVisibility(View.GONE);
            this.mContinueView.clearAnimation();
            this.mHintView.clearAnimation();
            this.mCountable = true;
        } else if (animation.equals(this.mFadeInAnim)) {
            this.mContinueView.clearAnimation();
            this.mHintView.clearAnimation();
        } else if (animation.equals(this.mScaleUpAnim)) {
            this.mNowStarView.clearAnimation();
            startStarScaleDown();
        } else if (animation.equals(this.mScaleDownAnim)) {
            this.mNowStarView.clearAnimation();
        }
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public boolean onTouch(View v, MotionEvent event) {
        TextView inner = null;
        switch (Integer.parseInt((String) v.getTag())) {
            case 0:
                inner = this.mChoiceInner1;
                break;
            case 1:
                inner = this.mChoiceInner2;
                break;
            case 2:
                inner = this.mChoiceInner3;
                break;
        }
        switch (event.getAction()) {
            case 0:
                inner.setScaleX(1.0f);
                inner.setScaleY(1.0f);
                break;
            case 1:
                inner.setScaleX(0.9f);
                inner.setScaleY(0.9f);
                break;
        }
        return false;
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

    protected void onDestroy() {
        super.onDestroy();
    }

    public void finish() {
        Log.e("finished", "finish");
        super.finish();
    }

    private void dealloc() {
        Runtime runtime = Runtime.getRuntime();
        Log.v("Runtime", "トータルメモリ[KB] = " + ((int) (runtime.totalMemory() / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID)));
        Log.v("Runtime", "空きメモリ[KB] = " + ((int) (runtime.freeMemory() / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID)));
        Log.v("Runtime", "現在使用しているメモリ[KB] = " + ((int) ((runtime.totalMemory() - runtime.freeMemory()) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID)));
        Log.v("Runtime", "Dalvikで使用できる最大メモリ[KB] = " + ((int) (runtime.maxMemory() / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID)));
    }
}

