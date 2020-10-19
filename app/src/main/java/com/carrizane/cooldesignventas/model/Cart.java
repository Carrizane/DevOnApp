package com.carrizane.cooldesignventas.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name="carts")
public class Cart extends Model {

    @Column(name = "name")
    private String name;

    @Column(name = "trademark")
    private String trademark;

    @Column(name = "stock")
    private int stock;

    @Column(name = "price")
    private Double price;

    @Column(name = "url")
    private String url;

    @Column(name = "quantity")
    private int quantity;

    public Cart() {
        super();
    }

    public Cart(String name, String trademark, int stock, Double price, String url, int quantity) {
        this.name = name;
        this.trademark = trademark;
        this.stock = stock;
        this.price = price;
        this.url = url;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrademark() {
        return trademark;
    }

    public void setTrademark(String trademark) {
        this.trademark = trademark;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
