package com.carrizane.cooldesignventas.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductSale implements Serializable {

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("stock")
    private int stock;

    @Expose
    @SerializedName("url")
    private String url;

    @Expose
    @SerializedName("trademark")
    private String trademark;

    @Expose
    @SerializedName("price")
    private Double price;

    @Expose
    @SerializedName("quantity")
    private int quantity;

    public ProductSale() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTrademark() {
        return trademark;
    }

    public void setTrademark(String trademark) {
        this.trademark = trademark;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
