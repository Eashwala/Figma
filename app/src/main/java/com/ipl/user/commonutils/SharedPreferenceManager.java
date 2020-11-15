package com.ipl.user.commonutils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {

    private static final String KEY_USER_TOKEN = "Token";
    private static final String KEY_GAME_ID = "GameId";
    private static final String KEY_USER_ID = "UserId";
    private static final String KEY_GAME_PLAYER1 = "player_1";
    private static final String KEY_GAME_PLAYER2 = "player_2";
    private static SharedPreferenceManager myPrefs;
    private SharedPreferences sharedPreferences;

    private SharedPreferenceManager(Context context){

        sharedPreferences=context.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
    }

    public static SharedPreferenceManager getInstance(Context context){

        if(myPrefs==null)
            myPrefs=new SharedPreferenceManager(context);
        return myPrefs;
    }

    private boolean setIntInPreferences(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    private int getIntFromPreferences(String key) {
        int data = sharedPreferences.getInt(key, -1);
        return data;
    }

    private boolean setDoubleInPreferences(String key, Double value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, Double.doubleToLongBits(value));
        return editor.commit();
    }

    private Double getDoubleFromPreferences(String key) {
        Double data = Double.longBitsToDouble(sharedPreferences.getLong(key, Double.doubleToLongBits(-1.0)));
        return data;
    }

    private boolean setBooleanInPreferences(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    private boolean getBooleanFromPreferences(String key) {
        boolean data = sharedPreferences.getBoolean(key, false);
        return data;
    }

    private boolean deleteDataFromPreferences(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        return editor.commit();
    }

    public void clearAllPreferences(){
        sharedPreferences.edit().clear().commit();
    }

//    public void saveUserModel(UserModel userModel)
//    {
//        if(userModel==null)
//            return;
//
//        setStringInPreferences("user_name", userModel.userName);
//        setStringInPreferences("user_email", userModel.userEmail);
//        setStringInPreferences("user_profile_pic", userModel.profilePic);
//    }
    public void setStringInPreferences(String key,String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getStringFromPreferances(String  Key) {
        return sharedPreferences.getString(Key, null);
    }

    public void setUserLoggedIn(Boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("UserLoggedIn", value);
        editor.apply();
    }

    public boolean getUserLoggedIn() {
        return sharedPreferences.getBoolean("UserLoggedIn", false);
    }

    public void setGameId(String gameid){
        sharedPreferences.edit().putString(KEY_GAME_ID,gameid).apply();
    }

    public String getGameId(){
        return sharedPreferences.getString(KEY_GAME_ID,"");
    }

    public void setUserId(String userId){
        sharedPreferences.edit().putString(KEY_USER_ID,userId).apply();
    }

    public String getUserId(){
        return sharedPreferences.getString(KEY_USER_ID,"");
    }


    public void setToken(String token){
        sharedPreferences.edit().putString(KEY_USER_TOKEN,token).apply();
    }

    public String getToken(){
        return sharedPreferences.getString(KEY_USER_TOKEN,"");
    }

    public void setGameTittle(String title) {
        sharedPreferences.edit().putString(KEY_GAME_PLAYER1,title).apply();
    }
    public String getGameTittle(){
        return sharedPreferences.getString(KEY_GAME_PLAYER1,"");
    }

    public void setGamePlayer2(String player2) {
        sharedPreferences.edit().putString(KEY_GAME_PLAYER2,player2).apply();
    }
    public String getGamePlayer2(){
        return sharedPreferences.getString(KEY_GAME_PLAYER2,"");
    }

//
//    public void setUserData(ProfileResponse user) {
//         Gson gson = new Gson();
//        String json = gson.toJson(user);
//        sharedPreferences.edit().putString(KEY_USER_OBJECT, json).apply();
//    }
//
//    public Object getUserData() {
//        Gson gson = new Gson();
//        String json = sharedPreferences.getString(KEY_USER_OBJECT, "");
//        return gson.fromJson(json.toString(), ProfileResponse.class);
//    }

}