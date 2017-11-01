package com.nhombabon.kanatraining.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhombabon.kanatraining.ProfileActivity;
import com.nhombabon.kanatraining.R;
import com.nhombabon.kanatraining.models.TopicObject;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private Context context;
    private List<TopicObject> topicList;

    public CategoryAdapter(Context context, List<TopicObject> topicList) {
        this.context = context;
        this.topicList = topicList;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_category_layout, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        TopicObject topicObject = topicList.get(position);
        //holder.topicImage
        holder.topicName.setText(topicObject.getTopicName());
        holder.topicDescription.setText(topicObject.getTopicDescription());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(context, ProfileActivity.class);
                context.startActivity(profileIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }
}
