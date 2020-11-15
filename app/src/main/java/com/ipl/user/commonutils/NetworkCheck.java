package com.ipl.user.commonutils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkCheck {
    private static NetworkCheck instance = null;
    private static Context mContext;
    public static NetworkCheck getInstance(Context c) {
        mContext = c;
        if (instance == null) {
            instance = new NetworkCheck();
        }
        return instance;
    }
    public boolean isConnectionAvailable() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
    public int getTypeOfConncection()
    {
        //          ConnectivityManager.TYPE_BLUETOOTH
        //          ConnectivityManager.TYPE_DUMMY
        //          ConnectivityManager.TYPE_ETHERNET
        //          ConnectivityManager.TYPE_MOBILE
        //          ConnectivityManager.TYPE_MOBILE_DUN
        //          ConnectivityManager.TYPE_MOBILE_HIPRI
        //          ConnectivityManager.TYPE_MOBILE_MMS
        //          ConnectivityManager.TYPE_MOBILE_SUPL
        //          ConnectivityManager.TYPE_WIFI
        //          ConnectivityManager.TYPE_WIMAX
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork.getType();
    }
}
