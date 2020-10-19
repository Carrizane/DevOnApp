package com.carrizane.cooldesignventas.controller;

import com.carrizane.cooldesignventas.model.Product;
import com.carrizane.cooldesignventas.model.ProductSale;
import com.carrizane.cooldesignventas.model.Sale;
import com.carrizane.cooldesignventas.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("product/all")
    Call<List<Product>> getAllProducts();

    @PUT("update")
    Call<Product> updateProduct(@Body Product product);

    @POST("cart/create")
    Call<ProductSale> addSale(@Body Sale sale);

    @GET("findByClient/{client}")
    Call<List<Sale>> getAllSale(@Path("client") String client);

    @FormUrlEncoded
    @POST("login.php")
    Call<User> signInUser(@Field("email") String email, @Field("password") String password);

}
