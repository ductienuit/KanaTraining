package com.nhombabon.kanatraining.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

import java.util.Timer;
import java.util.TimerTask;

public class ObsHorizontalScrollView extends HorizontalScrollView {
    private Handler mHandler = new Handler();
    private boolean mIsChanged;
    private boolean mIsScrolling;
    private int mPrevScrollX;
    private int mScrollEndCount;
    private final int mScrollEndCountMax = 3;
    private ObsHorizontalScrollListener mScrollListener = null;
    private Timer mTimer = null;

    public interface ObsHorizontalScrollListener {
        void onScrollChanged(ObsHorizontalScrollView obsHorizontalScrollView, int i, int i2, int i3, int i4);

        void onScrollEnd(ObsHorizontalScrollView obsHorizontalScrollView);
    }

    class ObsScrollTask extends TimerTask {

        class C01531 implements Runnable {
            C01531() {
            }

            public void run() {
                if (ObsHorizontalScrollView.this.mIsChanged) {
                    ObsHorizontalScrollView.this.mScrollEndCount = 0;
                } else {
                    ObsHorizontalScrollView access$0 = ObsHorizontalScrollView.this;
                    access$0.mScrollEndCount = access$0.mScrollEndCount + 1;
                    if (ObsHorizontalScrollView.this.mScrollEndCount >= 3 && ObsHorizontalScrollView.this.mIsScrolling) {
                        ObsHorizontalScrollView.this.mIsScrolling = false;
                        if (ObsHorizontalScrollView.this.mScrollListener != null) {
                            ObsHorizontalScrollView.this.mScrollListener.onScrollEnd(ObsHorizontalScrollView.this);
                        }
                    }
                }
                ObsHorizontalScrollView.this.mIsChanged = false;
            }
        }

        ObsScrollTask() {
        }

        public void run() {
            ObsHorizontalScrollView.this.mHandler.post(new C01531());
        }
    }

    public ObsHorizontalScrollView(Context context) {
        super(context);
        init();
    }

    public ObsHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ObsHorizontalScrollView(Context context, AttributeSet attrs, int defs) {
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
