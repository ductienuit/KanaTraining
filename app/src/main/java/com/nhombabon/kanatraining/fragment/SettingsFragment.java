package com.nhombabon.kanatraining.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.nhombabon.kanatraining.R;
import com.nhombabon.kanatraining.models.InforChoose;

import java.util.ArrayList;


public class SettingsFragment extends Fragment {

    private Switch setsound;

    public SettingsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        getActivity().setTitle("Settings");

        setsound = (Switch) view.findViewById(R.id.setsound);

        if(InforChoose.getStateSound()==0)
            setsound.setChecked(false);
        else
            setsound.setChecked(true);

        initCustomSpinner(view);

        addEvent();

        return view;
    }

    private void addEvent() {

        setsound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    //Bật âm thanh
                    InforChoose.setStateSound(1);
                }
                else
                {
                    //tắt âm thanh
                    InforChoose.setStateSound(0);
                }
            }
        });

    }

    private void initCustomSpinner(View view) {

        Spinner spinnerCustom= (Spinner) view.findViewById(R.id.spinner);
        // Spinner Drop down elements
        ArrayList<String> languages = new ArrayList<>();
        languages.add("English");
        languages.add("French");
        languages.add("Spanish");
        languages.add("Hindu");
        languages.add("Arabic");
        CustomSpinnerAdapter customSpinnerAdapter=new CustomSpinnerAdapter(getActivity(),languages);
        spinnerCustom.setAdapter(customSpinnerAdapter);
        spinnerCustom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                //Toast.makeText(parent.getContext(),  item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

        private final Context activity;
        private ArrayList<String> asr;

        public CustomSpinnerAdapter(Context context, ArrayList<String> asr) {
            this.asr=asr;
            activity = context;
        }



        public int getCount()
        {
            return asr.size();
        }

        public Object getItem(int i)
        {
            return asr.get(i);
        }

        public long getItemId(int i)
        {
            return (long)i;
        }



        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView txt = new TextView(getActivity());
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(18);
            txt.setGravity(Gravity.CENTER_VERTICAL);
            txt.setText(asr.get(position));
            txt.setTextColor(Color.parseColor("#000000"));
            return  txt;
        }

        public View getView(int i, View view, ViewGroup viewgroup) {
            TextView txt = new TextView(getActivity());
            txt.setGravity(Gravity.CENTER);
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(16);
            txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_down_circle, 0);
            txt.setText(asr.get(i));
            txt.setTextColor(Color.parseColor("#000000"));
            return  txt;
        }

    }

}
