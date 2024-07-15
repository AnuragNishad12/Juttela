package com.example.juttela.FrontPage;

import java.util.ArrayList;

public class FinalUser {

    public String name;
    public String age;
    public String country;
    public String gender;
    public String selectedCountry;
    public String imageUri;
    public String userId;

    // Add a String array to store multiple pieces of data
    public ArrayList<String> additionalData;

    public FinalUser() {
    }

    public FinalUser(String name, String age, String country, String gender, String selectedCountry, String imageUri, String userId, ArrayList<String> additionalData) {
        this.name = name;
        this.age = age;
        this.country = country;
        this.gender = gender;
        this.selectedCountry = selectedCountry;
        this.imageUri = imageUri;
        this.userId = userId;
        this.additionalData = additionalData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSelectedCountry() {
        return selectedCountry;
    }

    public void setSelectedCountry(String selectedCountry) {
        this.selectedCountry = selectedCountry;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public ArrayList<String> getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(ArrayList<String> additionalData) {
        this.additionalData = additionalData;
    }
}
