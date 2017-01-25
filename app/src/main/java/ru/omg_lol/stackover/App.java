package ru.omg_lol.stackover;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import ru.omg_lol.stackover.activity.common.BaseActivity;
import ru.omg_lol.stackover.api.ApiService;
import timber.log.Timber;

public class App extends Application implements ServiceConnection {
    private static App sInstance;

    private BaseActivity mCurrentActivity;

    public static App get() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());

        sInstance = this;

        bindApiService();
    }

    private void bindApiService() {
        Intent intent = new Intent(this, ApiService.class);
        bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    public void setCurrentActivity(BaseActivity activity) {
        mCurrentActivity = activity;
    }

    public BaseActivity getCurrentActivity() {
        return mCurrentActivity;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) { }

    @Override
    public void onServiceDisconnected(ComponentName name) { }
}
