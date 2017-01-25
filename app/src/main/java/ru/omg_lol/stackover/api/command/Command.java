package ru.omg_lol.stackover.api.command;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import ru.omg_lol.stackover.App;
import ru.omg_lol.stackover.Constants;
import ru.omg_lol.stackover.activity.common.BaseActivity;
import ru.omg_lol.stackover.api.facade.common.ApiException;
import ru.omg_lol.stackover.api.facade.common.ApiExceptionType;

public abstract class Command
{
    public static final int RESULT_VALUE_SUCCESS = 0;
    public static final int ERROR_VALUE_COMMON = 1;
    public static final int ERROR_INVALID_TOKEN = 3;

    private static BlockingQueue<Command> sCommands = new LinkedBlockingQueue<>();

    private Listener mListener;
    private ExceptionListener mExceptionListener;

    public Command() { }

    protected abstract CommandResult doExecute(Context context);

    public final void execute(Context context)
    {
        CommandResult result = doExecute(context);

        if (result == null) {
            return;
        }

        if (result.isError()) {
            fireOnCommandExecuted(result.getErrorCode(), result.getData());
        } else {
            fireOnCommandExecuted(0, result.getData());
        }
    }

    public Command setListener(Listener listener) {
        mListener = listener;
        return this;
    }

    public Command setExceptionListener(ExceptionListener exceptionListener) {
        mExceptionListener = exceptionListener;
        return this;
    }

    public boolean run() {
        Log.d(Constants.LOG_TAG, "Run command " + this.getClass().getSimpleName());

        try {
            sCommands.put(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.e(Constants.LOG_TAG, e.getMessage());
            return false;
        }

        return true;
    }

    protected void fireOnCommandException(final ApiExceptionType exceptionType) {
        Handler handler = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mExceptionListener != null) {
                        mExceptionListener.onCommandException(exceptionType);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void fireOnCommandExecuted(int resultCode) {
        fireOnCommandExecuted(resultCode, null);
    }

    protected void fireOnCommandExecuted(final int resultCode, final Object data) {
        Handler handler = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mListener != null) {
                        mListener.onCommandExecuted(resultCode, data);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static Command takeCommand() {
        try {
            return sCommands.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected CommandResult handleException(ApiException exception) {
        switch (exception.getExceptionType()) {
            case KNOWN:
                return handleError(exception.getErrorCode());
            default:
                handleUnknownError(exception.getExceptionType());
                return null;

        }
    }

    protected CommandResult handleError(int errorCode) {
        if (App.get() == null) {
            System.exit(0);
        }

        switch (errorCode) {
            case 110:
                sCommands.clear();
                // TODO: restartWithError(<invalid token>)
                break;
            case 120:

                break;
        }

        return null;
    }

    private void restartWithError(String catchMode) {
        // TODO: force show splash activity with catchMode flag
    }

    private boolean showNetworkFailureModal() {
        final BaseActivity activity = App.get().getCurrentActivity();

        if (activity == null) {
            return false;
        }

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.showNetworkFailureModal();
            }
        });

        return true;
    }

    private void handleUnknownError(ApiExceptionType exceptionType) {
        if (App.get() == null) {
            System.exit(0);
        }

        if (mExceptionListener != null) {
            fireOnCommandException(exceptionType);
        } else {
            sCommands.clear();

            if (!showNetworkFailureModal()) {
                // TODO: restartWithError(<network problem>)
            }
        }
    }

    public interface Listener {
        void onCommandExecuted(int resultCode, Object data);
    }

    public interface ExceptionListener {
        void onCommandException(ApiExceptionType exceptionType);
    }
}