package ru.omg_lol.stackover.api.response;

public abstract class ApiResponse {
    public String status;
    public Error error;
    public int resultCode;

    public boolean isError() {
        return error != null;
    }

    public boolean isInvalidResponseError() {
        return isError() && error.code >= 100 && error.code <= 199 && error.code != 110 && error.code != 120;
    }

    public static class Error {
        public int code;
        public String message;
    }

    public static class InvalidResponseException extends Exception {
        public InvalidResponseException() {
            super();
        }

        public InvalidResponseException(String detailMessage) {
            super(detailMessage);
        }
    }
}
