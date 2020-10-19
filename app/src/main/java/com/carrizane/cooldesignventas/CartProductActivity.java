package com.carrizane.cooldesignventas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.carrizane.cooldesignventas.adapters.CartDevicesAdapter;
import com.carrizane.cooldesignventas.controller.ApiClient;
import com.carrizane.cooldesignventas.controller.ApiInterface;
import com.carrizane.cooldesignventas.model.Cart;
import com.carrizane.cooldesignventas.model.Product;
import com.carrizane.cooldesignventas.model.ProductSale;
import com.carrizane.cooldesignventas.model.Sale;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartProductActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    CartDevicesAdapter adapter;
    CartDevicesAdapter.ItemClickListener itemClickListener;

    List<Cart> cartList;

    Button back, confirm;
    TextView total;

    String name, last_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_product);
        this.getSupportActionBar().hide();

        recyclerView = (RecyclerView) findViewById(R.id.listCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        back = (Button) findViewById(R.id.backMainBtn);
        confirm = (Button) findViewById(R.id.confirmBtn);

        total = (TextView) findViewById(R.id.totalCart);

        cartList = getAllCart();
        setCart(getAllCart());
        total.setText(String.valueOf(plusTotal(getAllCart())));

        back.setOnClickListener( (view) -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });

        confirm.setOnClickListener( (view) -> {
            List<ProductSale> ps = new ArrayList<>();
            String t = total.getText().toString();
            for (int i = 0; i < cartList.size(); i++) {
                Cart cartConfirm = cartList.get(i);
                Log.i("NOMBRES_CELUCOS", cartConfirm.getName() + " " + getAllCart().size());
                deleteCart(cartConfirm);
                updateStock(setProductObject(cartConfirm));
                ps.add(setDataConverter(cartConfirm));
            }
            SharedPreferences prefs = getSharedPreferences("PREFS", 0);
            name = prefs.getString("nameUser", "");
            last_name = prefs.getString("last_nameUser", "");
            Sale s = new Sale(name + " " + last_name, ps, t);
            createSales(s);
            startActivity(new Intent(getApplicationContext(), SaleActivity.class));
        });

        adapter.setOnItemClickListener(new CartDevicesAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {}
            @Override
            public void onDeleteClick(int position) {
                Cart cart = cartList.get(position);
                deleteCart(cart);
                String oldNumber = total.getText().toString();
                total.setText(String.valueOf(minusTotal(cart, oldNumber)));
                cartList.remove(position);
                recyclerView.removeViewAt(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, cartList.size());
            }
        });
    }

    public ProductSale setDataConverter(Cart cart){
        ProductSale productSale = new ProductSale();
        productSale.setName(cart.getName());
        productSale.setPrice(cart.getPrice());
        productSale.setQuantity(cart.getQuantity());
        productSale.setStock(cart.getStock());
        productSale.setTrademark(cart.getTrademark());
        productSale.setUrl(cart.getUrl());
        return productSale;
    }

    public Product setProductObject(Cart cart){
        Product product = new Product();
        product.setName(cart.getName());
        product.setTrademark(cart.getTrademark());
        product.setPrice(cart.getPrice());
        product.setStock(cart.getStock());
        product.setUrl(cart.getUrl());
        product.setQuantity(cart.getQuantity());
        return product;
    }

    public String plusTotal(List<Cart> carts){
        Double n = 0.0;
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        for (int i = 0; i < carts.size(); i++) {
            n += carts.get(i).getQuantity() * carts.get(i).getPrice();
        }
        return numberFormat.format(n);
    }

    public String minusTotal(Cart cart, String old){
        Double n = 0.0;
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        n = cart.getQuantity() * cart.getPrice();
        n = Double.parseDouble(old) - n;
        return numberFormat.format(n);
    }

    public void createSales(Sale sale){
        ApiInterface apiInterface = ApiClient.getApiCartClient().create(ApiInterface.class);
        Call<ProductSale> call = apiInterface.addSale(sale);
        call.enqueue(new Callback<ProductSale>() {
            @Override
            public void onResponse(Call<ProductSale> call, Response<ProductSale> response) {}
            @Override
            public void onFailure(Call<ProductSale> call, Throwable t) {
                Toast.makeText(CartProductActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateStock(Product product){
        ApiInterface apiInterface = ApiClient.getApiStockClient().create(ApiInterface.class);
        Call<Product> call = apiInterface.updateProduct(product);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {}
            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                Toast.makeText(CartProductActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public List<Cart> getAllCart(){
        return new Select().from(Cart.class).execute();
    }

    public void deleteCart(Cart cart){
        cart = Cart.load(Cart.class, cart.getId());
        cart.delete();
    }

    public void setCart(List<Cart> carts){
        adapter = new CartDevicesAdapter(this, carts, itemClickListener);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        cartList = carts;
    }
}