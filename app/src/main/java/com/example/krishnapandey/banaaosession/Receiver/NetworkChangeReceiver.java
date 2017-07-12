package com.example.krishnapandey.banaaosession.Receiver;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.example.krishnapandey.banaaosession.Activities.LoginActivity;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private ProgressDialog progressDialog;
@Override
public void onReceive(final Context context, final Intent intent) {
    progressDialog = new ProgressDialog(context);
    progressDialog.setMessage("Check your internet connection"+"\n"+"or close the app");

    if (checkInternet(context)) {
        progressDialog.hide();
        //Toast.makeText(context, "Network Available Do operations", Toast.LENGTH_LONG).show();
    } else {
        progressDialog.show();
        //progressDialog.setCanceledOnTouchOutside(false);
        Toast.makeText(context, "Check your internet connection"+"\n"+"or close the app", Toast.LENGTH_LONG).show();
    }

}

    boolean checkInternet(Context context) {
        ServiceManager serviceManager = new ServiceManager(context);
        if (serviceManager.isNetworkAvailable()) {
            Log.i("Ankit", "connected");
            return true;
        } else {
            Log.i("Ankit", "not connected");
            return false;
        }
    }

    public class ServiceManager extends ContextWrapper {

        public ServiceManager(Context base) {
            super(base);
        }

        public boolean isNetworkAvailable() {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
            return false;
        }

    }

}