package com.ipl.user.websocketutils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.ipl.user.commonutils.Constants;
import com.ipl.user.model.RealTimeEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public class IplSocketConnection implements ClientWebSocket.MessageListener {
    private ClientWebSocket clientWebSocket;
    private Context context;
    public Gson gson = new Gson();
    String gameid;
    private Handler socketConnectionHandler;


    private Runnable checkConnectionRunnable = new Runnable() {
        public void run() {
            if (!clientWebSocket.getConnection().isOpen()) {
                openConnection(gameid);
            }
            startCheckConnection();
        }
    };

    private void startCheckConnection() {
        socketConnectionHandler.postDelayed(checkConnectionRunnable, 5000);
    }

    private void stopCheckConnection() {
        socketConnectionHandler.removeCallbacks(checkConnectionRunnable);
    }

    public IplSocketConnection(Context context) {
        this.context = context;
        socketConnectionHandler = new Handler();
    }

    public void openConnection(String gameId) {
        this.gameid = gameId;
//        if (!Preferences.getManager().isAuth()) {
//            Log.i("Websocket", "Error: User is not authorize");
//            return;
//        }
        if (clientWebSocket != null)
            clientWebSocket.close();
        try {
            String url = Constants.WEBSOCKET_BASE_URL +"?gameId=" + gameId;
            clientWebSocket = new ClientWebSocket(this, url);
            clientWebSocket.connect();
          //  Log.i("Websocket", "Socket connected by user " + Preferences.getManager().getUserId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initScreenStateListener();
        startCheckConnection();
    }

    public void closeConnection() {
        if (clientWebSocket != null) {
            clientWebSocket.close();
            clientWebSocket = null;
        }
        releaseScreenStateListener();
        stopCheckConnection();
    }


    @Override
    public void onSocketMessage(String message) {
        EventBus.getDefault().post(gson.fromJson(message, RealTimeEvent.class));
    }

    @Subscribe
    public void handleRealTimeMessage(final RealTimeEvent event) {

    }

    /**
     * Screen state listener for socket live cycle
     */
    private void initScreenStateListener() {
        context.registerReceiver(screenStateReceiver, new IntentFilter(Intent.ACTION_SCREEN_ON));
        context.registerReceiver(screenStateReceiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));
    }

    private void releaseScreenStateListener() {
        try {
            context.unregisterReceiver(screenStateReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private BroadcastReceiver screenStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ( intent.getAction() !=null && intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                Log.i("Websocket", "Screen ON");
                openConnection(gameid);
            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                Log.i("Websocket", "Screen OFF");
                closeConnection();
            }
        }
    };

    public boolean isConnected() {
        return clientWebSocket != null &&
                clientWebSocket.getConnection() != null &&
                clientWebSocket.getConnection().isOpen();
    }
}
