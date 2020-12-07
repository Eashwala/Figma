package com.ipl.user.screens;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ipl.user.R;
import com.ipl.user.adapters.ProfileGamesAdapter;
import com.ipl.user.apiutils.APIInterface;
import com.ipl.user.apiutils.ApiClient;
import com.ipl.user.commonutils.MyCognito;
import com.ipl.user.commonutils.SharedPreferenceManager;
import com.ipl.user.model.Game;
import com.ipl.user.model.GamesList;
import com.ipl.user.model.Ranking;
import com.ipl.user.model.UserRanking;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    TextView prof_points_score, prof_games_score, prof_rank_score;
    ImageView prof_settings, logout;
    RecyclerView profile_games_recyc;
    APIInterface service;
    SharedPreferenceManager sharedPreferenceManager;
    MyCognito cognito;
    int pointsscoredbuser=0, gamesplayedbyuser=0;
    public ProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        prof_points_score = view.findViewById(R.id.prof_pointsscore);
        prof_games_score = view.findViewById(R.id.prof_gamescore);
        prof_rank_score = view.findViewById(R.id.prof_rank_score);
        service = ApiClient.getApiClientInstance().create(APIInterface.class);
        sharedPreferenceManager = SharedPreferenceManager.getInstance(getActivity());
        cognito = new MyCognito(getActivity());

        prof_settings = view.findViewById(R.id.prof_settings);
        logout = view.findViewById(R.id.logout);
        profile_games_recyc = view.findViewById(R.id.profile_games_recyc);
        onclicks();
        return view;

    }

    private void listGamesForUser() {
        Call<GamesList> call = service.listgamesforuser(sharedPreferenceManager.getUserId());
        call.enqueue(new Callback<GamesList>() {
            @Override
            public void onResponse(Call<GamesList> call, Response<GamesList> response) {
                if(response!=null && response.body()!=null) {
                    GamesList gamesList = response.body();
                    for(int i =0; i<gamesList.getGames().size(); i++){

                        if(gamesList.getGames().get(i) !=null && gamesList.getGames().get(i).getPoints()!=null)
                            Log.e("TAG", "listGamesForUser: "+gamesList.getGames().get(i).getPoints() );
                           pointsscoredbuser = pointsscoredbuser+ gamesList.getGames().get(i).getPoints();
                    }
                    gamesplayedbyuser = gamesList.getGames().size();
                    sendDataTodapter(gamesList.getGames(), sharedPreferenceManager);
                }
                prof_points_score.setText(String.valueOf(pointsscoredbuser));
                prof_games_score.setText(String.valueOf(gamesplayedbyuser));
            }

            @Override
            public void onFailure(Call<GamesList> call, Throwable t) {

            }
        });

    }

    private void sendDataTodapter(List<Game> games, SharedPreferenceManager sharedPreferenceManager) {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        profile_games_recyc.setLayoutManager(llm);
        ProfileGamesAdapter profileGamesAdapter = new ProfileGamesAdapter(getActivity(), games);
        profile_games_recyc.setAdapter(profileGamesAdapter);
    }

    private void onclicks() {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity(),  R.style.AlertDialogStyle)
                        //.setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Sigout User")
                        .setMessage("Logout from this device ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                cognito.userLogout();
                                sharedPreferenceManager.clearAllPreferences();
                                Intent intent = new Intent(getActivity(), SignUp.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    private void getUserProfile() {
        Call<UserRanking> call = service.getProfileRanking(sharedPreferenceManager.getUserId(),"100",sharedPreferenceManager.getGameId() );
        call.enqueue(new Callback<UserRanking>() {
            @Override
            public void onResponse(Call<UserRanking> call, retrofit2.Response<UserRanking> response) {

                if(response!=null &&  response.body()!=null){
                    UserRanking userRanking = response.body();
                    String rank = userRanking.getUserRank();
                    if(rank!=null && !rank.isEmpty()){
                        prof_rank_score.setText(rank);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserRanking> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        pointsscoredbuser=0;
        gamesplayedbyuser=0;
        listGamesForUser();
        if(sharedPreferenceManager.getGameId()!=null && !sharedPreferenceManager.getGameId().isEmpty()){
            getUserProfile();

        }
    }
}