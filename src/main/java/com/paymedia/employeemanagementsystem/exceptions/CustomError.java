package com.paymedia.employeemanagementsystem.exceptions;

public class CustomError {

    private String message;
    private int status;

    public CustomError(String message, int status) {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
