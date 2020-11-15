package com.ipl.user.websocketutils;


import androidx.appcompat.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;


public abstract class SocketConnectionActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


//    @Subscribe
//    public void handleRealTimeMessage(RealTimeEvent event) {
//        // processing of all real-time events
//    }
}
