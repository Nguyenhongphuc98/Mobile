package com.example.uitstark.dailys_notes.DTO;


import java.util.Date;

public class BirthDay {
    private int id;
    private int id_user;
    private String name;
    private Date bornDay;
    private String note;

    public BirthDay(int id, int id_user, String name, Date bornDay, String note) {
        this.id = id;
        this.id_user = id_user;
        this.name = name;
        this.bornDay = bornDay;
        this.note = note;
    }

    public BirthDay(int id_user, String name, Date bornDay, String note) {
        this.id = id;
        this.id_user = id_user;
        this.name = name;
        this.bornDay = bornDay;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBornDay() {
        return bornDay;
    }

    public void setBornDay(Date bornDay) {
        this.bornDay = bornDay;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
