package com.ipl.user.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ipl.user.R;
import com.ipl.user.apiutils.APIInterface;
import com.ipl.user.commonutils.SharedPreferenceManager;
import com.ipl.user.model.BlankResponse;
import com.ipl.user.model.Game;
import com.ipl.user.screens.ChooseGameActivity;
import com.ipl.user.screens.GameDetailPage;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseGameAdapter extends RecyclerView.Adapter<ChooseGameAdapter.MyViewHolder> {

    private Context context;
    List<Game> gameslist;
    SharedPreferenceManager sharedPreferenceManager;
    APIInterface service;

    public ChooseGameAdapter(Context applicationContext, List<Game> games, SharedPreferenceManager sharedPreferenceManager, APIInterface service) {
        this.gameslist = games;
        this.context=applicationContext;
        this.sharedPreferenceManager = sharedPreferenceManager;
        this.service= service;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.choosegamelayout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.gamecategory.setText(gameslist.get(position).getContent());
        holder.gametittle.setText(gameslist.get(position).getTitle());
        holder.gamedescription.setText(gameslist.get(position).getDescription());
        holder.gameimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gamedetailsintent = new Intent(context, GameDetailPage.class);
                gamedetailsintent.putExtra("game_id",gameslist.get(position).getId());
                context.startActivity(gamedetailsintent);
            }
        });
        holder.gamecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog alertDialog = new AlertDialog.Builder(context,  R.style.AlertDialogStyle)
                        //.setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Game Selected")
                        .setMessage("Do You want to play the game ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what would happen when positive button is clicked

                               // joinGame(gameslist.get(position).getId());

                                String gametags = gameslist.get(position).getContent();

                                List<String> stringArray = Arrays.asList(gametags.split("VS", -1));


                                sharedPreferenceManager.setGameId(gameslist.get(position).getId());
                                sharedPreferenceManager.setGameTittle(stringArray.get(0));
                                sharedPreferenceManager.setGamePlayer2(stringArray.get(1));
                                ((ChooseGameActivity)context).onBackPressed();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                sharedPreferenceManager.setGameId("");
                                sharedPreferenceManager.setGameTittle("");
                                sharedPreferenceManager.setGamePlayer2("");
                                ((ChooseGameActivity)context).onBackPressed();
                            }
                        })
                        .show();
            }
        });

//      Glide.with(context).load(""+gameslist.get(position).getCoverPhoto()).centerCrop().into(holder.gameimage);
    }

    private void joinGame(String gameid) {

        Call<BlankResponse> call = service.joingame(gameid, "");
        call.enqueue(new Callback<BlankResponse>() {
            @Override
            public void onResponse(Call<BlankResponse> call, Response<BlankResponse> response) {
                if(response!=null && response.body()!=null) {

                }
            }

            @Override
            public void onFailure(Call<BlankResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return gameslist != null ? gameslist.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView gamecategory, gametittle, gamedescription;
        ImageButton gameimage;
        LinearLayout gamecard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            gameimage = (itemView).findViewById(R.id.gameimage);
            gamecategory= (itemView).findViewById(R.id.gamecategory);
            gametittle= (itemView).findViewById(R.id.gametittle);
            gamedescription= (itemView).findViewById(R.id.gamedescription);
            gamecard = (itemView).findViewById(R.id.gamecard);
        }

    }
}


