package ru.omg_lol.stackover.api;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import ru.omg_lol.stackover.api.command.Command;

public class ApiService extends Service implements Runnable {
    private static Context sContext = null;
    private final IBinder mBinder = new LocalBinder();
    private boolean mInterrupt = false;
    private Thread thread;

    public ApiService() { }

    @Override
    public void onCreate()
    {
        super.onCreate();

        sContext = this;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void onDestroy()
    {
        mInterrupt = true;
        thread.interrupt();
        sContext = null;
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return mBinder;
    }

    @Override
    public void run()
    {
        while (!mInterrupt)
        {
            Command.takeCommand().execute(this);
        }
    }

    public static Context getContext() {
        return sContext;
    }

    public class LocalBinder extends Binder
    {
        public ApiService getService()
        {
            return ApiService.this;
        }
    }
}
