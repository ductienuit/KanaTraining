package com.nhombabon.kanatraining.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhombabon.kanatraining.ProfileActivity;
import com.nhombabon.kanatraining.models.QuizObject;

import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizViewHolder>{

    private Context context;
    private List<QuizObject> quizList;
    private int layoutResource;

    public QuizAdapter(Context context, List<QuizObject> quizList, int layout) {
        this.context = context;
        this.quizList = quizList;
        this.layoutResource = layout;
    }

    @Override
    public QuizViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResource, parent, false);
        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuizViewHolder holder, int position) {
        QuizObject quizObject = quizList.get(position);
        holder.quizName.setText(quizObject.getQuizName());
        //holder.quizImage.setImageResource(R.drawable.profile);

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
        return quizList.size();
    }
}
