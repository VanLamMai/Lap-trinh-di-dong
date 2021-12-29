package com.example.foodlistview;

public class Product {
    private int productImage;
    private String productName;
    private String productDescription;
    private double productPrice;

    public Product(int productImage, String productName, String productDescription, double productPrice) {
        this.productImage = productImage;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
    }

    public int getImage() {
        return productImage;
    }

    public void setImage(int productImage) {
        this.productImage = productImage;
    }

    public String getName() {
        return productName;
    }

    public void setName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return productDescription;
    }

    public void setDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public double getPrice() {
        return productPrice;
    }

    public void setPrice(double productPrice) {
        this.productPrice = productPrice;
    }
    public String toString(){
        return "Name: "+getName() +"\n" +"Description :"+getDescription()+"\n"
                +"Price: "+getPrice();
    }
}
