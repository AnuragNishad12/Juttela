package com.example.juttela.FrontPage;

public class FinalUser {

    public String name;
    public String age;
    public String country;
    public String gender;
    public String selectedCountry;
    public String imageUri;


    public FinalUser() {
    }

    public FinalUser(String name, String age, String country, String gender, String selectedCountry, String imageUri) {
        this.name = name;
        this.age = age;
        this.country = country;
        this.gender = gender;
        this.selectedCountry = selectedCountry;
        this.imageUri = imageUri;

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


}
