package com.example.juttela.Fragment.Model;

public class Request {
    private String senderId;
    private String receiverId;
    private String status;

    public Request() {
    }

    public Request( String senderId, String receiverId, String status) {

        this.senderId = senderId;
        this.receiverId = receiverId;
        this.status = status;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
