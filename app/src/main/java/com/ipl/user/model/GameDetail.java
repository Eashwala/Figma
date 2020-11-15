package com.ipl.user.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GameDetail {

    @SerializedName("game")
    @Expose
    private Game game;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
