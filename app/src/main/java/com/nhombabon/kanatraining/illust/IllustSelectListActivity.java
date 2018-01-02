package com.nhombabon.kanatraining.illust;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nhombabon.kanatraining.AppBaseActivity;
import com.nhombabon.kanatraining.AppConfig;
import com.nhombabon.kanatraining.R;
import com.nhombabon.kanatraining.models.Common;
import com.nhombabon.kanatraining.models.InforChoose;

import java.util.ArrayList;

public class IllustSelectListActivity extends AppBaseActivity {

    private LinearLayout mMainView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_illust_select_list);

        findViewById(R.id.quiz_top_button).setVisibility(View.GONE);

        ((TextView) findViewById(R.id.change_title)).setText(getResources().getText(R.string.SelectLine_ViewName).toString());
        this.mMainView = (LinearLayout) findViewById(R.id.illust_select_main_view);
        fontChange(AppConfig.FONT_NAME_LETTER, (TextView) findViewById(R.id.change_title));
        makeTable();
    }

    private void makeTable() {
        this.mMainView.removeAllViews();
        Common common = (Common) getApplication();
        ArrayList<String> mKeyList = common.getmColKeyList();
        for (int i = 0; i < mKeyList.size() - 1; i++) {
            String line = "";
            String imgname = "";
            View lineLayout = getLayoutInflater().inflate(R.layout.select_line, null);
            this.mMainView.addView(lineLayout);
            View clickCell = lineLayout.findViewById(R.id.select_cell);
            ImageView imageView = (ImageView) lineLayout.findViewById(R.id.select_line_image);
            TextView textView = (TextView) lineLayout.findViewById(R.id.list_type_select);
            fontChange(AppConfig.FONT_NAME_LETTER, textView);
            clickCell.setTag(Integer.toString(i));
            String[] fdata;
            String[] ldata;
            if (i < mKeyList.size() - 2) {
                ArrayList<String[]> colList = (ArrayList) common.getColDataList((String) mKeyList.get(i));
                fdata = (String[]) colList.get(0);
                ldata = (String[]) colList.get(colList.size() - 1);
                line = String.format("%s - %s", new Object[]{fdata[3], ldata[3]});

                if(InforChoose.getChooseKana()==0)
                    imgname = String.format("hiragana/ch_img/%s_1.png", new Object[]{fdata[2]});
                else
                    imgname = String.format("katakana/ch_img/%s_1.png", new Object[]{fdata[2]});



            } else {
                ArrayList<String[]> colList2 = (ArrayList) common.getColDataList((String) mKeyList.get(i + 1));
                fdata = (String[]) ((ArrayList)  common.getColDataList((String) mKeyList.get(i))).get(0);
                ldata = (String[]) colList2.get(colList2.size() - 1);
                line = String.format("%s - %s", new Object[]{fdata[3], ldata[3]});


                if(InforChoose.getChooseKana()==0)
                    imgname = String.format("hiragana/ch_img/%s_1.png", new Object[]{fdata[2]});
                else
                    imgname = String.format("katakana/ch_img/%s_1.png", new Object[]{fdata[2]});
            }
            textView.setText(line);
            loadAssetImage(imgname, imageView);
        }
    }

    public void clickListType(View v) {
        String key = (String) ((Common) getApplication()).getColKeyList(Integer.parseInt((String) v.getTag()));
        Intent it = new Intent();
        it.putExtra(AppConfig.SELECTED_ILLUST, 1);
        it.putExtra(AppConfig.SELECTED_CHAR, key);
        it.setClass(this, IllustMainActivity.class);
        startActivity(it);
    }

    public void clickNaviBack(View view) {
        finish();
    }
}

