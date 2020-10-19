package com.carrizane.cooldesignventas.controller;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String ENDPOINT_PRODUCTS = "https://create-ventas-service.herokuapp.com/";
    private static final String ENDPOINT_STOCK = "https://stock-service-py.herokuapp.com/stock/";
    private static final String ENDPOINT_CART = "https://cart-service.herokuapp.com/";
    private static final String ENDPOINT_SALE= "http://158.101.116.176:2222/devon-market/api/";
    private static final String ENDPOINT_LOGIN = "https://signventasin.000webhostapp.com/";

    private static Retrofit retrofitProduct;
    private static Retrofit retrofitStock;
    private static Retrofit retrofitCart;
    private static Retrofit retrofitSale;
    private static Retrofit retrofitLogin;

    public static Retrofit getApiProductClient(){
        if (retrofitProduct == null){
            retrofitProduct = new Retrofit.Builder().baseUrl(ENDPOINT_PRODUCTS).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofitProduct;
    }

    public static Retrofit getApiStockClient(){
        if (retrofitStock == null){
            retrofitStock = new Retrofit.Builder().baseUrl(ENDPOINT_STOCK).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofitStock;
    }

    public static Retrofit getApiCartClient(){
        if (retrofitCart == null){
            retrofitCart = new Retrofit.Builder().baseUrl(ENDPOINT_CART).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofitCart;
    }

    public static Retrofit getApiSaleClient(){
        if (retrofitSale == null){
            retrofitSale = new Retrofit.Builder().baseUrl(ENDPOINT_SALE).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofitSale;
    }

    public static Retrofit getApiLoginClient(){
        if (retrofitLogin == null){
            retrofitLogin = new Retrofit.Builder().baseUrl(ENDPOINT_LOGIN).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofitLogin;
    }

}
