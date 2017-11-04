package com.nhombabon.kanatraining;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class QuizSelectListActivity extends AppBaseActivity {
    private boolean mCanGoNext;
    private int mQuizType = -1;
    private boolean[] mSelectedList;
    private View mStartButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        int i = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_select_list);
        this.mSelectedList = new boolean[5];
        for (i = 0; i < 5; i++) {
            this.mSelectedList[i] = false;
        }
        this.mCanGoNext = false;
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, (TextView) findViewById(R.id.change_title));
        fontChange(AppConfig.FONT_NAME_LETTER, (TextView) findViewById(R.id.select_line_button1));
        fontChange(AppConfig.FONT_NAME_LETTER, (TextView) findViewById(R.id.select_line_button2));
        fontChange(AppConfig.FONT_NAME_LETTER, (TextView) findViewById(R.id.select_line_button3));
        fontChange(AppConfig.FONT_NAME_LETTER, (TextView) findViewById(R.id.select_line_button4));
        fontChange(AppConfig.FONT_NAME_LETTER, (TextView) findViewById(R.id.select_line_button5));
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, (TextView) findViewById(R.id.select_title));
        fontChange(AppConfig.FONT_NAME_FIRA_LIGHT, (TextView) findViewById(R.id.start_quiz_button));
        this.mStartButton = findViewById(R.id.start_quiz_button);
        this.mQuizType = getIntent().getIntExtra(AppConfig.SELECTED_QUIZ, 0);
        Log.i("", "mQuizType " + this.mQuizType);
        View similarView = findViewById(R.id.quiz_select_similar);
        View tableView = findViewById(R.id.quiz_select_table);
        View titleView = findViewById(R.id.select_title);
        /*0 is for VISIBLE
        4 is for INVISIBLE
        8 is for GONE*/
        if (this.mQuizType == 3) {
            similarView.setVisibility(View.VISIBLE);
            tableView.setVisibility(View.GONE);
            titleView.setVisibility(View.GONE);
            for (i = 0; i < 5; i++) {
                this.mSelectedList[i] = true;
            }
        } else {
            similarView.setVisibility(View.GONE);
            tableView.setVisibility(View.VISIBLE);
            titleView.setVisibility(View.VISIBLE);
        }
        adjustTitle();
        adjustCellColor();
    }

    public void adjustTitle() {
        TextView titleView = (TextView) findViewById(R.id.change_title);
        switch (this.mQuizType) {
            case 0:
                titleView.setText(getResources().getString(R.string.Quiz_Question1));
                return;
            case 1:
                titleView.setText(getResources().getString(R.string.Quiz_Question2));
                return;
            case 2:
                titleView.setText(getResources().getString(R.string.Quiz_Question3));
                return;
            case 3:
                titleView.setText(getResources().getString(R.string.Quiz_Question4));
                return;
            default:
                return;
        }
    }

    public void clickCell(View v) {
        int tag = Integer.parseInt((String) v.getTag());
        this.mSelectedList[tag] = !this.mSelectedList[tag];
        adjustCellColor();
    }

    private void adjustCellColor() {
        View table = findViewById(R.id.quiz_select_table);
        boolean isSelected = false;
        for (int i = 0; i < 5; i++) {
            TextView cell = (TextView) table.findViewWithTag(Integer.toString(i));
            if (cell != null) {
                if (this.mSelectedList[i]) {
                    isSelected = true;
                    cell.setBackgroundResource(R.drawable.bg_quiz_select_active);
                    cell.setTextColor(-1);
                } else {
                    cell.setBackgroundResource(R.drawable.bg_quiz_select_normal);
                    cell.setTextColor(ViewCompat.MEASURED_STATE_MASK);
                }
            }
        }
        if (isSelected || this.mQuizType == 3) {
            loadResBgImage("btn_cycle01", this.mStartButton);
            this.mCanGoNext = true;
            return;
        }
        loadResBgImage("btn_cycle04", this.mStartButton);
        this.mCanGoNext = false;
    }

    public void clickQuizStart(View v) {
        if (this.mCanGoNext) {
            pickupQuizData();
        }
    }

    public void clickNaviBack(View view) {
        finish();
    }

    private void pickupQuizData() {
        int i=0;
        /*Must to add android:name"Common" to AndroidManifest.xml
        because Common inherit class Application*/
        Common common = (Common) getApplication();
        if (common.mColKeyList == null) {
            common.init();
        }
        ArrayList<String> targetList = new ArrayList<String>();
        ArrayList<String> quizWordList = new ArrayList<String>();
        for (i = 0; i < common.mColKeyList.size(); i++) {
            Log.i("", "Key " + ((String) common.mColKeyList.get(i)));
        }
        for (i = 0; i < 5; i++) {
            if (this.mSelectedList[i]) {
                int di;
                String[] d;
                ArrayList<String[]> data1 =
                        (ArrayList) common.mColDataList.get((String) common.mColKeyList.get((i * 2) + 0));
                ArrayList<String[]> data2 =
                        (ArrayList) common.mColDataList.get((String) common.mColKeyList.get((i * 2) + 1));
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
                    d = (String[]) common.mCharDataList.get("ん");
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
        common.mSelectedList = this.mSelectedList;
        Intent it = new Intent();
        it.putExtra(AppConfig.RESULT_TIME, 0);
        it.putExtra(AppConfig.RESULT_POINT_LIST, pointArray);
        it.putExtra(AppConfig.RESULT_QUIZ_TYPE, this.mQuizType);
        it.putExtra(AppConfig.RESULT_ALL_TARGET_LIST, targetList);
        it.putExtra(AppConfig.RESULT_QUIZ_WORD_LIST, quizWordList);
        //it.setClass(this, QuizMainActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //startActivity(it);
    }
}

