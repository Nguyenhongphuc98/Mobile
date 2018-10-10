package com.example.uitstark.dailys_notes.DTO;


import java.util.Date;

public class ToDo {
    private int id;
    private int id_user;
    private String title;
    private String content;
    private Date time;


    public ToDo(int id, int id_user, String title, String content, Date time) {
        this.id = id;
        this.id_user = id_user;
        this.title = title;
        this.content = content;
        this.time = time;
    }
    public ToDo(int id_user, String title, String content, Date time) {
        this.id = id;
        this.id_user = id_user;
        this.title = title;
        this.content = content;
        this.time = time;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

}
