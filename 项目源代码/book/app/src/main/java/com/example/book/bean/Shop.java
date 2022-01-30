package com.example.book.bean;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Shop implements Serializable {
    private String id;
    private String userId;
    private String title;
    private String info;
    private String price;
    private Bitmap image;
    private String time;
    private String contact;

    public String getId() {
        return id;
    }
    public void setId(String id) { this.id = id; }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public Bitmap getImage() {
        return image;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", info='" + info + '\'' +
                ", price='" + price + '\'' +
                ", image='" + image + '\'' +
                ", time='" + time + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }



}
