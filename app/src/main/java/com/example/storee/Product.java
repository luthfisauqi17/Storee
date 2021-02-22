package com.example.storee;

public class Product {
    private int id;
    private String name;
    private int stock;
    private double price;
    private String image;
    private String createAt;

    public Product(int id, String name, int stock, double price, String image, String createAt) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.image = image;
        this.createAt = createAt;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public double getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getCreateAt() {
        return createAt;
    }
}
