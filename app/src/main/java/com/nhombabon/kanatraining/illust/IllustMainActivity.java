package com.nhombabon.kanatraining.illust;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Point;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nhombabon.kanatraining.AppBaseActivity;
import com.nhombabon.kanatraining.AppConfig;
import com.nhombabon.kanatraining.HomeActivity;
import com.nhombabon.kanatraining.R;
import com.nhombabon.kanatraining.models.Common;
import com.nhombabon.kanatraining.models.InforChoose;
import com.nhombabon.kanatraining.utils.KanaHorizontalScrollView;

import java.io.IOException;
import java.util.ArrayList;

public class IllustMainActivity extends AppBaseActivity implements Animation.AnimationListener, View.OnTouchListener, KanaHorizontalScrollView.KanaHorizontalScrollListener {
    private View mBackBaseView;
    private TextView mBackTextView;
    private ImageView mBackView;
    private RelativeLayout mBaseView;
    private ImageView mBlurImageView;
    private ImageView mChartButton;
    private Animation mChartInAnim;
    private Animation mChartOutAnim;
    private Animation mFadeInAnim;
    private Animation mFadeOutAnim;
    private View mFrontBaseView;
    private TextView mFrontTextView;
    private ImageView mFrontView;
    private int mIllustType;
    private Animation mInFromRightAnim;
    private boolean mIsButtonOut;
    private boolean mIsFinish;
    private boolean mIsMoviePlaying;
    private boolean mIsWait;
    private View mLastPopView;
    private LinearLayout mLeftIconAreaView;
    private ImageView mLeftIconImageView;
    private View mLeftView;
    private ImageView mMovieButton;
    private int mMovieCount;
    private Animation mMovieInAnim;
    private Animation mMovieOutAnim;
    private String mNextChar = "";
    private String mNowChar;
    private ArrayList<String[]> mNowColDataList;
    private int mNowIndex = 0;
    private int mPageWidth = 0;
    private String mPrevChar = "";
    private LinearLayout mRightIconAreaView;
    private ImageView mRightIconImageView;
    private View mRightView;
    private boolean mScrollAnimating;
    private KanaHorizontalScrollView mScrollView;
    private int mScrollX = 0;
    private int mSoundId;
    private SoundPool mSoundPool;
    private ImageView mVoiceButton;
    private Animation mVoiceInAnim;
    private Animation mVoiceOutAnim;

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    class C01401 implements Runnable {

        class C01391 implements Runnable {
            C01391() {
            }

            public void run() {
                IllustMainActivity.this.startFade(IllustMainActivity.this.mMovieCount + 1);
            }
        }

        C01401() {
        }

        public void run() {
            Log.e("", "OutAnim End " + IllustMainActivity.this.mMovieCount);
            if (IllustMainActivity.this.mMovieCount < 5) {
                if (IllustMainActivity.this.mMovieCount == 3) {
                    IllustMainActivity.this.mFrontView.setVisibility(View.VISIBLE);
                    IllustMainActivity.this.mFrontTextView.setVisibility(View.INVISIBLE);
                    IllustMainActivity.this.mBackView.setVisibility(View.VISIBLE);
                    IllustMainActivity.this.mBackTextView.setVisibility(View.INVISIBLE);
                } else if (IllustMainActivity.this.mMovieCount >= 4) {
                    IllustMainActivity.this.mFrontView.setVisibility(View.VISIBLE);
                    IllustMainActivity.this.mFrontTextView.setVisibility(View.INVISIBLE);
                    IllustMainActivity.this.mBackView.setVisibility(View.INVISIBLE);
                    IllustMainActivity.this.mBackTextView.setVisibility(View.VISIBLE);
                }
                IllustMainActivity.this.mFrontBaseView.setVisibility(View.VISIBLE);
                IllustMainActivity.this.mBackBaseView.setVisibility(View.INVISIBLE);
                IllustMainActivity.this.mHandler.postDelayed(new C01391(), 1000);
                return;
            }
            IllustMainActivity.this.setImageToImageView(1, IllustMainActivity.this.mFrontView);
            IllustMainActivity.this.mIsMoviePlaying = false;
            IllustMainActivity.this.stopMovie();
        }
    }

    class C01412 implements Runnable {
        C01412() {
        }

        public void run() {
            IllustMainActivity.this.mIsWait = true;
            IllustMainActivity.this.calcPage();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illust_main);
        this.mSoundId = -1;
        this.mIsMoviePlaying = false;
        this.mMovieCount = 999;
        this.mIsButtonOut = false;
        this.mIsFinish = false;
        Intent intent = getIntent();
        this.mIllustType = intent.getIntExtra(AppConfig.SELECTED_ILLUST, 0);
        this.mNowChar = intent.getStringExtra(AppConfig.SELECTED_CHAR);
        if (this.mNowChar == null || this.mNowChar.equals("")) {
            if (InforChoose.getChooseKana() == 0)
                this.mNowChar = "あ";
            else
                this.mNowChar = "ア";
        }

        addControl();

        ((TextView) findViewById(R.id.change_title)).setText(getResources().getText(R.string.IllustDetail_ViewName).toString());
        this.mNowIndex = 0;
        this.mScrollAnimating = false;
        this.mSoundPool = new SoundPool(1, 3, 0);


        iloadAnimation();

        changeFont();

        this.mLastPopView.setVisibility(View.GONE);
        findViewById(R.id.quiz_top_button).setVisibility(View.GONE);
        this.mLastPopView.findViewById(R.id.navigation_back_button).setVisibility(View.GONE);
        this.mLastPopView.findViewById(R.id.quiz_top_button).setVisibility(View.GONE);

        adjustLayout();

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

        this.mNowColDataList = ((Common) getApplication()).getColDataList(this.mNowChar);
        this.mNowIndex = 0;
        if (this.mIllustType == 0) {
            this.mLastPopView.findViewById(R.id.btn_pop_button1).setVisibility(View.GONE);
        }
        adjustChar();
    }

    private void iloadAnimation() {
        this.mFadeInAnim = loadAnimation(R.anim.fade_in);
        this.mFadeOutAnim = loadAnimation(R.anim.fade_out);
        this.mInFromRightAnim = loadAnimation(R.anim.in_from_right);
        this.mChartInAnim = loadAnimation(R.anim.in_from_bottom);
        this.mMovieInAnim = loadAnimation(R.anim.in_from_bottom);
        this.mVoiceInAnim = loadAnimation(R.anim.in_from_bottom);
        this.mChartOutAnim = loadAnimation(R.anim.out_to_bottom);
        this.mMovieOutAnim = loadAnimation(R.anim.out_to_bottom);
        this.mVoiceOutAnim = loadAnimation(R.anim.out_to_bottom);
    }

    private void changeFont() {
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, findViewById(R.id.change_title));
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, findViewById(R.id.text_type_catch));
        fontChange(AppConfig.FONT_NAME_LETTER, findViewById(R.id.text_type_en));
        fontChange(AppConfig.FONT_NAME_LETTER, findViewById(R.id.text_type_catch));
        fontChange(AppConfig.FONT_NAME_KYOKASHO, this.mFrontTextView);
        fontChange(AppConfig.FONT_NAME_KYOKASHO, this.mBackTextView);
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, this.mLastPopView.findViewById(R.id.change_title));
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, this.mLastPopView.findViewById(R.id.btn_pop_button1));
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, this.mLastPopView.findViewById(R.id.btn_pop_button2));
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, this.mLastPopView.findViewById(R.id.btn_pop_button3));
    }

    private void addControl() {
        this.mFrontView = findViewById(R.id.change_illust);
        this.mBackView = findViewById(R.id.illust_back_image);
        this.mBackTextView = findViewById(R.id.illust_back_text);
        this.mFrontTextView = findViewById(R.id.illust_front_text);
        this.mFrontBaseView = findViewById(R.id.illust_center_front);
        this.mBackBaseView = findViewById(R.id.illust_center_back);
        this.mScrollView = findViewById(R.id.illust_main_scroll_view);
        this.mScrollView.setOnScrollListener(this);
        this.mScrollView.setOnTouchListener(this);
        this.mScrollView.setSmoothScrollingEnabled(true);
        this.mBaseView = findViewById(R.id.illust_main_base_view);
        this.mLeftView = findViewById(R.id.illust_main_left_view);
        this.mRightView = findViewById(R.id.illust_main_right_view);
        this.mLeftIconImageView = findViewById(R.id.illust_main_left_image);
        this.mRightIconImageView = findViewById(R.id.illust_main_right_image);
        this.mLeftIconAreaView = findViewById(R.id.illust_main_left_arrow_box);
        this.mRightIconAreaView = findViewById(R.id.illust_main_right_arrow_box);
        this.mChartButton = findViewById(R.id.chart_list_button);
        this.mMovieButton = findViewById(R.id.movie_start_button);
        this.mVoiceButton = findViewById(R.id.voice_start_button);
        this.mBlurImageView = findViewById(R.id.pop_blur_image);
        this.mLastPopView = findViewById(R.id.illust_last_pop);
    }

    public void onResume() {
        super.onResume();
    }

    public void finish() {
        clearVoice();
        super.finish();
    }

    private void adjustLayout() {
        Display disp = ((WindowManager) getSystemService("window")).getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        Log.i("", "Size " + size.x + " " + size.y);
        this.mPageWidth = size.x;
        this.mBaseView.setLayoutParams(new LinearLayout.LayoutParams(size.x, size.y));
        this.mLeftView.setLayoutParams(new LinearLayout.LayoutParams(size.x, size.y));
        this.mRightView.setLayoutParams(new LinearLayout.LayoutParams(size.x, size.y));
    }

    private void clearVoice() {
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
        String[] data = common.getCharDataList(this.mNowChar);
        int index = common.getmCharList().indexOf(this.mNowChar);
        switch (this.mIllustType) {
            case 0:
                if (index > 0) {
                    this.mPrevChar = common.getmCharList().get(index - 1);
                    this.mLeftView.setVisibility(View.VISIBLE);
                } else {
                    this.mPrevChar = this.mNowChar;
                    this.mLeftView.setVisibility(View.GONE);
                }
                if (index >= common.getmCharList().size() - 1) {
                    this.mNextChar = this.mNowChar;
                    this.mRightView.setVisibility(View.GONE);
                    break;
                }
                this.mNextChar = common.getmCharList().get(index + 1);
                this.mRightView.setVisibility(View.VISIBLE);
                break;
            case 1:
                if (index <= 0 || this.mNowIndex <= 0) {
                    this.mPrevChar = this.mNowChar;
                    this.mLeftView.setVisibility(View.GONE);
                } else {
                    this.mPrevChar = common.getmCharList().get(index - 1);
                    this.mLeftView.setVisibility(View.VISIBLE);
                }
                if (!this.mNowChar.equals("を") && !this.mNowChar.equals("ヲ")) {
                    if (index < common.getmCharList().size() - 1 && this.mNowIndex < 4 && this.mNowIndex < this.mNowColDataList.size() - 1) {
                        this.mNextChar = common.getmCharList().get(index + 1);
                        this.mRightView.setVisibility(View.VISIBLE);
                        break;
                    }
                    this.mNextChar = this.mNowChar;
                    this.mRightView.setVisibility(View.GONE);
                    break;
                }


                if (InforChoose.getChooseKana() == 0)
                    this.mNextChar = "ん";
                else
                    this.mNextChar = "ン";


                this.mRightView.setVisibility(View.VISIBLE);
                this.mLastPopView.findViewById(R.id.btn_pop_button1).setVisibility(View.GONE);
                break;
            default:
                break;
        }
        String[] prevData = common.getCharDataList(this.mPrevChar);
        String[] nextData = common.getCharDataList(this.mNextChar);
        if (this.mNowChar.equals("い")) {
            fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, findViewById(R.id.text_type_en));
        } else {
            fontChange(AppConfig.FONT_NAME_LETTER, findViewById(R.id.text_type_en));
        }
        this.mBaseView.setBackgroundColor(colorWithHex(data[6]));
        this.mLeftView.setBackgroundColor(colorWithHex(prevData[6]));
        this.mRightView.setBackgroundColor(colorWithHex(nextData[6]));
        this.mBackTextView.setText(this.mNowChar);
        this.mFrontTextView.setText(this.mNowChar);
        int column = Integer.parseInt(data[1]);
        loadResBgImage(String.format("bg_bean%02d", Integer.valueOf(column)), findViewById(R.id.text_beans_bg));
        int leftColumn = Integer.parseInt(prevData[1]);
        int rightColumn = Integer.parseInt(nextData[1]);
        loadResImage(String.format("ic_mark%02d", Integer.valueOf(leftColumn)), this.mLeftIconImageView);
        loadResImage(String.format("ic_mark%02d", Integer.valueOf(rightColumn)), this.mRightIconImageView);
        loadResBgImage(String.format("bg_cycle%02d", Integer.valueOf(column)), findViewById(R.id.illust_center_circle));
        String filename = "hiragana/voice/" + data[2] + ".ogg";
        Log.i("", "Voice File: " + filename);
        AssetManager am = getAssets();
        this.mSoundId = -1;
        try {
            this.mSoundId = this.mSoundPool.load(am.openFd(filename), 1);
            Log.i("", "mSoundId " + this.mSoundId);
        } catch (IOException e2) {
            Log.e("", "Ogg failed.");
            e2.printStackTrace();
        }
        ((TextView) findViewById(R.id.text_hiragana)).setText(this.mNowChar);
        TextView enView = findViewById(R.id.text_type_en);
        enView.setText(data[3]);


        if (InforChoose.getChooseKana() == 0) {
            if (this.mNowChar.equals("を")) {
                enView.setTextScaleX(0.8f);
            } else {
                enView.setTextScaleX(1.0f);
            }
        } else {
            if (this.mNowChar.equals("ヲ")) {
                enView.setTextScaleX(0.8f);
            } else {
                enView.setTextScaleX(1.0f);
            }
        }

        setImageToImageView(1, this.mFrontView);
        setImageToImageView(1, this.mBackView);
        ((TextView) findViewById(R.id.text_type_catch)).setText(spanString(data[5], data[1]));
        if (this.mPrevChar.equals(this.mNowChar)) {
            this.mScrollView.scrollTo(0, 0);
        } else {
            this.mScrollView.scrollTo(this.mPageWidth, 0);
        }
    }

    private void animInFromBottom() {
        this.mLeftIconAreaView.setVisibility(View.VISIBLE);
        this.mRightIconAreaView.setVisibility(View.VISIBLE);
        this.mChartInAnim.setFillAfter(true);
        this.mMovieInAnim.setFillAfter(true);
        this.mVoiceInAnim.setFillAfter(true);
        this.mChartInAnim.setDuration((long) 150);
        this.mChartButton.startAnimation(this.mChartInAnim);
        this.mMovieInAnim.setStartOffset((long) 40);
        this.mMovieInAnim.setDuration((long) 150);
        this.mMovieButton.startAnimation(this.mMovieInAnim);
        this.mVoiceInAnim.setStartOffset((long) 80);
        this.mVoiceInAnim.setDuration((long) 150);
        this.mVoiceButton.startAnimation(this.mVoiceInAnim);
    }

    private void animOutToBottom() {
        this.mLeftIconAreaView.setVisibility(View.GONE);
        this.mRightIconAreaView.setVisibility(View.GONE);
        this.mChartOutAnim.setFillAfter(true);
        this.mMovieOutAnim.setFillAfter(true);
        this.mVoiceOutAnim.setFillAfter(true);
        this.mChartOutAnim.setDuration((long) 150);
        this.mChartButton.startAnimation(this.mChartOutAnim);
        this.mMovieOutAnim.setStartOffset((long) 40);
        this.mMovieOutAnim.setDuration((long) 150);
        this.mMovieButton.startAnimation(this.mMovieOutAnim);
        this.mVoiceOutAnim.setStartOffset((long) 80);
        this.mVoiceOutAnim.setDuration((long) 150);
        this.mVoiceButton.startAnimation(this.mVoiceOutAnim);
    }



    public void ClickChartList(View v) {
        Intent it = new Intent();
        it.putExtra(AppConfig.SELECTED_HOME, 3);
        it.setClass(this, HomeActivity.class);
        startActivity(it);
    }

    public void clickVoice(View v) {
        this.mSoundPool.play(this.mSoundId, 1.0f, 1.0f, 0, 0, 1.0f);
    }

    public void clickMovieStart(View v) {
        this.mIsMoviePlaying = !this.mIsMoviePlaying;
        if (this.mIsMoviePlaying) {
            playMovie();
        } else {
            stopMovie();
        }
    }

    private void playMovie() {
        ((ImageView) findViewById(R.id.movie_start_button)).setImageResource(R.drawable.btn_illust_stop);
        this.mBackView.setVisibility(View.VISIBLE);
        startFade(1);
    }

    private void stopMovie() {
        this.mMovieCount = 999;
        this.mFrontBaseView.clearAnimation();
        this.mBackBaseView.clearAnimation();
        setImageToImageView(1, this.mFrontView);
        this.mFrontView.setVisibility(View.VISIBLE);
        this.mBackView.setVisibility(View.INVISIBLE);
        this.mBackTextView.setVisibility(View.INVISIBLE);
        this.mFrontTextView.setVisibility(View.INVISIBLE);
        ((ImageView) findViewById(R.id.movie_start_button)).setImageResource(R.drawable.btn_illust_playback);
    }

    public void clickNaviBack(View view) {
        finish();
    }

    private void startFade(int num) {
        Log.e("", "StartFade " + num);
        this.mMovieCount = num;
        setImageToImageView(num, this.mFrontView);
        if (num == 4) {
            this.mFrontView.setVisibility(View.VISIBLE);
            this.mFrontTextView.setVisibility(View.INVISIBLE);
            this.mBackView.setVisibility(View.INVISIBLE);
            this.mBackTextView.setVisibility(View.VISIBLE);
        } else if (num >= 5) {
            this.mFrontView.setVisibility(View.INVISIBLE);
            this.mFrontTextView.setVisibility(View.VISIBLE);
            this.mBackView.setVisibility(View.VISIBLE);
            this.mBackTextView.setVisibility(View.INVISIBLE);
            setImageToImageView(1, this.mBackView);
        } else {
            this.mFrontView.setVisibility(View.VISIBLE);
            this.mFrontTextView.setVisibility(View.INVISIBLE);
            this.mBackView.setVisibility(View.VISIBLE);
            this.mBackTextView.setVisibility(View.INVISIBLE);
            setImageToImageView(num + 1, this.mBackView);
        }
        this.mFadeInAnim.setDuration((long) 1000);
        this.mFadeOutAnim.setDuration((long) 1000);
        this.mFadeInAnim.setFillAfter(true);
        this.mFadeOutAnim.setFillAfter(true);
        this.mFadeOutAnim.setAnimationListener(this);
        this.mFrontBaseView.startAnimation(this.mFadeOutAnim);
        this.mBackBaseView.startAnimation(this.mFadeInAnim);
    }

    private void setImageToImageView(int num, ImageView imageView) {
        if (InforChoose.getChooseKana() == 0)
            loadAssetImage("hiragana/ch_img/" + ((Common) getApplication()).getCharDataList(this.mNowChar)[2] + "_" + num + ".png", imageView);
        else
            loadAssetImage("katakana/ch_img/" + ((Common) getApplication()).getCharDataList(this.mNowChar)[2] + "_" + num + ".png", imageView);
    }

    public void clickCenterImageTest(View v) {
    }

    public void onAnimationStart(Animation animation) {
    }

    public void onAnimationEnd(Animation animation) {
        if (animation.equals(this.mFadeOutAnim)) {
            this.mFrontView.post(new C01401());
        } else if (!animation.equals(this.mFadeInAnim)) {
            if (animation.equals(this.mVoiceOutAnim)) {
                this.mIsButtonOut = true;
            } else if (animation.equals(this.mVoiceInAnim)) {
                this.mIsButtonOut = false;
            }
        }
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    private void calcPage() {
        boolean isNext;
        Log.i("", "calcPage");
        isNext = this.mScrollX > 0;
        this.mScrollX = Math.abs(this.mScrollX);
        this.mScrollAnimating = true;
        if (this.mNowChar.equals(this.mNextChar) && this.mScrollX <= 0) {
            Log.i("", "mIsFinish");
            this.mIsFinish = true;
            showLastPop();
        } else if (this.mScrollX > ((int) (((float) this.mPageWidth) * 0.3f))) {
            if (!isNext) {
                this.mScrollView.smoothScrollTo(0, 0);
            } else if (this.mPrevChar.equals(this.mNowChar)) {
                this.mScrollView.smoothScrollTo(this.mPageWidth, 0);
            } else {
                this.mScrollView.smoothScrollTo(this.mPageWidth * 2, 0);
            }
            stopMovie();
            animOutToBottom();
            changeChar(isNext);
        } else if (this.mPrevChar.equals(this.mNowChar)) {
            this.mScrollView.smoothScrollTo(0, 0);
        } else {
            this.mScrollView.smoothScrollTo(this.mPageWidth, 0);
        }
    }

    private void changeChar(boolean isNext) {
        String nowKey;
        Common common;
        ArrayList<String> colKeyList;
        int i;
        if (isNext) {
            if (this.mNowChar.equals("を") || this.mNowChar.equals("ヲ")) {

                if (InforChoose.getChooseKana() == 0)
                    this.mNowChar = "ん";
                else
                    this.mNowChar = "ン";

            } else if (this.mNowIndex >= 4 || this.mNowIndex >= this.mNowColDataList.size() - 1) {
                nowKey = this.mNowColDataList.get(0)[4];
                common = (Common) getApplication();
                colKeyList = common.getmColKeyList();
                for (i = 0; i < colKeyList.size() - 1; i++) {
                    if (colKeyList.get(i).equals(nowKey)) {
                        this.mNowIndex = 0;
                        String nextKey = colKeyList.get(i + 1);
                        this.mNowColDataList = common.getColDataList(nextKey);
                        this.mNowChar = nextKey;
                    }
                }
            } else {
                this.mNowIndex++;
                this.mNowChar = this.mNowColDataList.get(this.mNowIndex)[4];
            }
        } else if (this.mNowChar.equals("ん")  || this.mNowChar.equals("ン")) {

            if (InforChoose.getChooseKana() == 0)
                this.mNowChar = "を";
            else
                this.mNowChar = "ヲ";


        } else if (this.mNowIndex > 0) {
            this.mNowIndex--;
            this.mNowChar = this.mNowColDataList.get(this.mNowIndex)[4];
        } else {
            nowKey = this.mNowColDataList.get(0)[4];
            common = (Common) getApplication();
            colKeyList = common.getmColKeyList();
            for (i = 1; i < colKeyList.size(); i++) {
                if (colKeyList.get(i).equals(nowKey)) {
                    this.mNowIndex = 4;
                    this.mNowColDataList = common.getColDataList(colKeyList.get(i - 1));
                    this.mNowChar = this.mNowColDataList.get(this.mNowIndex)[4];
                }
            }
        }
    }

    private void showLastPop() {
        Log.i("", "Show Last Pop");
        createBlurImage(getViewBitmap(this.mBaseView), this.mBlurImageView);
        this.mInFromRightAnim.setDuration(300);
        this.mInFromRightAnim.setFillAfter(true);
        this.mLastPopView.startAnimation(this.mInFromRightAnim);
        this.mLastPopView.setVisibility(View.VISIBLE);
    }

    public void onScrollChanged(KanaHorizontalScrollView scrollView, int x, int y, int oldx, int oldy) {
        int baseX = this.mPageWidth;
        if (this.mPrevChar.equals(this.mNowChar)) {
            baseX = 0;
        }
        int difX = x - baseX;
        Log.i("", "ScrollChange " + difX);
        this.mScrollX = difX;
    }

    public void onScrollEnd(KanaHorizontalScrollView scrollView) {
        Log.e("", "Scroll End");
        this.mIsWait = false;
        if (this.mScrollAnimating) {
            Log.e("", "Scroll Animating");
            if (this.mIsButtonOut) {
                animInFromBottom();
            }
            adjustChar();
            if (this.mPrevChar.equals(this.mNowChar)) {
                this.mScrollView.scrollTo(0, 0);
            } else {
                this.mScrollView.scrollTo(this.mPageWidth, 0);
            }
            this.mScrollAnimating = false;
        }
    }

    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                Log.i("", "MotionEvent.ACTION_DOWN");
                break;
            case 1:
            case 3:
                Log.i("", "MotionEvent.ACTION_UP");
                if (!this.mIsWait) {
                    this.mHandler.post(new C01412());
                    break;
                }
                break;
        }
        return false;
    }

    public void clickPopNextRow(View v) {
        String nowKey = this.mNowColDataList.get(0)[4];
        Common common = (Common) getApplication();
        ArrayList<String> colKeyList = common.getmColKeyList();
        for (int i = 0; i < colKeyList.size() - 1; i++) {
            if (colKeyList.get(i).equals(nowKey)) {
                this.mNowIndex = 0;
                String nextKey = colKeyList.get(i + 1);
                this.mNowColDataList = common.getColDataList(nextKey);
                this.mNowChar = nextKey;
            }
        }
        this.mIsFinish = false;
        adjustChar();
        this.mLastPopView.clearAnimation();
        this.mLastPopView.setVisibility(View.GONE);
    }

    public void clickPopSeeAgain(View v) {
        this.mNowIndex = 0;
        switch (this.mIllustType) {
            case 0:
                Common common = (Common) getApplication();
                this.mNowChar = common.getmColKeyList().get(0);
                this.mNowColDataList = common.getColDataList(this.mNowChar);
                break;
            case 1:
                this.mNowChar = this.mNowColDataList.get(0)[4];
                break;
        }
        this.mIsFinish = false;
        adjustChar();
        this.mLastPopView.clearAnimation();
        this.mLastPopView.setVisibility(View.GONE);
    }

    public void clickPopBackToTop(View v) {
        Intent it = new Intent();
        it.setClass(this, IllustSelectTypeActivity.class);
        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        it.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(it);
    }
}
