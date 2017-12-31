package com.nhombabon.kanatraining;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.nhombabon.kanatraining.models.InforChoose;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by DucTien on 04/11/2017.
 * Load data from crv and take it to struct data
 */

public class Common extends Application {
    private static final String TAG = "Common";

    public HashMap<String, String[]> mCharDataList;
    public ArrayList<String> mCharEnList;
    public ArrayList<String> mCharList;
    public HashMap<String, ArrayList<String[]>> mColDataList;
    public ArrayList<String> mColKeyList;
    SharedPreferences.Editor mEditor;
    SharedPreferences mPrefs;
    public boolean[] mSelectedList;

    public void init() {
        initPrefs();
        loadCSV();
    }

    //Chua chinh load hiragana hay katakana
    public void loadCSV() {
        InputStream inputStream = null;
        try {
            if(InforChoose.getChooseKana()==0)
                inputStream = getResources().getAssets().open("katakana/data.csv");
            else
                inputStream = getResources().getAssets().open("katakana/data.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Read file CSV to List<String[]> by ReadCSVFile
        analyzeCSV(new ReadCSVFile(inputStream).read());
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

        ArrayList<String[]> colDataList = new ArrayList<String[]>();
        String prevLineKey = "";
        for (int i = 0; i < list.size(); i++) {
            String[] line = (String[]) list.get(i);
            Log.i(TAG, "1.Line: " + line);
            if (line.length > 3) {
                String lineKey = line[0];
                Log.i(TAG, "2.LineKey: " + lineKey);
                if (!lineKey.equals(prevLineKey)) {
                    if (prevLineKey.length() > 0) {
                        Log.i(TAG, "3.ChangeLine: " + colKey);
                        this.mColDataList.put(colKey,  colDataList);
                        colDataList = new ArrayList();
                    }
                    colKey = line[4];
                    Log.i(TAG, "4.ColKey: " + colKey);
                    this.mColKeyList.add(colKey);
                }
                key = line[4];
                Log.i(TAG, "5.key: " + colKey);
                en = line[3];
                Log.i(TAG, "6.en: " + colKey);
                this.mCharList.add(key);
                this.mCharEnList.add(en);
                colDataList.add(line);
                this.mCharDataList.put(key, line);
                prevLineKey = lineKey;
            }
        }
        Log.i(TAG, "Final:ChangeLine " + colKey);
        this.mColDataList.put(colKey, colDataList);
    }

    //Init sharedPresferences in android, like cache
    public void initPrefs() {
        this.mPrefs = getSharedPreferences("hmh_prefs", 0);
        this.mEditor = this.mPrefs.edit();
    }

    public void setPref(String key, String value) {
        this.mEditor.putString(key, value);
        this.mEditor.apply();
    }

    public String getPref(String key) {
        return this.mPrefs.getString(key, "");
    }
}
