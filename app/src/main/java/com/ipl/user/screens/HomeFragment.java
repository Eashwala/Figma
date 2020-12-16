package com.ipl.user.screens;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ipl.user.R;
import com.ipl.user.adapters.AnswersAdapter;
import com.ipl.user.apiutils.APIInterface;
import com.ipl.user.apiutils.ApiClient;
import com.ipl.user.commonutils.MyCognito;
import com.ipl.user.commonutils.OnItemClick;
import com.ipl.user.commonutils.SharedPreferenceManager;
import com.ipl.user.model.Ranking;
import com.ipl.user.model.RealTimeEvent;
import com.ipl.user.model.SubmitAnswerToQuestion;
import com.ipl.user.model.UserRanking;
import com.ipl.user.websocketutils.IplSocketApplication;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
public class HomeFragment extends Fragment implements OnItemClick {
    LinearLayout guesstheoutcomelayout, analysingresults, preparingresults, gameended;
    ImageView bookmark, notifications, location_icon, rightarrow;
    TextView  timeleftquestions, pointsearned, player1_score, player2, points_score, rank_score,live_score ;
    ProgressBar progressbartimeleft, progressbaranalysingresults, progressBarpreparingresults;
    RecyclerView answersrecyc;
    TextView question_name, selectgame;
    RelativeLayout choosegamelayout;
    LinearLayout toplayout, selectgamelayout, progresslayout;
    String  answer; int progressbartime;
    List<String> answerslist = new ArrayList<>();
    List<String> noanswerslist = new ArrayList<>();
    AnswersAdapter answersAdapter;
    SharedPreferenceManager sharedPreferenceManager;
    MyCountDownTimer myCountDownTimer;
    String timeout;
    int second, pointfromapi;
    String predictivetimetaken;

    APIInterface service;
    private static final String TAG = "home";
    IplSocketApplication iplSocketApplication;
    RealTimeEvent realTimeEvent = new RealTimeEvent();
    MyCognito cognito;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);


        return view;
    }


    private void init(View view) {

        cognito = new MyCognito(getActivity());

        choosegamelayout = view.findViewById(R.id.choosegamelayout);

        selectgamelayout = view.findViewById(R.id.selectgamelayout);
        toplayout = view.findViewById(R.id.toplayout);
        selectgame = view.findViewById(R.id.selectgame);

        answersrecyc = view.findViewById(R.id.answersrecyc);
        progressbartimeleft = view.findViewById(R.id.homeprogressbartimeleft);
        progressbaranalysingresults = view.findViewById(R.id.progressbaranalysingresults);
        progressBarpreparingresults = view.findViewById(R.id.progressBarpreparingresults);

        guesstheoutcomelayout = view.findViewById(R.id.guesstheoutcomelayout);
        analysingresults = view.findViewById(R.id.analysingresults);
        preparingresults = view.findViewById(R.id.preparingresults);
        gameended = view.findViewById(R.id.gameended);

        progresslayout = view.findViewById(R.id.progresslayout);

        bookmark = view.findViewById(R.id.bookmark);
        notifications = view.findViewById(R.id.notifications);
        location_icon = view.findViewById(R.id.location_icon);
        rightarrow = view.findViewById(R.id.rightarrow);

        timeleftquestions = view.findViewById(R.id.timeleftquestions);
        question_name = view.findViewById(R.id.question_name);
        pointsearned = view.findViewById(R.id.pointsearned);
        player1_score = view.findViewById(R.id.player1_score);
        player2 = view.findViewById(R.id.player2);

        points_score = view.findViewById(R.id.points_score);
        rank_score = view.findViewById(R.id.rank_score);
        live_score = view.findViewById(R.id.live_score);
        service = ApiClient.getApiClientInstance().create(APIInterface.class);
        sharedPreferenceManager = SharedPreferenceManager.getInstance(getActivity());
        if(getActivity()!=null)
            iplSocketApplication = (IplSocketApplication)getActivity().getApplicationContext();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        answersrecyc.setLayoutManager(gridLayoutManager);
        answersAdapter = new AnswersAdapter(getActivity(), this);
        answersrecyc.setAdapter(answersAdapter);
        onclicks();
    }

    private void startCountDownTimer() {

        if(myCountDownTimer!=null){
            myCountDownTimer.cancel();
        }

        if(timeout!=null && !timeout.isEmpty()){
            int timeinsec = Integer.parseInt(timeout);
            myCountDownTimer = new MyCountDownTimer(timeinsec*1000, 1000);
            myCountDownTimer.start();
        }else{

        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if(sharedPreferenceManager.getGameId()!=null && !sharedPreferenceManager.getGameId().isEmpty()){
            String newgameid = sharedPreferenceManager.getGameId();
            if(getActivity()!=null && newgameid!=null){
                if(getActivity() != null && !iplSocketApplication.isSocketConnected()){
                    iplSocketApplication.openSocketConnection(newgameid);
                    selectgamelayout.setVisibility(View.GONE);
                    toplayout.setVisibility(View.VISIBLE);
                    gameended.setVisibility(View.GONE);
                    rank_score.setText("0");
                    points_score.setText("0");
                    player1_score.setText(sharedPreferenceManager.getGameTittle());
                    player2.setText(sharedPreferenceManager.getGamePlayer2());
                    Log.e(TAG, "onResume: socketopeneddd" );
//                    getUserRanking();
                }
            }

        }else {
            selectgamelayout.setVisibility(View.VISIBLE);
            toplayout.setVisibility(View.GONE);
            player1_score.setText("Select a Game");
            player2.setText("");
        }
    }

    private void getUserRanking() {

        Log.e(TAG, "getUserRanking: "+sharedPreferenceManager.getUserId() );
        Log.e(TAG, "getUserRanking: "+sharedPreferenceManager.getGameId() );

        Call<UserRanking> call = service.getUserRanking(sharedPreferenceManager.getUserId(),"100",sharedPreferenceManager.getGameId());
        call.enqueue(new Callback<UserRanking>() {
            @Override
            public void onResponse(Call<UserRanking> call, retrofit2.Response<UserRanking> response) {


                if (response!=null && response.isSuccessful()){
                    UserRanking userRanking = response.body();
                  //  int points = 0;
                    int livemembers=0, points=0;
                    String rank = userRanking.getUserRank();
                    List<Ranking> rankingList = new ArrayList<>();

                    if (userRanking.getRankings()!= null && userRanking.getRankings().size()>0 ){
                        livemembers =userRanking.getRankings().size();
                        for(int i=0;i<userRanking.getRankings().size();i++){
                            rankingList = userRanking.getRankings();

                            if(rankingList.get(i).getId().equalsIgnoreCase(sharedPreferenceManager.getUserId())){

                               Ranking rr = rankingList.get(i);
                                points = rr.getPoints();
  Log.e(TAG, "onResponse: userranking"+points );
                            }
                        }
                    }
                    rank_score.setText(rank);
                    points_score.setText(String.valueOf(points));
                    live_score.setText(String.valueOf(livemembers));


                }else{

                }


            }

            @Override
            public void onFailure(Call<UserRanking> call, Throwable t) {

            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        if(iplSocketApplication.isSocketConnected())
            iplSocketApplication.closeSocketConnection();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(sharedPreferenceManager.getGameId()!=null && !sharedPreferenceManager.getGameId().isEmpty() && sharedPreferenceManager.getUserId()!=null && !sharedPreferenceManager.getUserId().isEmpty()){
          getUserRanking();
        }

        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
        preparingresults.setVisibility(View.VISIBLE);
        analysingresults.setVisibility(View.GONE);
        gameended.setVisibility(View.GONE);
    }

    @Subscribe
    public void handleRealTimeMessage(final RealTimeEvent event) {
        assert  getActivity()!=null;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                realTimeEvent = event;
                String  msgType = event.getMsgType();
                Log.e(TAG, "run: "+ msgType );
                if(msgType.equalsIgnoreCase("createQuestion")){
                    guesstheoutcomelayout.setVisibility(View.VISIBLE);
                    preparingresults.setVisibility(View.GONE);
                    analysingresults.setVisibility(View.GONE);
                    gameended.setVisibility(View.GONE);

                    String  ques = event.getMsgContent().getQuestion();
                    timeout = event.getMsgContent().getTimeOut();
                    question_name.setText(ques);
                    Log.e(TAG, "run:ques  "+ ques );
                    List<String>  optionslist = new ArrayList<>();
                    optionslist= event.getMsgContent().getOptions();

                    answerslist.clear();
                    for (int i=0; i<optionslist.size(); i++) {
                        answerslist.add(optionslist.get(i));
                    }
                    if(answerslist!=null && answerslist.size()>0){
                        Log.e(TAG, "onCreateView: user options list answersssssssss" +answerslist );
                        answersAdapter.updateData(answerslist);
                        answersAdapter.notifyDataSetChanged();
                    }else {
                        Log.e(TAG, "onCreateView: null answersssssssss" + answerslist);
                    }

                    progresslayout.setVisibility(View.VISIBLE);
                    startCountDownTimer();

                }else if(msgType.equalsIgnoreCase("closeQuestion")){

                    predicitveQuestionclosed(event);

                }else if(msgType.equalsIgnoreCase("updateGameData")){

                    String gametags = event.getMsgContent().getContent();

                    List<String> stringArray = Arrays.asList(gametags.split("VS", -1));

                    player1_score.setText(stringArray.get(0));
                    player2.setText(stringArray.get(1));

                    Log.e(TAG, "run: anwered quessssssssss success" );
                }else if(msgType.equalsIgnoreCase("stopGame")){
                    Log.e(TAG, "run: stop gameeeeeeeee   ");
                    pointfromapi=0;
                    if(sharedPreferenceManager.getGameId()!=null && !sharedPreferenceManager.getGameId().isEmpty() && sharedPreferenceManager.getUserId()!=null && !sharedPreferenceManager.getUserId().isEmpty()){
                        getUserRanking();
                    }
                    guesstheoutcomelayout.setVisibility(View.GONE);
                    analysingresults.setVisibility(View.GONE);
                    preparingresults.setVisibility(View.GONE);
                    gameended.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void predicitveQuestionclosed(RealTimeEvent event) {

        question_name.setText("Sorry, question closed!");
        String answerfromserver =event.getMsgContent().getAnswer();
        String queidd =event.getMsgContent().getQuestionId();
        answersAdapter.updateData(noanswerslist);
        Log.e(TAG, "run:ques close id "+ queidd );
        if(answer!=null){
            if(answerfromserver.equalsIgnoreCase(answer)){
                pointsearned.setText("Yay.. its a correct answer");
            }else {
                pointsearned.setText("Wrong answer");
            }
            submitAnswerrtoQuestion(queidd, answerfromserver, predictivetimetaken);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    guesstheoutcomelayout.setVisibility(View.GONE);
                    analysingresults.setVisibility(View.GONE);
                    preparingresults.setVisibility(View.VISIBLE);
                    gameended.setVisibility(View.GONE);
                }
            }, 3000);

        }else{
            guesstheoutcomelayout.setVisibility(View.GONE);
            analysingresults.setVisibility(View.GONE);
            preparingresults.setVisibility(View.VISIBLE);
            gameended.setVisibility(View.GONE);
        }

    }

    private void onclicks() {

        selectgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(getActivity(), ChooseGameActivity.class);
                startActivity(ii);
            }
        });

        rightarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sharedPreferenceManager.getGameId()!=null && !sharedPreferenceManager.getGameId().isEmpty()) {

                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity(),  R.style.AlertDialogStyle)
                            //.setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Stop Game")
                            .setMessage("Do You want to quit from the game ?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    sharedPreferenceManager.setGameId("");
                                    Intent ii = new Intent(getActivity(), ChooseGameActivity.class);
                                    startActivity(ii);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .show();
                }else{
                    Intent ii = new Intent(getActivity(), ChooseGameActivity.class);
                    startActivity(ii);
                }
            }
        });

        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(String value) {
        answer = value;
        Log.e(TAG, "onclick"+ answer );
        String quesid = realTimeEvent.getMsgContent().getId();
        final String correctanswer = realTimeEvent.getMsgContent().getAnswer();

        if(realTimeEvent.getMsgContent().getQType().equalsIgnoreCase("standard")){

            progressbartimeleft.setProgress(0);
            myCountDownTimer.cancel();
            int timeinsec1 = Integer.parseInt(timeout);
            int time = timeinsec1- second;
            String timetaken = String.valueOf(time);
            guesstheoutcomelayout.setVisibility(View.GONE);
            analysingresults.setVisibility(View.VISIBLE);
            preparingresults.setVisibility(View.GONE);
            gameended.setVisibility(View.GONE);
            if(answer!=null) {
                if (answer.equalsIgnoreCase(correctanswer)) {
                    pointsearned.setText("correct answerrr");
                } else {
                    pointsearned.setText("wrong  answerrrrr");
                }

            }
            Log.e(TAG, "onClick: useridddddddd "+sharedPreferenceManager.getUserId() );

            submitAnswerrtoQuestion(quesid, correctanswer, timetaken);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    guesstheoutcomelayout.setVisibility(View.GONE);
                    analysingresults.setVisibility(View.GONE);
                    preparingresults.setVisibility(View.VISIBLE);
                    gameended.setVisibility(View.GONE);
                }
            }, 3000);

        }else{
            progresslayout.setVisibility(View.GONE);
            int timeinsec1 = Integer.parseInt(timeout);
            int time = timeinsec1- second;
            predictivetimetaken = String.valueOf(time);

            myCountDownTimer.cancel();
            guesstheoutcomelayout.setVisibility(View.GONE);
            analysingresults.setVisibility(View.VISIBLE);
            preparingresults.setVisibility(View.GONE);
            gameended.setVisibility(View.GONE);
            pointsearned.setText("");

        }
    }

    private void submitAnswerrtoQuestion(String quesid, String correctanswer, String timetaken) {
        Call<SubmitAnswerToQuestion> call = service.answerQuestion(sharedPreferenceManager.getUserId(),sharedPreferenceManager.getGameId(),quesid,answer, correctanswer,timetaken);
        call.enqueue(new Callback<SubmitAnswerToQuestion>() {
            @Override
            public void onResponse(Call<SubmitAnswerToQuestion> call, retrofit2.Response<SubmitAnswerToQuestion> response) {
                if(response!=null && response.body()!=null ){
                    SubmitAnswerToQuestion submitAnswerToQuestion = response.body();
                    pointfromapi = pointfromapi+ submitAnswerToQuestion.getPoint();
                }
                Log.e(TAG, "onResponse: ppp"+pointfromapi );
                points_score.setText(String.valueOf(pointfromapi));
            }
            @Override
            public void onFailure(Call<SubmitAnswerToQuestion> call, Throwable t) {
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(iplSocketApplication!=null)
            iplSocketApplication.closeSocketConnection();
        Log.e(TAG, "onDestroy: cosed connection " );
    }


    public class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            second = (int) ((millisUntilFinished / 1000) % 60);
            // long minutes = (millisUntilFinished/(1000*60)) % 60;

            progressbartimeleft.setMax(Integer.parseInt(timeout));
            timeleftquestions.setText(second + "/"+ timeout);

            int progresstimeleft = (int) (millisUntilFinished/1000);
            progressbartimeleft.setProgress(progresstimeleft);

        }

        @Override
        public void onFinish() {
            progressbartimeleft.setProgress(0);
            guesstheoutcomelayout.setVisibility(View.GONE);
            preparingresults.setVisibility(View.VISIBLE);
            analysingresults.setVisibility(View.GONE);
            gameended.setVisibility(View.GONE);
        }

    }


}