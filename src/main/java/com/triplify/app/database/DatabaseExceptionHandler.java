package com.triplify.app.database;

public class DatabaseExceptionHandler extends Throwable {

    private final String errorMessage;
    private final Throwable error;

    public DatabaseExceptionHandler(String message, Throwable error) {
        super(message,error);
        this.errorMessage = message;
        this.error = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Throwable getError() {
        return error;
    }

    @Override
    public String toString() {
        return "DatabaseConnectionException{" +
                "errorMessage='" + errorMessage + '\'' +
                ", error=" + error +
                '}';
    }
}
