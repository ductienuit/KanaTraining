package com.nhombabon.kanatraining.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhombabon.kanatraining.R;

public class TopicViewHolder extends RecyclerView.ViewHolder{

    public ImageView quizImage;
    public TextView quizName;
    public View view;

    public TopicViewHolder(View itemView) {
        super(itemView);
        quizImage = (ImageView)itemView.findViewById(R.id.selected_quiz_image);
        quizName = (TextView)itemView.findViewById(R.id.selected_quiz_name);
        view = itemView;
    }
}
