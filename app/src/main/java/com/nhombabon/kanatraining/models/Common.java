package com.nhombabon.kanatraining.models;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Common extends Application {
    public HashMap<String, String[]> mCharDataList;
    public ArrayList<String> mCharEnList;
    public ArrayList<String> mCharList;
    public HashMap<String, Object> mColDataList;
    public ArrayList<String> mColKeyList;

    Editor mEditor;
    SharedPreferences mPrefs;
    public boolean[] mSelectedList;

    public void init() {
        loadCSV();
    }

    public void loadCSV() {
        InputStream inputStream = null;
        try {
            inputStream = getResources().getAssets().open("data.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
//
//        List<String[]> list = (List<String[]>) new CSVFile(inputStream);
//
//
//        for (int i = 0; i < list.size(); i++) {
//            String[] line = (String[]) list.get(i);
//
//            Log.i("mtSiniChi", line.toString());
//        }
        analyzeCSV(new CSVFile(inputStream).read());
    }

    public void analyzeCSV(List<String[]> list) {
        String colKey = "";
        String key = "";
        String en = "";
        this.mColKeyList = new ArrayList();
        this.mColDataList = new HashMap();
        this.mCharDataList = new HashMap();
        this.mCharList = new ArrayList();
        this.mCharEnList = new ArrayList();
        ArrayList<String[]> colDataList = new ArrayList();
        String prevLineKey = "";

        for (int i = 0; i < list.size(); i++) {
            String[] line = (String[]) list.get(i);
            if (line.length > 3) {
                String lineKey = line[0];
                Log.i("Common", "LineKey " + lineKey);
                if (!lineKey.equals(prevLineKey)) {
                    if (prevLineKey.length() > 0) {
                        Log.i("Common", "ChangeLine " + colKey);
                        this.mColDataList.put(colKey, (Object)colDataList);
                        colDataList = new ArrayList();
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

}
