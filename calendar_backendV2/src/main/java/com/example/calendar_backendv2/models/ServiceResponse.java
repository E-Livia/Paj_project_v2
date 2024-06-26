package com.example.calendar_backendv2.models;

public class ServiceResponse {
    private int code;
    private String message;

    public ServiceResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    // Getter for code
    public int getCode() {
        return code;
    }

    // Setter for code (if needed)
    public void setCode(int code) {
        this.code = code;
    }

    // Getter for message
    public String getMessage() {
        return message;
    }

    // Setter for message (if needed)
    public void setMessage(String message) {
        this.message = message;
    }
}
