package com.nhombabon.kanatraining.fragment;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nhombabon.kanatraining.models.Common;
import com.nhombabon.kanatraining.R;
import com.nhombabon.kanatraining.models.InforChoose;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kanatraining on 12/11/2017.
 */

public class KatakanaFragment extends Fragment {
    private int mChartType;
    private String mSelectedChar = null;
    private int mSoundId;
    private SoundPool mSoundPool;

    public HashMap<String, String[]> mCharDataList;
    public ArrayList<String> mCharEnList;
    public ArrayList<String> mCharList;
    public HashMap<String, ArrayList<String[]>> mColDataList;
    public ArrayList<String> mColKeyList;

    class C01301 implements SoundPool.OnLoadCompleteListener {
        C01301() {
        }

        public void onLoadComplete(SoundPool sp, int sampleId, int status) {
            KatakanaFragment.this.mSoundPool.play(KatakanaFragment.this.mSoundId, 1.0f, 1.0f, 0, 0, 1.0f);
        }
    }

    class C01312 implements View.OnClickListener {
        C01312() {
        }

        public void onClick(View v) {
            KatakanaFragment.this.loadVoice(((TextView) v).getText().toString());
        }
    }

    class C01323 implements View.OnClickListener {
        C01323() {
        }

        public void onClick(View v) {
            KatakanaFragment.this.loadVoice(((TextView) v).getText().toString());
        }
    }

    class C01334 implements View.OnClickListener {
        C01334() {
        }

        public void onClick(View v) {
            KatakanaFragment.this.loadVoice(((TextView) v).getText().toString());
        }
    }

    class C01345 implements View.OnClickListener {
        C01345() {
        }

        public void onClick(View v) {
            KatakanaFragment.this.loadVoice(((TextView) v).getText().toString());
        }
    }

    class C01356 implements View.OnClickListener {
        C01356() {
        }

        public void onClick(View v) {
            KatakanaFragment.this.loadVoice(((TextView) v).getText().toString());
        }
    }

    public void loadCSV(View view) {
        InputStream inputStream = null;
        try {
            inputStream = getResources().getAssets().open("katakana/data.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //analyzeCSV(new CSVFile(inputStream).read());
    }

    public void analyzeCSV(List<String[]> list) {
        String colKey = "";
        String key = "";
        String en = "";
        this.mColKeyList = new ArrayList<String>();
        this.mColDataList = new HashMap<String, ArrayList<String[]>>();
        this.mCharDataList = new HashMap<String, String[]>();
        this.mCharList = new ArrayList<String>();
        this.mCharEnList = new ArrayList<String>();
        ArrayList<String[]> colDataList = new ArrayList<String[]>();
        String prevLineKey = "";

        for (int i = 0; i < list.size(); i++) {
            String[] line = (String[]) list.get(i);
            if (line.length > 3) {
                String lineKey = line[0];
                Log.i("Common", "LineKey " + lineKey);
                if (!lineKey.equals(prevLineKey)) {
                    if (prevLineKey.length() > 0) {
                        Log.i("Common", "ChangeLine " + colKey);
                        this.mColDataList.put(colKey, (ArrayList<String[]>) colDataList);
                        colDataList = new ArrayList<String[]>();
                    }
                    colKey = line[4];
                    Log.i("Common", "ColKey " + colKey);
                    this.mColKeyList.add(colKey);
                }
                key = line[4];
                en = line[3];
                this.mCharList.add(key);
                this.mCharEnList.add(en);
                colDataList.add(line);
                this.mCharDataList.put(key, line);
                prevLineKey = lineKey;
            }
        }
        Log.i("Common", "ChangeLine " + colKey);
        this.mColDataList.put(colKey, colDataList);
    }

    private void fontChange(String fontName, TextView v) {
        Typeface face;
        face = Typeface.createFromAsset(getActivity().getAssets(), "font/" + fontName);

        v.setTypeface(face);
    }

    private void makeChartTable(View view) {
        //Gọi application để đọc file crv add vào list
        Common common;
        try {
            common = (Common) getActivity().getApplication();
        } catch (Exception e) {
            e.printStackTrace();
            common = (Common) getActivity().getApplication();
        }
        if (common.getmCharDataList() == null) {
            Log.e("common.mCharDataList", "null");
            common.init();
        }


        int i;
        LinearLayout baseLayout = (LinearLayout) view.findViewById(R.id.chart_list_main_view);
        baseLayout.removeAllViews();

        ArrayList<String> colKeyList = common.mColKeyListKatakana;
        HashMap<String, ArrayList<String[]>> colDataList = common.mColDataListKatakana;
        boolean clickable = true;
        if (this.mChartType == 0) {
            clickable = false;
        }
        for (i = 0; i < colKeyList.size(); i++) {
            ArrayList<String[]> dataList = (ArrayList<String[]>) colDataList.get((String) colKeyList.get(i));
            View lineLayout = getActivity().getLayoutInflater().inflate(R.layout.chart_input, null);
            lineLayout.setTag(i);
            baseLayout.addView(lineLayout);
            TextView headView = (TextView) lineLayout.findViewById(R.id.chart_input_text_header);
            TextView aView = (TextView) lineLayout.findViewById(R.id.chart_input_text_a);
            TextView iView = (TextView) lineLayout.findViewById(R.id.chart_input_text_i);
            TextView uView = (TextView) lineLayout.findViewById(R.id.chart_input_text_u);
            TextView eView = (TextView) lineLayout.findViewById(R.id.chart_input_text_e);
            TextView oView = (TextView) lineLayout.findViewById(R.id.chart_input_text_o);
            fontChange("LetterGothicStd-Slanted.otf", headView);
            aView.setText("");
            iView.setText("");
            uView.setText("");
            eView.setText("");
            oView.setText("");

            for (int li = 0; li < dataList.size(); li++) {
                String[] data = (String[]) dataList.get(li);

                if (data.length >= 2) {
                    int pos = (Integer.parseInt(data[1]) - 1) % 5;
                    Log.i("kanatraining", "mt-pos" + pos);
                    String hiragana = data[4];
                    switch (pos) {
                        case 0:
                            if (i == 0) {
                                headView.setText("");
                            } else {
                                headView.setText(data[0]);
                            }
                            if (data.length <= 3) {
                                break;
                            }
                            aView.setText(hiragana);
                            if (hiragana.equals("ン")) {
                                aView.setBackgroundResource(R.drawable.bg_circle08_s);
                            }
                            if (this.mSelectedChar != null && hiragana.equals(this.mSelectedChar)) {
                                aView.setBackgroundResource(R.drawable.bg_circle09_s);
                                aView.setTextColor(-1);
                            }
                            if (clickable && !hiragana.equals("")) {
                                aView.setClickable(true);
                                aView.setOnClickListener(new KatakanaFragment.C01312());
                                break;
                            }

                            break;
                        case 1:
                            if (data.length <= 3) {
                                break;
                            }
                            iView.setText(hiragana);
                            if (this.mSelectedChar != null && hiragana.equals(this.mSelectedChar)) {
                                iView.setBackgroundResource(R.drawable.bg_circle09_s);
                                iView.setTextColor(-1);
                            }
                            if (clickable && !hiragana.equals("")) {
                                iView.setClickable(true);
                                iView.setOnClickListener(new KatakanaFragment.C01323());
                                break;
                            }

                            break;
                        case 2:
                            if (data.length <= 3) {
                                break;
                            }
                            uView.setText(hiragana);
                            if (this.mSelectedChar != null && hiragana.equals(this.mSelectedChar)) {
                                uView.setBackgroundResource(R.drawable.bg_circle09_s);
                                uView.setTextColor(-1);
                            }
                            if (clickable && !hiragana.equals("")) {
                                uView.setClickable(true);
                                uView.setOnClickListener(new KatakanaFragment.C01334());
                                break;
                            }

                            break;
                        case 3:
                            if (data.length <= 3) {
                                break;
                            }
                            eView.setText(hiragana);
                            if (this.mSelectedChar != null && hiragana.equals(this.mSelectedChar)) {
                                eView.setBackgroundResource(R.drawable.bg_circle09_s);
                                eView.setTextColor(-1);
                            }
                            if (clickable && !hiragana.equals("")) {
                                eView.setClickable(true);
                                eView.setOnClickListener(new KatakanaFragment.C01345());
                                break;
                            }

                            break;
                        case 4:
                            if (data.length <= 3) {
                                break;
                            }
                            oView.setText(hiragana);
                            if (this.mSelectedChar != null && hiragana.equals(this.mSelectedChar)) {
                                oView.setBackgroundResource(R.drawable.bg_circle09_s);
                                oView.setTextColor(-1);
                            }
                            if (clickable && !hiragana.equals("")) {
                                oView.setClickable(true);
                                oView.setOnClickListener(new KatakanaFragment.C01356());
                                break;
                            }

                            break;
                        default:
                            break;
                    }
                }
            }
        }
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2, 1.0f);
        for (i = 0; i < colKeyList.size(); i++) {
            baseLayout.findViewWithTag(i).setLayoutParams(layoutParams);
        }
    }

    public void loadVoice(String cha) {
        //Gọi application để đọc file crv add vào list
        Common common;
        try {
            common = (Common) getActivity().getApplication();
        } catch (Exception e) {
            e.printStackTrace();
            common = (Common) getActivity().getApplication();
        }
        if (common.getmCharDataList() == null) {
            Log.e("common.mCharDataList", "null");
            common.init();
        }

        String filename = "katakana/voice/" + (common.mCharDataListKatakana.get(cha))[2] + ".ogg";
        Log.i("kanatraining", "Voice File: " + filename);
        AssetManager am = getActivity().getAssets();
        this.mSoundId = -1;
        try {
            this.mSoundId = this.mSoundPool.load(am.openFd(filename), 1);
            Log.i("", "mSoundId " + this.mSoundId);
        } catch (IOException e) {
            Log.e("", "Ogg failed.");
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.chart_list, container, false);

        this.mSoundId = -1;
        this.mChartType = 1;

        this.mSoundPool = new SoundPool(1, 3, 0);
        this.mSoundPool.setOnLoadCompleteListener(new KatakanaFragment.C01301());

        makeChartTable(view);

        return view;
    }

}