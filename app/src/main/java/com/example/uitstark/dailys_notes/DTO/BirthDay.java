package com.example.uitstark.dailys_notes.DTO;


import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class BirthDay implements Serializable{
    private int id;
    private int id_user;
    private String name;
    private String bornDay;
    private String timeRemind;
    private String note;
    private int Status;
    private int color;

    public BirthDay() {

    }

    public BirthDay(int id, int id_user, String name, String bornDay,String timeRemind, String note,int status,int color) {
        this.id = id;
        this.id_user = id_user;
        this.name = name;
        this.bornDay = bornDay;
        this.timeRemind=timeRemind;
        this.note = note;
        this.Status=status;
        this.color=color;
    }

    public BirthDay(int id_user, String name, String bornDay,String timeRemind, String note,int status,int color) {
        this.id_user = id_user;
        this.name = name;
        this.bornDay = bornDay;
        this.timeRemind=timeRemind;
        this.note = note;
        this.Status=status;
        this.color=color;
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

    public String getBornDay() {
        return bornDay;
    }

    public void setBornDay(String bornDay) {
        this.bornDay = bornDay;
    }

    public String getTimeRemind() {
        return timeRemind;
    }

    public void setTimeRemind(String timeRemind) {
        this.timeRemind = timeRemind;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
