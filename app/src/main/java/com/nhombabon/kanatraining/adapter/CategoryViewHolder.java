package com.nhombabon.kanatraining.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhombabon.kanatraining.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder{

    public ImageView topicImage;
    public TextView topicName;
    public TextView topicDescription;
    public View view;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        topicImage = (ImageView)itemView.findViewById(R.id.topic_image);
        topicName = (TextView)itemView.findViewById(R.id.topic_name);
        topicDescription = (TextView)itemView.findViewById(R.id.topic_description);
        view = itemView;
    }
}
