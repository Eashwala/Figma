package com.ipl.user.screens;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ipl.user.R;
import com.ipl.user.apiutils.APIInterface;
import com.ipl.user.apiutils.ApiClient;
import com.ipl.user.model.GameDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameDetailPage extends AppCompatActivity {
    TextView gamedetailtitle, gamedetaildetails, gamedetailcontenturl,gamedetailstartdate, gamedetailenddate, gamedetailtags,gamedetailtype;
    String gameid;
APIInterface service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_detail_page);
        service = ApiClient.getApiClientInstance().create(APIInterface.class);

        Intent i = getIntent();
        if(i!=null){

            gameid = i.getStringExtra("game_id");

        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.gamedetail_toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.chevronleft, null));
        }

        gamedetailtitle =findViewById(R.id.gamedetailtitle);
        gamedetaildetails =findViewById(R.id.gamedetaildetails);
        gamedetailcontenturl=findViewById(R.id. gamedetailcontenturl);
        gamedetailstartdate =findViewById(R.id.gamedetailstartdate);
        gamedetailenddate =findViewById(R.id.gamedetailenddate);
        gamedetailtags  =findViewById(R.id.gamedetailtags);
        gamedetailtype  =findViewById(R.id.gamedetailtype);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getGameDetail(gameid);

    }

    private void getGameDetail(String gameid) {
        Call<GameDetail> call = service.getGameById(gameid);
        call.enqueue(new Callback<GameDetail>() {
            @Override
            public void onResponse(Call<GameDetail> call, Response<GameDetail> response) {

                if(response!=null && response.body()!=null) {
                    GameDetail gameDetail = response.body();

                    gamedetailtitle.setText(gameDetail.getGame().getTitle());
                    gamedetailstartdate.setText(gameDetail.getGame().getStartTime());
                    gamedetailenddate.setText(gameDetail.getGame().getEndTime());
                    gamedetaildetails.setText(gameDetail.getGame().getDescription());
                    gamedetailcontenturl.setText(gameDetail.getGame().getContent());
                    gamedetailtype.setText(gameDetail.getGame().getType());


                    String strNames = null;
                    if(gameDetail.getGame().getTags()!=null)
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            strNames = String.join(", ", gameDetail.getGame().getTags());
                        }
                    gamedetailtags.setText(strNames);

                }
            }

            @Override
            public void onFailure(Call<GameDetail> call, Throwable t) {

            }
        });
    }
}