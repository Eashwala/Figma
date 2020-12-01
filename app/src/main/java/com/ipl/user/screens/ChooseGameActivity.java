package com.ipl.user.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ipl.user.R;
import com.ipl.user.adapters.ChooseGameAdapter;
import com.ipl.user.apiutils.APIInterface;
import com.ipl.user.apiutils.ApiClient;
import com.ipl.user.commonutils.SharedPreferenceManager;
import com.ipl.user.model.Game;
import com.ipl.user.model.GamesList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseGameActivity extends AppCompatActivity {
    RecyclerView choosegamerecyc;
  APIInterface service;
  ImageView backicon;
  SharedPreferenceManager sharedPreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_game);

        choosegamerecyc = findViewById(R.id.choosegamerecyc);
        backicon  = findViewById(R.id.backicon);
        service = ApiClient.getApiClientInstance().create(APIInterface.class);
        sharedPreferenceManager = SharedPreferenceManager.getInstance(getApplicationContext());

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        choosegamerecyc.setLayoutManager(llm);

        backicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getGamesList() {
        Call<GamesList> call = service.getGamesList();
        call.enqueue(new Callback<GamesList>() {
            @Override
            public void onResponse(Call<GamesList> call, Response<GamesList> response) {
                if(response!=null && response.body()!=null) {
                    GamesList gamesList = response.body();

                    List<Game> gg = new ArrayList<>();
                    for(int i=0; i<gamesList.getGames().size(); i++){
                        if(!gamesList.getGames().get(i).getState().equalsIgnoreCase("Stopped")){
                            gg.add(gamesList.getGames().get(i));
                        }
                    }
                    sendDataTodapter(gg, sharedPreferenceManager);
                }
            }

            @Override
            public void onFailure(Call<GamesList> call, Throwable t) {

            }
        });
    }

    private void sendDataTodapter(List<Game> games, SharedPreferenceManager sharedPreferenceManager) {
        ChooseGameAdapter gamesAdapter = new ChooseGameAdapter(getApplicationContext(), games, sharedPreferenceManager,service);
        choosegamerecyc.setAdapter(gamesAdapter);
    }
    @Override
    public void onResume() {
        super.onResume();
        getGamesList();
    }

}