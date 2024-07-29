package com.example.juttela.Fragment.Model;

public class FriendRequest {
    private String senderId;
    private String receiverId;
    private String status;
    private String image;
    private String name;

    public FriendRequest(String senderId, String receiverId, String image, String name) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.image = image;
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FriendRequest() {
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
