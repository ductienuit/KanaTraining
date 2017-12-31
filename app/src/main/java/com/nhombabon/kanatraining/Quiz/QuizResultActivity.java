package com.nhombabon.kanatraining.Quiz;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nhombabon.kanatraining.AppBaseActivity;
import com.nhombabon.kanatraining.AppConfig;
import com.nhombabon.kanatraining.Common;
import com.nhombabon.kanatraining.HomeActivity;
import com.nhombabon.kanatraining.R;
import com.nhombabon.kanatraining.models.InforChoose;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class QuizResultActivity extends AppBaseActivity {

    private RelativeLayout mBaseView;
    private int mBestTime;
    private TextView mBestTimeView;
    private String mClearText;
    private Animation mFadeInAndZoomAnim;
    private Animation mFadeInAnim;
    private Animation mFadeOutAnim;
    private Boolean mIsAnimFinish = Boolean.valueOf(false);
    private int[] mPointArray;
    private int mQuizType;
    private ArrayList<String> mQuizWordList;
    private View mResetView;
    private int mScore;
    private int mScoreCountUp = 0;
    private ScoreLoopEngine mScoreLoopEngine = new ScoreLoopEngine();
    private TextView mScoreTextView;
    private TextView mScoreView;
    private ArrayList<String> mTargetList;
    private int mTextCountUp = 0;
    private TextLoopEngine mTextLoopEngine = new TextLoopEngine();
    private int mTime;
    private int mTimeCountUp = 0;
    private TimeLoopEngine mTimeLoopEngine = new TimeLoopEngine();
    private TextView mTimeView;
    private int cutTime;


    class ScoreLoopEngine extends Handler {
        private boolean isUpdate;

        ScoreLoopEngine() {
        }

        public void start() {
            this.isUpdate = true;
            handleMessage(new Message());
        }

        public void stop() {
            this.isUpdate = false;
        }

        public void handleMessage(Message msg) {
            removeMessages(0);
            if (this.isUpdate) {
                QuizResultActivity.this.updateScore();
                sendMessageDelayed(obtainMessage(0), 150);
            }
        }
    }

    public void updateScore() {
        this.mScoreCountUp++;
        if (this.mScoreCountUp > this.mScore) {
            this.mScoreLoopEngine.stop();
            return;
        }
        this.mScoreView.setText(String.format("%d/%d", new Object[]{Integer.valueOf(this.mScoreCountUp), Integer.valueOf(10)}));
    }

    class TextLoopEngine extends Handler {
        private boolean isUpdate;

        TextLoopEngine() {
        }

        public void start() {
            this.isUpdate = true;
            handleMessage(new Message());
        }

        public void stop() {
            this.isUpdate = false;
        }

        public void handleMessage(Message msg) {
            removeMessages(0);
            if (this.isUpdate) {
                QuizResultActivity.this.updateText();
                sendMessageDelayed(obtainMessage(0), 100);
            }
        }
    }

    public void updateText() {
        this.mTextCountUp++;
        if (this.mTextCountUp > this.mClearText.length()) {
            this.mTextLoopEngine.stop();
            this.mScoreLoopEngine.start();
            this.mTimeLoopEngine.start(this.mTime);
            return;
        }
        this.mScoreTextView.setText(this.mClearText.substring(0, this.mTextCountUp));
    }

    class TimeLoopEngine extends Handler {
        private boolean isUpdate;
        private int speedTime;

        TimeLoopEngine() {
        }

        public void start(int time) {
            this.isUpdate = true;
            this.speedTime = 2000 / (time * 1000);
            handleMessage(new Message());
        }

        public void stop() {
            this.isUpdate = false;
        }

        public void handleMessage(Message msg) {
            removeMessages(0);
            if (this.isUpdate) {
                QuizResultActivity.this.updateTime();
                sendMessageDelayed(obtainMessage(0), (long) this.speedTime);
            }
        }
    }

    public void updateTime() {
        this.mTimeCountUp++;
        int zanTime=0;
        if (this.mTimeCountUp > this.mTime) {
            this.mTimeLoopEngine.stop();
            this.mIsAnimFinish = Boolean.valueOf(true);

            if (this.mBestTime == this.mTime) {
                zanTime = this.mBestTime - ((this.mBestTime / 60) * 60);
                this.mBestTimeView.setText(String.format("%d:%02d", new Object[]{Integer.valueOf(cutTime), Integer.valueOf(zanTime)}));
                this.mFadeInAndZoomAnim.setDuration(1000);
                this.mFadeInAndZoomAnim.setFillAfter(false);
                this.mBestTimeView.startAnimation(this.mFadeInAndZoomAnim);
                return;
            }
            return;
        }
        zanTime = this.mTimeCountUp - ((this.mTimeCountUp / 60) * 60);
        this.mTimeView.setText(String.format("%d:%02d", new Object[]{Integer.valueOf(cutTime), Integer.valueOf(zanTime)}));
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);



        //Lấy id view
        this.mScoreView = (TextView) findViewById(R.id.txt_quiz_score);
        this.mTimeView = (TextView) findViewById(R.id.txt_quiz_time);
        this.mBestTimeView = (TextView) findViewById(R.id.txt_quiz_time_best);
        this.mBaseView = (RelativeLayout) findViewById(R.id.quiz_result_base_view);
        this.mResetView = findViewById(R.id.pop_quiz_top);
        this.mScoreTextView = (TextView) findViewById(R.id.text_animation_result1);
        ((AnimationDrawable) ((ImageView) findViewById(R.id.animation_result1)).getBackground()).start();


        //Lấy dữ liệu từ activity trước đó để hiển thị
        Intent intent = getIntent();
        //Lấy thời gian hoàn thành quiz trước đó
        this.mTime = intent.getIntExtra(AppConfig.RESULT_TIME, 0);
        this.cutTime = mTime/60;   //Lấy giây
        this.mPointArray = intent.getIntArrayExtra(AppConfig.RESULT_POINT_LIST);
        this.mQuizType = intent.getIntExtra(AppConfig.RESULT_QUIZ_TYPE, 0);
        this.mTargetList = intent.getStringArrayListExtra(AppConfig.RESULT_ALL_TARGET_LIST);
        this.mQuizWordList = intent.getStringArrayListExtra(AppConfig.RESULT_QUIZ_WORD_LIST);

        //Load animation để tạo hiệu ứng đặc sắc cho view
        this.mFadeInAnim = loadAnimation(R.anim.fade_in);
        this.mFadeOutAnim = loadAnimation(R.anim.fade_out);
        this.mFadeInAndZoomAnim = loadAnimation(R.anim.fade_in_and_zoom);


        //Lấy besttime trong sharePreference (Không gian lưu trữ tạm thời)
        Common common = (Common) getApplication();
        String bestTimeStr = common.getPref(AppConfig.PREF_QUIZ_BEST_TIME);
        if (bestTimeStr == null || bestTimeStr.equals("")) {
            this.mBestTime = AppConfig.QUIZ_BEST_TIME_DEFAULT;
        } else {
            this.mBestTime = Integer.parseInt(bestTimeStr);
        }
        Log.e("bestTimeStr", "bestTimeStr:" + this.mBestTime);
        Log.e("bestTimeStr", "mTime:" + this.mTime);
        if (this.mBestTime > this.mTime) {
            this.mBestTime = this.mTime;
            this.mBestTimeView.setText("00:00");
            common.setPref(AppConfig.PREF_QUIZ_BEST_TIME, Integer.toString(this.mTime));
        } else if (this.mBestTime > 0) {
            int zanTime = this.mBestTime - ((this.mBestTime / 60) * 60);
            this.mBestTimeView.setText(String.format("%d:%02d", new Object[]{Integer.valueOf(cutTime), Integer.valueOf(zanTime)}));
        }


        //Cài lại font cho các chữ cái trong view
        fontChange(AppConfig.FONT_NAME_FIRA_EXTRA, this.mScoreTextView);
        fontChange(AppConfig.FONT_NAME_FIRA_ULTRA, (TextView) findViewById(R.id.txt_quiz_score));
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, (TextView) findViewById(R.id.title_quiz_score));
        fontChange(AppConfig.FONT_NAME_FIRA_ULTRA, (TextView) findViewById(R.id.txt_quiz_time));
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, (TextView) findViewById(R.id.title_quiz_time));
        fontChange(AppConfig.FONT_NAME_FIRA_ULTRA, (TextView) findViewById(R.id.txt_quiz_time_best));
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, (TextView) findViewById(R.id.title_quiz_time_best));
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, (TextView) findViewById(R.id.quiz_retry_button));
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, (TextView) findViewById(R.id.quiz_menu_button));



        this.mScoreTextView.setText("");
        this.mScoreView.setText(String.format("0/%d", new Object[]{Integer.valueOf(10)}));
        this.mTimeView.setText("00:00");
        adjustResult();
    }

    public void adjustResult() {
        this.mScore = 0;
        for (int i : this.mPointArray) {
            if (i == 1) {
                this.mScore++;
            }
        }
        Resources res = getResources();
        if (this.mScore == 10) {
            this.mClearText = res.getString(R.string.QuizResult_Clear1);
        } else if (this.mScore >= 5) {
            this.mClearText = res.getString(R.string.QuizResult_Clear2);
        } else {
            this.mClearText = res.getString(R.string.QuizResult_Clear3);
        }
        this.mTextLoopEngine.start();
    }

    public void clickRetryButton(View v) {
        if (this.mIsAnimFinish.booleanValue()) {
            int i;
            this.mTime = 0;
            for (i = 0; i < this.mPointArray.length; i++) {
                this.mPointArray[i] = 0;
            }
            Common common = (Common) getApplication();
            if (common.getmColKeyList() == null) {
                common.init();
            }
            ArrayList<String> targetList = new ArrayList();
            ArrayList<String> quizWordList = new ArrayList();
            for (i = 0; i < common.getmColKeyList().size(); i++) {
                Log.i("", "Key " + ((String) common.getColKeyList(i)));
            }
            for (i = 0; i < 5; i++) {
                if (common.mSelectedList[i]) {
                    int di;


                    String[] d;
                    ArrayList<String[]> data1 = (ArrayList) common.getColDataList((String) common.getColKeyList((i * 2) + 0));
                    ArrayList<String[]> data2 = (ArrayList) common.getColDataList((String) common.getColKeyList((i * 2) + 1));


                    for (di = 0; di < data1.size(); di++) {
                        d = (String[]) data1.get(di);
                        if (this.mQuizType != 3) {
                            targetList.add(d[4]);
                        } else if (d.length > 7 && !d[7].equals("")) {
                            targetList.add(d[4]);
                        }
                    }
                    for (di = 0; di < data2.size(); di++) {
                        d = (String[]) data2.get(di);
                        if (this.mQuizType != 3) {
                            targetList.add(d[4]);
                        } else if (d.length > 7 && !d[7].equals("")) {
                            targetList.add(d[4]);
                        }
                    }
                    if (i == 4) {


                        if(InforChoose.getChooseKana()==0)
                            d = (String[]) common.getCharDataList("ん");
                        else
                            d = (String[]) common.getCharDataList("ン");


                        if (this.mQuizType != 3) {
                            targetList.add(d[4]);
                        } else if (d.length > 7 && !d[7].equals("")) {
                            targetList.add(d[4]);
                        }
                    }
                }
            }
            Log.i("", "all target added!");
            Collections.shuffle(targetList);
            Log.i("", "random done!");
            int count = targetList.size();
            if (count < 10) {
                Log.i("", "plus start!");
                ArrayList<Integer> addList = new ArrayList();
                Random rand = new Random();
                while (addList.size() + count < 10) {
                    int n = rand.nextInt(count);
                    Log.i("", "n " + n);
                    boolean isAdded = false;
                    for (i = 0; i < addList.size(); i++) {
                        if (((Integer) addList.get(i)).intValue() == n) {
                            Log.i("", "is added.");
                            isAdded = true;
                            break;
                        }
                    }
                    if (!isAdded) {
                        addList.add(Integer.valueOf(n));
                    }
                }
                Log.i("", "plus done!");
                for (i = 0; i < targetList.size(); i++) {
                    quizWordList.add((String) targetList.get(i));
                }
                for (i = 0; i < addList.size(); i++) {
                    quizWordList.add((String) targetList.get(((Integer) addList.get(i)).intValue()));
                }
            } else if (count >= 10) {
                Log.i("", "minus start!");
                for (i = 0; i < 10; i++) {
                    quizWordList.add((String) targetList.get(i));
                }
                Log.i("", "minus done!");
            }
            for (i = 0; i < quizWordList.size(); i++) {
                Log.i("", "Word: " + ((String) quizWordList.get(i)));
            }
            int[] pointArray = new int[10];
            for (i = 0; i < 10; i++) {
                pointArray[i] = 0;
            }

            //Chuyển qua activity
            Intent it = new Intent();
            it.putExtra(AppConfig.RESULT_TIME, 0);
            it.putExtra(AppConfig.RESULT_POINT_LIST, pointArray);
            it.putExtra(AppConfig.RESULT_QUIZ_TYPE, this.mQuizType);
            it.putExtra(AppConfig.RESULT_ALL_TARGET_LIST, targetList);
            it.putExtra(AppConfig.RESULT_QUIZ_WORD_LIST, quizWordList);
            it.setClass(getApplication(), QuizMainActivity.class);
            it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(it);

        }
    }

    public void clickQuizMenuButton(View v) {
        if (this.mIsAnimFinish.booleanValue()) {
            Intent it = new Intent();
            it.setClass(this, HomeActivity.class);
            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(it);
        }
    }
}
