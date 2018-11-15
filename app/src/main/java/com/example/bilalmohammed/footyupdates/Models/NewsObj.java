package com.example.bilalmohammed.footyupdates.Models;



public class NewsObj {
    String author, title, desc, url, image_url, publish_date;

    // stores all the objects that are ging to be used]

    public NewsObj(String author, String title, String desc, String url, String image_url, String publish_date) {
        this.author = author;
        this.title = title;
        this.desc = desc;
        this.url = url;
        this.image_url = image_url;
        this.publish_date = publish_date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }
}
