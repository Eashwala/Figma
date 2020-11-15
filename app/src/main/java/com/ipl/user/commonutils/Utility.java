package com.ipl.user.commonutils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.ipl.user.R;

public class Utility {
    public static void printLog(String logstring) {
        Log.e("Utility", "printLog: " + logstring);
    }

    public static void showToast(Context applicationContext, String string) {
        Toast.makeText(applicationContext, string, Toast.LENGTH_LONG).show();
    }

    public static boolean isNetworkAvailable(Context applicationContext) {
        ConnectivityManager cm = (ConnectivityManager) applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static void retryInternet(final Context context) {

        AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.AlertDialogStyle)
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Internet!!")
                .setMessage("Do You want to play the game ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if( isNetworkAvailable(context)){
                            dialogInterface.dismiss();
                        }else{
                            retryInternet(context);
                        }


                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                })
                .show();
    }
}
