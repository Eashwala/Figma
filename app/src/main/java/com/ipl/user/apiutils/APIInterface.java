package com.ipl.user.apiutils;

import com.ipl.user.model.BlankResponse;
import com.ipl.user.model.GameDetail;
import com.ipl.user.model.GamesList;
import com.ipl.user.model.SubmitAnswerToQuestion;
import com.ipl.user.model.UserRanking;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

//    {{httpEndPoint}}/answerquestion?userId=u1&matchId=0ac75bb0-071f-11eb-b2f0-450080cc1788
//&questionId=aa0ffd40-071e-11eb-892c-85144445341f&userAnswer=Team1&correctAnswer=Team1&timeTaken=10
//
//  {{httpEndPoint}}/userranking?userId=u2&limit=1&gameId=0ac75bb0-071f-11eb-b2f0-450080cc1788

    @POST("answerquestion")
    Call<SubmitAnswerToQuestion> answerQuestion(@Query("userId") String userId, @Query("matchId") String matchId, @Query("questionId") String questionId, @Query("userAnswer") String userAnswer, @Query("correctAnswer") String correctAnswer, @Query("timeTaken") String timeTaken);

    @POST("joingame")
    Call<BlankResponse> joingame(@Query("gameId") String gameId, @Query("userId") String userId);

    @GET("games")
    Call<GamesList> getGamesList();

    @GET("getgame")
    Call<GameDetail> getGameById(@Query("id")  String gameid);

    @GET("userranking")
    Call<UserRanking> getUserRanking(@Query("userId")  String userId, @Query("limit")  String limit, @Query("gameId")  String gameId);

    @GET("userranking")
    Call<UserRanking> getProfileRanking(@Query("userId")  String userId, @Query("limit")  String limit, @Query("gameId")  String gameId);

}
