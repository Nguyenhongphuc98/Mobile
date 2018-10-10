package com.example.uitstark.dailys_notes.DTO;


public class User {

    private int id;
    private String email;
    private String passWord;
    private String fullName;
    private String phoneNumber;

    public  User(int id, String email,String passWord,String fullName,String phoneNumber){
        this.id=id;
        this.email=email;
        this.passWord=passWord;
        this.fullName=fullName;
        this.phoneNumber=phoneNumber;
    }
    public  User(String email,String passWord,String fullName,String phoneNumber){
        this.id=0;
        this.email=email;
        this.passWord=passWord;
        this.fullName=fullName;
        this.phoneNumber=phoneNumber;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}