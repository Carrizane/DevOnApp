package com.carrizane.cooldesignventas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.carrizane.cooldesignventas.adapters.AllDevicesAdapter;
import com.carrizane.cooldesignventas.adapters.NewDevicesAdapter;
import com.carrizane.cooldesignventas.controller.ApiClient;
import com.carrizane.cooldesignventas.controller.ApiInterface;
import com.carrizane.cooldesignventas.model.Product;

import java.io.Serializable;
import java.util.List;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView, recyclerViewAll;

    NewDevicesAdapter adapterHorizontal;
    AllDevicesAdapter adapterAll;

    NewDevicesAdapter.ItemClickListener itemClickListenerNew;
    AllDevicesAdapter.ItemClickListener itemClickListenerAll;

    List<Product> listHorizontal;
    List<Product> listAll;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getSupportActionBar().hide();

        recyclerView = (RecyclerView) findViewById(R.id.listProducts);
        recyclerViewAll = (RecyclerView) findViewById(R.id.listProductsH);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navBottom);

        getProducts();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        recyclerViewAll.setHasFixedSize(true);
            recyclerViewAll.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = new Intent(MainActivity.this, CartActivity.class);
        Bundle args = new Bundle();

        itemClickListenerNew = ((view, position) -> {
            Product p = listHorizontal.get(position);
            args.putSerializable("productObject", (Serializable) p);
            intent.putExtra("product", args);
            startActivity(intent);
        });

        itemClickListenerAll = ((view, position) -> {
            Product p = listAll.get(position);
            args.putSerializable("productObject", (Serializable) p);
            intent.putExtra("product", args);
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.navigationHome:
                        Toast.makeText(MainActivity.this, "This is home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigationCart:
                        startActivity(new Intent(MainActivity.this, CartProductActivity.class));
                        break;
                    case R.id.navigationSale:
                        startActivity(new Intent(MainActivity.this, SaleActivity.class));
                        break;
                    case R.id.navigationLogout:
                        SharedPreferences prefs = getSharedPreferences("PREFS", 0);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.clear().commit();
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        break;
                }
                return true;
            }
        });
    }

    public void getProducts(){
        ApiInterface apiInterface = ApiClient.getApiProductClient().create(ApiInterface.class);
        Call<List<Product>> call = apiInterface.getAllProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful() && response.body() != null){
                    onAllGetResult(response.body());
                    onNewGetResult(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onAllGetResult(List<Product> products){
        adapterHorizontal = new NewDevicesAdapter(this, products, itemClickListenerNew);
        adapterHorizontal.notifyDataSetChanged();
        recyclerView.setAdapter(adapterHorizontal);

        listHorizontal = products;
    }

    public void onNewGetResult(List<Product> products){
        adapterAll = new AllDevicesAdapter(this, products, itemClickListenerAll);
        adapterAll.notifyDataSetChanged();
        recyclerViewAll.setAdapter(adapterAll);

        listAll = products;
    }
}