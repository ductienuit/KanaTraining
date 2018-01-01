package com.nhombabon.kanatraining;

import android.content.Intent;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nhombabon.kanatraining.models.Common;
import com.nhombabon.kanatraining.utils.KanaHorizontalScrollView;

import java.util.ArrayList;

public class IllustMainActivity extends AppBaseActivity {


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
    private ImageView mHintButton;
    private Animation mHintInAnim;
    private Animation mHintOutAnim;
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
                    IllustMainActivity.this.mFrontView.setVisibility(0);
                    IllustMainActivity.this.mFrontTextView.setVisibility(4);
                    IllustMainActivity.this.mBackView.setVisibility(0);
                    IllustMainActivity.this.mBackTextView.setVisibility(4);
                } else if (IllustMainActivity.this.mMovieCount >= 4) {
                    IllustMainActivity.this.mFrontView.setVisibility(0);
                    IllustMainActivity.this.mFrontTextView.setVisibility(4);
                    IllustMainActivity.this.mBackView.setVisibility(4);
                    IllustMainActivity.this.mBackTextView.setVisibility(0);
                }
                IllustMainActivity.this.mFrontBaseView.setVisibility(0);
                IllustMainActivity.this.mBackBaseView.setVisibility(4);
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

    private void calcPage() {
        boolean isNext;
        Log.i("", "calcPage");
        if (this.mScrollX > 0) {
            isNext = true;
        } else {
            isNext = false;
        }
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
            if (this.mNowChar.equals("を")) {
                this.mNowChar = "ん";
            } else if (this.mNowIndex >= 4 || this.mNowIndex >= this.mNowColDataList.size() - 1) {
                nowKey = ((String[]) this.mNowColDataList.get(0))[4];
                common = (Common) getApplication();
                colKeyList = common.getmColKeyList();
                for (i = 0; i < colKeyList.size() - 1; i++) {
                    if (((String) colKeyList.get(i)).equals(nowKey)) {
                        this.mNowIndex = 0;
                        String nextKey = (String) colKeyList.get(i + 1);
                        this.mNowColDataList = (ArrayList) common.getColDataList(nextKey);
                        this.mNowChar = nextKey;
                    }
                }
            } else {
                this.mNowIndex++;
                this.mNowChar = ((String[]) this.mNowColDataList.get(this.mNowIndex))[4];
            }
        } else if (this.mNowChar.equals("ん")) {
            this.mNowChar = "を";
        } else if (this.mNowIndex > 0) {
            this.mNowIndex--;
            this.mNowChar = ((String[]) this.mNowColDataList.get(this.mNowIndex))[4];
        } else {
            nowKey = ((String[]) this.mNowColDataList.get(0))[4];
            common = (Common) getApplication();
            colKeyList = common.getmColKeyList();
            for (i = 1; i < colKeyList.size(); i++) {
                if (((String) colKeyList.get(i)).equals(nowKey)) {
                    this.mNowIndex = 4;
                    this.mNowColDataList = (ArrayList) common.getColDataList((String) colKeyList.get(i - 1));
                    this.mNowChar = ((String[]) this.mNowColDataList.get(this.mNowIndex))[4];
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
        this.mLastPopView.setVisibility(0);
    }

    private void animOutToBottom() {
        this.mLeftIconAreaView.setVisibility(View.GONE);
        this.mRightIconAreaView.setVisibility(View.GONE);
        this.mChartOutAnim.setFillAfter(true);
        this.mMovieOutAnim.setFillAfter(true);
        this.mVoiceOutAnim.setFillAfter(true);
        this.mHintOutAnim.setFillAfter(true);
        this.mChartOutAnim.setDuration((long) 150);
        this.mChartButton.startAnimation(this.mChartOutAnim);
        this.mMovieOutAnim.setStartOffset((long) 40);
        this.mMovieOutAnim.setDuration((long) 150);
        this.mMovieButton.startAnimation(this.mMovieOutAnim);
        this.mVoiceOutAnim.setStartOffset((long) 80);
        this.mVoiceOutAnim.setDuration((long) 150);
        this.mVoiceButton.startAnimation(this.mVoiceOutAnim);
        this.mHintOutAnim.setStartOffset((long) 120);
        this.mHintOutAnim.setDuration((long) 150);
        this.mHintButton.startAnimation(this.mHintOutAnim);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illust_main);
    }


    //Chua
    //Hiển thị từ, ảnh miêu tả, đoạn miêu tả.
    public void ClickIllustText(View v) {
        Intent it = new Intent();
       // it.setClass(this, IllustTextPopActivity.class);
        it.putExtra(AppConfig.SELECTED_CHAR, this.mNowChar);
        startActivity(it);
        overridePendingTransition(R.anim.act_open_rotate_enter, R.anim.act_open_rotate_exit);
    }

    //Chua
    //Click để hiện danh sách và chart của bản chữ
    public void ClickChartList(View v) {
        Intent it = new Intent();
        it.putExtra(AppConfig.SELECTED_CHART, 0);
        it.putExtra(AppConfig.SELECTED_CHAR, this.mNowChar);
       // it.setClass(this, ChartListActivity.class);
        startActivity(it);
    }

    //chạy âm thanh từ x
    public void clickVoice(View v) {
        this.mSoundPool.play(this.mSoundId, 1.0f, 1.0f, 0, 0, 1.0f);
    }

    //Chạy sprite ảnh
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
        loadAssetImage("ch_img/" + ((String[]) ((Common) getApplication()).getCharDataList(this.mNowChar))[2] + "_" + num + ".png", imageView);
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
}
