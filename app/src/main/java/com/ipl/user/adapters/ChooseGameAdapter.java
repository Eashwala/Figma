

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
        holder.gamecategory.setText(gameslist.get(position).getStartTime());
        holder.gametittle.setText(gameslist.get(position).getTitle());
        holder.gamedescription.setText(gameslist.get(position).getDescription());
        if(gameslist.get(position).getState() !=null && gameslist.get(position).getState().equalsIgnoreCase("Started")){
            holder.gamestatus.setText("LIVE");
        }else{
            holder.gamestatus.setText("UP COMMING");
        }


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
                if(gameslist.get(position).getState().equalsIgnoreCase("Started")){
                AlertDialog alertDialog = new AlertDialog.Builder(context,  R.style.AlertDialogStyle)
                        //.setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Game Selected")
                        .setMessage("Do You want to play the game ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what would happen when positive button is clicked
                                sharedPreferenceManager.setGameId(gameslist.get(position).getId());
                                String gamecontent = gameslist.get(position).getContent();

                                if(gamecontent!=null && !gamecontent.isEmpty() && gamecontent.contains("VS")){
                                    List<String> stringArray = Arrays.asList(gamecontent.split("VS", -1));

                                    if(stringArray.get(0)!=null && !stringArray.get(0).isEmpty()){
                                        sharedPreferenceManager.setGameTittle(stringArray.get(0));
                                    }
                                    if(stringArray.get(1)!=null && !stringArray.get(1).isEmpty()){
                                        sharedPreferenceManager.setGamePlayer2(stringArray.get(1));
                                    }
                                }else{
                                    sharedPreferenceManager.setGameTittle(gamecontent);
                                    sharedPreferenceManager.setGamePlayer2("");
                                }
                                joinGame(gameslist.get(position).getId());
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
//                                sharedPreferenceManager.setGameId("");
//                                sharedPreferenceManager.setGameTittle("");
//                                sharedPreferenceManager.setGamePlayer2("");
                                // ((ChooseGameActivity)context).onBackPressed();
                            }
                        })
                        .show();
                }else{
                    Toast.makeText(context, "You cannot join the upcomming game",Toast.LENGTH_LONG).show();
                }
            }
        });

        Glide.with(context).load(""+gameslist.get(position).getCoverPhoto()).centerCrop().placeholder(R.drawable.dummyimage).into(holder.gameimage);
    }

    private void joinGame(String gameid) {
        Call<BlankResponse> call = service.joingame(gameid, sharedPreferenceManager.getUserId());
        call.enqueue(new Callback<BlankResponse>() {
            @Override
            public void onResponse(Call<BlankResponse> call, Response<BlankResponse> response) {
                if(response!=null && response.body()!=null) {
                    BlankResponse bb = response.body();
                    String gameidddd = bb.getGameId();
                    ((ChooseGameActivity)context).onBackPressed();
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
        TextView gamecategory, gametittle, gamedescription, gamestatus;
        ImageButton gameimage;
        LinearLayout gamecard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            gameimage = (itemView).findViewById(R.id.gameimage);
            gamecategory= (itemView).findViewById(R.id.gamecategory);
            gametittle= (itemView).findViewById(R.id.gametittle);
            gamedescription= (itemView).findViewById(R.id.gamedescription);
            gamecard = (itemView).findViewById(R.id.gamecard);
            gamestatus = (itemView).findViewById(R.id.gamestatus);
        }

    }
}


