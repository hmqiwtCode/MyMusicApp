package com.example.serviceboundmusic;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

public class ConnectServiceM {
    private static MyService myService;
    public static boolean isBound = false;
    private static ServiceConnection connection;

    public static synchronized ServiceConnection getService() {
        if (connection == null) {
            connection = new ServiceConnection() {
                @Override
                public void onServiceDisconnected(ComponentName name) {
                    isBound = false;
                }
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    MyService.MyBinder binder = (MyService.MyBinder) service;
                    myService = binder.getService();
                    isBound = true;
                }
            };
        }
        return connection;
    }




}
