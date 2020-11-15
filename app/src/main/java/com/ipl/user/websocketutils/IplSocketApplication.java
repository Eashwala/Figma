package com.ipl.user.websocketutils;

import android.app.Application;
import android.util.Log;


public class IplSocketApplication extends Application {

    private IplSocketConnection iplSocketConnection;
    String gameId;

    @Override
    public void onCreate() {
        super.onCreate();
        iplSocketConnection = new IplSocketConnection(this);
        BackgroundManager.get(this).registerListener(appActivityListener);
    }


    public void closeSocketConnection() {
        iplSocketConnection.closeConnection();
    }

    public void openSocketConnection(String gameId) {
        this.gameId = gameId;
        iplSocketConnection.openConnection(gameId);
    }

    public boolean isSocketConnected() {
        return iplSocketConnection.isConnected();
    }

    public void reconnect() {
        iplSocketConnection.openConnection(gameId);
    }


    private BackgroundManager.Listener appActivityListener = new BackgroundManager.Listener() {
        public void onBecameForeground() {
            openSocketConnection(gameId);
            Log.i("Websocket", "Became Foreground");
        }

        public void onBecameBackground() {
            closeSocketConnection();
            Log.i("Websocket", "Became Background");
        }
    };
}
