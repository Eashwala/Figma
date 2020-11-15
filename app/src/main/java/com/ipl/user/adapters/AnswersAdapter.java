package com.ipl.user.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ipl.user.R;
import com.ipl.user.commonutils.OnItemClick;

import java.util.ArrayList;
import java.util.List;


public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.MyViewHolder> {

    private Context context;
    List<String> answerslist = new ArrayList<>();
//    private OnItemClick mCallback;
         OnItemClick mCallback;
    public AnswersAdapter(Context context, OnItemClick mCallback) {
        this.context=context;
        this.mCallback= mCallback;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.answerslayout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.answer_name.setText(answerslist.get(position).toString());

        holder.answer_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mCallback != null) {
                    mCallback.onClick(answerslist.get(position).toString());
                    }
                }
        });
    }

    @Override
    public int getItemCount() {
        return answerslist != null ? answerslist.size() : 0;
    }

    public void updateData(List<String> newModels) {
        if(newModels!=null)

        this.answerslist = newModels;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView answer_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            answer_name= (itemView).findViewById(R.id.answer_name);

        }

    }
}

