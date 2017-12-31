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

    private HashMap<String, String[]> mCharDataListHiragana;
    private HashMap<String, String[]> mCharDataListKatakana;

    public ArrayList<String> mCharEnListHiragana;
    public ArrayList<String> mCharListHiragana;
    public HashMap<String, ArrayList<String[]>> mColDataListHiragana;
    public ArrayList<String> mColKeyListHiragana;


    public ArrayList<String> mCharEnListKatakana;
    public ArrayList<String> mCharListKatakana;
    public HashMap<String, ArrayList<String[]>> mColDataListKatakana;
    public ArrayList<String> mColKeyListKatakana;


    SharedPreferences.Editor mEditor;
    SharedPreferences mPrefs;
    public boolean[] mSelectedList;

    public void init() {
        initPrefs();
        loadCSV();
    }

    public void loadCSV() {
        InputStream inputStreamHiragana = null;
        InputStream inputStreamKatakana = null;
        try {
            inputStreamHiragana = getResources().getAssets().open("hiragana/data.csv");
            inputStreamKatakana = getResources().getAssets().open("katakana/data.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }


        //Read file CSV to List<String[]> by ReadCSVFile
        analyzeCSVHira(new ReadCSVFile(inputStreamHiragana).read());

        //Read file CSV to List<String[]> by ReadCSVFile
        analyzeCSVKata(new ReadCSVFile(inputStreamKatakana).read());
    }

    public void analyzeCSVHira(List<String[]> list) {
        String colKey = "";
        String key = "";
        String en = "";


        this.mColKeyListHiragana = new ArrayList();
        this.mColDataListHiragana = new HashMap();
        this.mCharDataListHiragana = new HashMap();
        this.mCharListHiragana = new ArrayList();
        this.mCharEnListHiragana = new ArrayList();

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
                        this.mColDataListHiragana.put(colKey,  colDataList);
                        colDataList = new ArrayList();
                    }
                    colKey = line[4];
                    Log.i(TAG, "4.ColKey: " + colKey);
                    this.mColKeyListHiragana.add(colKey);
                }
                key = line[4];
                Log.i(TAG, "5.key: " + colKey);
                en = line[3];
                Log.i(TAG, "6.en: " + colKey);
                this.mCharListHiragana.add(key);
                this.mCharEnListHiragana.add(en);
                colDataList.add(line);
                this.mCharDataListHiragana.put(key, line);
                prevLineKey = lineKey;
            }
        }
        Log.i(TAG, "Final:ChangeLine " + colKey);
        this.mColDataListHiragana.put(colKey, colDataList);
    }

    public void analyzeCSVKata(List<String[]> list) {
        String colKey = "";
        String key = "";
        String en = "";


        this.mColKeyListKatakana = new ArrayList();
        this.mColDataListKatakana = new HashMap();
        this.mCharDataListKatakana = new HashMap();
        this.mCharListKatakana = new ArrayList();
        this.mCharEnListKatakana = new ArrayList();

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
                        this.mColDataListKatakana.put(colKey,  colDataList);
                        colDataList = new ArrayList();
                    }
                    colKey = line[4];
                    Log.i(TAG, "4.ColKey: " + colKey);
                    this.mColKeyListKatakana.add(colKey);
                }
                key = line[4];
                Log.i(TAG, "5.key: " + colKey);
                en = line[3];
                Log.i(TAG, "6.en: " + colKey);
                this.mCharListKatakana.add(key);
                this.mCharEnListKatakana.add(en);
                colDataList.add(line);
                this.mCharDataListKatakana.put(key, line);
                prevLineKey = lineKey;
            }
        }
        Log.i(TAG, "Final:ChangeLine " + colKey);
        this.mColDataListKatakana.put(colKey, colDataList);
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


    public ArrayList<String> getmCharList() {
        if(InforChoose.getChooseKana()==0)
            return mCharListHiragana;
        return  mCharListKatakana;
    }

    public String[] getCharDataList(String key)
    {
        if(InforChoose.getChooseKana()==0)
            return mCharDataListHiragana.get(key);
        return  mCharDataListKatakana.get(key);
    }

    public HashMap<String, String[]> getmCharDataList()
    {
        if(InforChoose.getChooseKana()==0)
            return mCharDataListHiragana;
        return  mCharDataListKatakana;
    }

    public ArrayList<String[]> getColDataList(String key)
    {
        if(InforChoose.getChooseKana()==0)
            return mColDataListHiragana.get(key);
        return mColDataListKatakana.get(key);
    }

    public HashMap<String, ArrayList<String[]>> getmColDataList() {
        if(InforChoose.getChooseKana()==0)
            return mColDataListHiragana;
        return mColDataListKatakana;
    }

    public int getIndexOfCharEnList(Object object) {
        if(InforChoose.getChooseKana()==0)
            return mCharEnListHiragana.indexOf(object);
        return mCharEnListKatakana.indexOf(object);
    }

    public ArrayList<String> getmCharEnList() {
        if(InforChoose.getChooseKana()==0)
            return mCharEnListHiragana;
        return mCharEnListKatakana;
    }

    public String getColKeyList(int key)
    {
        if(InforChoose.getChooseKana()==0)
            return mColKeyListHiragana.get(key);
        return  mColKeyListKatakana.get(key);
    }

    public ArrayList<String> getmColKeyList()
    {
        if(InforChoose.getChooseKana()==0)
            return mColKeyListHiragana;
        return  mColKeyListHiragana;
    }
}
