package com.akgarg.apnagurukul.model;

public class ResponseMessage {

    private String message;
    private String severity;

    public ResponseMessage() {
    }

    public ResponseMessage(String message, String severity) {
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
