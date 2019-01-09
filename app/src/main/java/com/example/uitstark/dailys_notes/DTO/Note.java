package com.example.uitstark.dailys_notes.DTO;


public class Note {

    private int id;
    private int id_user;
    private String title;
    private String content;
    private int color;

    public Note(int id, int id_user, String title, String content, int color) {
        this.id = id;
        this.id_user = id_user;
        this.title = title;
        this.content = content;
        this.color = color;
    }

    public Note(int id_user, String title, String content, int color) {
        this.id_user = id_user;
        this.title = title;
        this.content = content;
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}