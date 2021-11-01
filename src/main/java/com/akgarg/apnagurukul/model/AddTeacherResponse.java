package com.akgarg.apnagurukul.model;


@SuppressWarnings("unused")
public class AddTeacherResponse {

    private int status;
    private String message;

    public AddTeacherResponse() {
    }

    public AddTeacherResponse(int status,
                              String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AddTeacherResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
