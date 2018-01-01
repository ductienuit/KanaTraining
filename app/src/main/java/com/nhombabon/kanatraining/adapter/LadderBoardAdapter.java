package com.nhombabon.kanatraining.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhombabon.kanatraining.R;
import com.nhombabon.kanatraining.models.LadderProfile;

import java.util.ArrayList;

import static com.github.mikephil.charting.charts.Chart.LOG_TAG;


public class LadderBoardAdapter extends RecyclerView.Adapter<LadderBoardAdapter.DataObjectHolder> {

    private static String TAG = LadderBoardAdapter.class.getSimpleName();
    private ArrayList<LadderProfile> mDataset;


    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView score;
        ImageView image;

        public DataObjectHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.profileName);
            score = (TextView) itemView.findViewById(R.id.profileScore);
            image = (ImageView) itemView.findViewById(R.id.profileImage);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }
    }

    public LadderBoardAdapter(ArrayList<LadderProfile> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ladder_board_list_item, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }


    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.name.setText(mDataset.get(position).getUsername());
        holder.score.setText(String.valueOf(mDataset.get(position).getScore()) + " points");
        holder.image.setImageResource(mDataset.get(position).getImage());
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
