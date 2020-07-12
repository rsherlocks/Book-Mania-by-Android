package com.example.androidshaper.book_sell;

public class MyItem {
    String id;
    String name;
    String cetagory;
    String author;
    String description;
    String publish;
    String picture;
    String pdf;
    String price;
    String sell;

    public MyItem(String id, String name, String cetagory, String author, String description, String publish, String picture, String pdf, String price, String sell) {
        this.id = id;
        this.name = name;
        this.cetagory = cetagory;
        this.author = author;
        this.description = description;
        this.publish = publish;
        this.picture = picture;
        this.pdf = pdf;
        this.price = price;
        this.sell = sell;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCetagory() {
        return cetagory;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getPublish() {
        return publish;
    }

    public String getPicture() {
        return picture;
    }

    public String getPdf() {
        return pdf;
    }

    public String getPrice() {
        return price;
    }

    public String getSell() {
        return sell;
    }
}

