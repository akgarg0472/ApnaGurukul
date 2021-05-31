package com.akgarg.apnagurukul.model;

public class ErrorMessage {

    private String message;
    private String severity;

    public ErrorMessage() {
    }

    public ErrorMessage(String message, String severity) {
        this.message = message;
        this.severity = severity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
}
