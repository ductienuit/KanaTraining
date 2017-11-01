package com.nhombabon.kanatraining.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nhombabon.kanatraining.R;

import java.util.ArrayList;


public class LevelsListAdapter extends RecyclerView.Adapter<LevelsListAdapter.DataObjectHolder> {

    private static String LOG_TAG = LevelsListAdapter.class.getCanonicalName();
    private ArrayList<String> mDataset;
    private static MyClickListener myClickListener;
    private Context context;


    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView level;
        RatingBar ratingBar;

        public DataObjectHolder(View itemView) {
            super(itemView);
            level = (TextView) itemView.findViewById(R.id.level);
            ratingBar = (RatingBar) itemView.findViewById(R.id.rating);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public LevelsListAdapter(ArrayList<String> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.level_list_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }


    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        if(position < 5) {
            holder.level.setText(mDataset.get(position));
            holder.ratingBar.setRating(position + 1);
            //holder.level.setBackgroundDrawable(context.getDrawable(R.drawable.round_corner));
        }else{
            holder.level.setText("");
            //holder.level.setBackgroundDrawable(context.getDrawable(R.drawable.padlock));
            holder.ratingBar.setRating(0);
        }
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

}
