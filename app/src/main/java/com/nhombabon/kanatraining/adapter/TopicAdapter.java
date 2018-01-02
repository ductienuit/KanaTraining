package com.nhombabon.kanatraining.adapter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nhombabon.kanatraining.AppConfig;
import com.nhombabon.kanatraining.LoginActivity;
import com.nhombabon.kanatraining.Quiz.QuizSelectListActivity;
import com.nhombabon.kanatraining.illust.IllustMainActivity;
import com.nhombabon.kanatraining.illust.IllustSelectListActivity;
import com.nhombabon.kanatraining.illust.IllustSelectTypeActivity;
import com.nhombabon.kanatraining.models.Common;
import com.nhombabon.kanatraining.models.QuizObject;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class TopicAdapter extends RecyclerView.Adapter<TopicViewHolder>{

    private Context context;
    private List<QuizObject> quizList;
    private int layoutResource;

    public TopicAdapter(Context context, List<QuizObject> quizList, int layout) {
        this.context = context;
        this.quizList = quizList;
        this.layoutResource = layout;
    }

    @Override
    public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResource, parent, false);
        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TopicViewHolder holder, int position) {
        final QuizObject quizObject = quizList.get(position);
        holder.quizName.setText(quizObject.getQuizName());
        holder.quizImage.setImageResource(quizObject.getImagePath());



        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( quizObject.getQuizName()=="Read the kana")
                {
                    Intent it = new Intent();
                    it.putExtra(AppConfig.SELECTED_QUIZ, 0);
                    it.setClass(context, QuizSelectListActivity.class);
                    context.startActivity(it);
                }
                else if( quizObject.getQuizName()=="Choose the kana")
                {
                    Intent it = new Intent();
                    it.putExtra(AppConfig.SELECTED_QUIZ, 1);
                    it.setClass(context, QuizSelectListActivity.class);
                    context.startActivity(it);
                }
               else  if( quizObject.getQuizName()=="Listen & Choose")
                {
                    Intent it = new Intent();
                    it.putExtra(AppConfig.SELECTED_QUIZ, 2);
                    it.setClass(context, QuizSelectListActivity.class);
                    context.startActivity(it);
                }
                else if( quizObject.getQuizName()=="Similar kana")
                {
                    Intent it = new Intent();
                    it.putExtra(AppConfig.SELECTED_QUIZ, 3);
                    it.setClass(context, QuizSelectListActivity.class);
                    context.startActivity(it);
                }
                else if(quizObject.getQuizName()=="Illustrations")
                {
                    Intent it = new Intent();
                    it.setClass(context, IllustSelectTypeActivity.class);
                    context.startActivity(it);
                }
                else if( quizObject.getQuizName()=="Illustrations one by one")
                {
                    Intent it = new Intent();
                    it.putExtra(AppConfig.SELECTED_ILLUST, 0);
                    //it.putExtra(AppConfig.SELECTED_CHAR, (String) common.mColKeyList.get(0));
                    it.setClass(context, IllustMainActivity.class);
                    context.startActivity(it);
                }
                else if( quizObject.getQuizName()=="Illustrations row by row")
                {
                    Intent it = new Intent();
                    it.setClass(context, IllustSelectListActivity.class);
                    context.startActivity(it);
                }
        }
        });
    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }

}

