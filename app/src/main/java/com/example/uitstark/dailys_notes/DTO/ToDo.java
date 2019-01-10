package com.example.uitstark.dailys_notes.DTO;


import java.util.Date;

public class ToDo {
    private int id;
    private int id_user;
    private String title;
    private String content;
    private String time;
    private int status; // 0: chưa làm, 1: đã làm
    private int color;


    public ToDo(int id, int id_user, String title, String content, String time, int status, int color) {
        this.id = id;
        this.id_user = id_user;
        this.title = title;
        this.content = content;
        this.time = time;
        this.status = status;
        this.color = color;
    }

    public ToDo(int id_user, String title, String content, String time, int status, int color) {
        this.id_user = id_user;
        this.title = title;
        this.content = content;
        this.time = time;
        this.status = status;
        this.color = color;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
