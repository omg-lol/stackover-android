package ru.omg_lol.stackover.api.command;

public class CommandResult {
    private int mErrorCode = -1;
    private int mResultCode = -1;
    private Object mData = null;

    public boolean isError() {
        return mErrorCode > 0;
    }

    public int getErrorCode() {
        return mErrorCode;
    }

    public int getResultCode() {
        return mResultCode;
    }

    public void setErrorCode(int mErrorCode) {
        this.mErrorCode = mErrorCode;
    }

    public void setResultCode(int mResultCode) {
        this.mResultCode = mResultCode;
    }

    public Object getData() {
        return mData;
    }

    public static CommandResult createErrorResult(int errorCode) {
        CommandResult commandResult = new CommandResult();
        commandResult.setErrorCode(errorCode);
        return commandResult;
    }

    public static CommandResult createSuccessResult(int resultCode) {
        CommandResult commandResult = new CommandResult();
        commandResult.setResultCode(resultCode);
        return commandResult;
    }

    public static CommandResult createSuccessResult(int resultCode, Object data) {
        CommandResult commandResult = new CommandResult();
        commandResult.setResultCode(resultCode);
        commandResult.mData = data;
        return commandResult;
    }
}