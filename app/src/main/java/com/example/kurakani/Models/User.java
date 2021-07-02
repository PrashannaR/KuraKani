package com.example.kurakani.Models;

public class User {

    private String uid, name, phoneNumber, profilePhoto;
    public User() {

    }
    public User(String imageUrl){

    }

    public User(String uid, String imageUrl){
        
    }

    public User(String uid, String name, String phoneNumber, String profilePhoto) {
        this.uid = uid;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.profilePhoto = profilePhoto;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}
