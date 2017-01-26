package ru.omg_lol.stackover;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;

import ru.omg_lol.stackover.activity.common.BaseActivity;
import ru.omg_lol.stackover.api.ApiService;
import ru.omg_lol.stackover.database.DBHelper;
import timber.log.Timber;

public class App extends Application implements ServiceConnection {
    private static App sInstance;
    private static DBHelper mDbHelper;
    private static SQLiteDatabase mDatabase;

    private BaseActivity mCurrentActivity;

    public static App get() {
        return sInstance;
    }
    public static DBHelper getDBHelper() {
        return mDbHelper;
    }
    public static SQLiteDatabase getDatabase() {
        return mDatabase;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());

        sInstance = this;
        mDbHelper = new DBHelper(getApplicationContext());
        mDatabase = mDbHelper.getWritableDatabase();

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
