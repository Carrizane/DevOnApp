package com.carrizane.cooldesignventas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.carrizane.cooldesignventas.model.Cart;
import com.carrizane.cooldesignventas.model.Product;

public class CartActivity extends AppCompatActivity {

    TextView name, price, quantity;

    ImageView image;

    Button minus, plus, add, back;

    Cart cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        this.getSupportActionBar().hide();

        minus = (Button) findViewById(R.id.minusBtn);
        plus = (Button) findViewById(R.id.plusBtn);
        add = (Button) findViewById(R.id.addCart);
        back = (Button) findViewById(R.id.backBtn);

        image = (ImageView) findViewById(R.id.imageObject);

        name = (TextView) findViewById(R.id.nameObject);
        price = (TextView) findViewById(R.id.priceObject);
        quantity = (TextView) findViewById(R.id.quantityNumber);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("product");
        Product product = (Product) args.getSerializable("productObject");

        if (product.getStock() == 0){
            quantity.setText("Out of stock");
            minus.setEnabled(false);
            plus.setEnabled(false);
            add.setEnabled(false);
        }else{
            cart = new Cart();
            cart.setName(product.getName());
            cart.setTrademark(product.getTrademark());
            cart.setStock(product.getStock());
            cart.setPrice(product.getPrice());
            cart.setUrl(product.getUrl());
            cart.setQuantity(1);
            quantity.setText(String.valueOf(cart.getQuantity()));
        }

        name.setText(product.getName());
        price.setText(String.valueOf(product.getPrice()));

        Log.i("CheckIfNull", product.getUrl());
        Glide.with(this).load(product.getUrl()).error(R.drawable.samsung_logo).into(image);

        back.setOnClickListener( (view) ->{
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });

        minus.setOnClickListener( (view) -> {
            minusQuantity(quantity.getText().toString(), cart);
        });

        plus.setOnClickListener( (view) -> {
            plusQuantity(quantity.getText().toString(), cart);
        });

        Intent i = new Intent(CartActivity.this, CartProductActivity.class);
        add.setOnClickListener( (view) ->{
            addCart(cart);
            startActivity(i);
        });
    }

    private void addCart(Cart cart){
        cart.save();
    }

    private void minusQuantity(String number, Cart cart){
        int q = Integer.parseInt(number);
        if (q ==1){
            Toast.makeText(this, "Min. Value 1", Toast.LENGTH_SHORT).show();
        }else{
            q -= 1;
            quantity.setText(String.valueOf(q));
            cart.setQuantity(q);
        }
    }

    private void plusQuantity(String number, Cart cart){
        int q = Integer.parseInt(number);
        if (q == cart.getStock()){
            Toast.makeText(this, "Max. value " + cart.getStock(), Toast.LENGTH_SHORT).show();
        }else{
            q += 1;
            quantity.setText(String.valueOf(q));
            cart.setQuantity(q);
        }
    }

}