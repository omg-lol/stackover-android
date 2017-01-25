package ru.omg_lol.stackover.activity.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.omg_lol.stackover.App;
import timber.log.Timber;

public class BaseActivity extends AppCompatActivity {
    public static final String ERROR_MESSAGE_CANT_CONNECT_TO_SERVER = "Не удалось подключиться к серверу";

    protected final Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Timber.i("onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    protected void onStart() {
        Timber.i("onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Timber.i("onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Timber.i("onResume");
        super.onResume();

        App.get().setCurrentActivity(this);
    }

    @Override
    protected void onPause() {
        Timber.i("onPause");

        if (App.get().getCurrentActivity() == this) {
            App.get().setCurrentActivity(null);
        }

        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Timber.i("onNewIntent");
    }

    @Override
    protected void onStop() {
        Timber.i("onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Timber.i("onDestroy");
        super.onDestroy();
    }

    public void showNetworkFailureModal() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ошибка");
        builder.setMessage(ERROR_MESSAGE_CANT_CONNECT_TO_SERVER);

        builder.setCancelable(true);
        builder.setNegativeButton("Закрыть", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}