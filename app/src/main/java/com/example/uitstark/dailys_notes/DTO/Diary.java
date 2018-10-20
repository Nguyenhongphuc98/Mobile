package com.example.uitstark.dailys_notes.DTO;


public class Diary {

    private int id;
    private int id_user;
    private String title;
    private String content;
    private String img;
    private String font;
    private int color;


    public Diary(int id, int id_user, String title, String content, String img, String font, int color) {
        this.id = id;
        this.id_user = id_user;
        this.title = title;
        this.content = content;
        this.img = img;
        this.font = font;
        this.color = color;
    }
    public Diary( int id_user, String title, String content, String img, String font, int color) {
        this.id = id;
        this.id_user = id_user;
        this.title = title;
        this.content = content;
        this.img = img;
        this.font = font;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}