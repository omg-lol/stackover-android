package ru.omg_lol.stackover.api.facade.common;

import ru.omg_lol.stackover.api.response.ApiResponse;

public class ApiException extends Exception {
    private ApiExceptionType mExceptionType;
    private int mErrorCode;

    public ApiException() {
        this(ApiExceptionType.UNKNOWN, -1, "");
    }

    public ApiException(ApiExceptionType exceptionType) {
        this(exceptionType, -1, "");
    }

    public ApiException(ApiResponse.Error responseError) {
        this(ApiExceptionType.KNOWN, responseError.code, responseError.message);
    }

    public ApiException(ApiExceptionType exceptionType, int errorCode, String detailMessage) {
        super(detailMessage);

        mExceptionType = exceptionType;
        mErrorCode = errorCode;
    }

    public ApiExceptionType getExceptionType() {
        return mExceptionType;
    }

    public int getErrorCode() {
        return mErrorCode;
    }
}