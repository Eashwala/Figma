package com.ipl.user.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ipl.user.R;
import com.ipl.user.model.Game;

import java.util.List;



public class ProfileGamesAdapter extends RecyclerView.Adapter<ProfileGamesAdapter.MyViewHolder> {

    private Context context;
    List<Game> profilegameslist;

    public ProfileGamesAdapter(Context context, List<Game> profilegameslist) {
        this.profilegameslist = profilegameslist;
        this.context=context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.profilegamelayout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.prof_achivement.setText(profilegameslist.get(position).getTitle());

        Glide.with(context).load(""+profilegameslist.get(position).getCoverPhoto()).centerCrop().placeholder(R.drawable.dummyimage).into(holder.prof_game_image);

    }

    @Override
    public int getItemCount() {
        return profilegameslist != null ? profilegameslist.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView prof_achivement, prof_progress_status;
        ProgressBar prof_progressbar;
        ImageView prof_game_image, prof_right_icon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            prof_game_image = (itemView).findViewById(R.id.prof_game_image);
            prof_right_icon = (itemView).findViewById(R.id.prof_right_icon);
            prof_achivement= (itemView).findViewById(R.id.prof_achivement);
            prof_progress_status= (itemView).findViewById(R.id.prof_progress_status);
            prof_progressbar= (itemView).findViewById(R.id.prof_progressbar);
        }

    }
}



