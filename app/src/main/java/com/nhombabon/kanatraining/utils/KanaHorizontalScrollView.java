package com.nhombabon.kanatraining.utils;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by DucTien on 01/01/2018.
 */

public class KanaHorizontalScrollView extends HorizontalScrollView {private Handler mHandler = new Handler();
    private boolean mIsChanged;
    private boolean mIsScrolling;
    private int mPrevScrollX;
    private int mScrollEndCount;
    private final int mScrollEndCountMax = 3;
    private ObsHorizontalScrollListener mScrollListener = null;
    private Timer mTimer = null;

    public interface ObsHorizontalScrollListener {
        void onScrollChanged(KanaHorizontalScrollView KanaHorizontalScrollView, int i, int i2, int i3, int i4);

        void onScrollEnd(KanaHorizontalScrollView KanaHorizontalScrollView);
    }

    class ObsScrollTask extends TimerTask {

        class C01531 implements Runnable {
            C01531() {
            }

            public void run() {
                if (KanaHorizontalScrollView.this.mIsChanged) {
                    KanaHorizontalScrollView.this.mScrollEndCount = 0;
                } else {
                    KanaHorizontalScrollView access$0 = KanaHorizontalScrollView.this;
                    access$0.mScrollEndCount = access$0.mScrollEndCount + 1;
                    if (KanaHorizontalScrollView.this.mScrollEndCount >= 3 && KanaHorizontalScrollView.this.mIsScrolling) {
                        KanaHorizontalScrollView.this.mIsScrolling = false;
                        if (KanaHorizontalScrollView.this.mScrollListener != null) {
                            KanaHorizontalScrollView.this.mScrollListener.onScrollEnd(KanaHorizontalScrollView.this);
                        }
                    }
                }
                KanaHorizontalScrollView.this.mIsChanged = false;
            }
        }

        ObsScrollTask() {
        }

        public void run() {
            KanaHorizontalScrollView.this.mHandler.post(new C01531());
        }
    }

    public KanaHorizontalScrollView(Context context) {
        super(context);
        init();
    }

    public KanaHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KanaHorizontalScrollView(Context context, AttributeSet attrs, int defs) {
        super(context, attrs, defs);
        init();
    }

    private void init() {
        this.mIsScrolling = false;
        this.mPrevScrollX = 0;
        this.mScrollEndCount = 0;
        this.mIsChanged = false;
        startTimer();
    }

    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    public void setOnScrollListener(ObsHorizontalScrollListener scrollListener) {
        this.mScrollListener = scrollListener;
    }

    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        this.mIsScrolling = true;
        this.mIsChanged = true;
        if (this.mScrollListener != null) {
            this.mScrollListener.onScrollChanged(this, x, y, oldx, oldy);
        }
        this.mPrevScrollX = x;
    }

    public void startTimer() {
        stopTimer();
        this.mTimer = new Timer();
        this.mTimer.scheduleAtFixedRate(new ObsScrollTask(), 1, 100);
    }

    public void stopTimer() {
        if (this.mTimer != null) {
            this.mTimer.cancel();
            this.mTimer = null;
        }
    }
}
