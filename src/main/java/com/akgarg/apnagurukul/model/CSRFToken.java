package com.akgarg.apnagurukul.model;

public class CSRFToken {
    private final String username;
    private final String token;
    private final String message;

    public CSRFToken(String username, String token, String message) {
        this.username = username;
        this.token = token;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "CSRFToken{" +
                "username='" + this.getUsername() + '\'' +
                ", token='" + this.getToken() + '\'' +
                ", message='" + this.getMessage() + '\'' +
                '}';
    }
}
