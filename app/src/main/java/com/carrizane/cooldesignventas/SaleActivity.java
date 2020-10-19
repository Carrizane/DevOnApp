package com.carrizane.cooldesignventas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.carrizane.cooldesignventas.adapters.SalesAdapter;
import com.carrizane.cooldesignventas.controller.ApiClient;
import com.carrizane.cooldesignventas.controller.ApiInterface;
import com.carrizane.cooldesignventas.model.Sale;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaleActivity extends AppCompatActivity {

    Button back;

    RecyclerView recyclerView;

    SalesAdapter adapter;
    SalesAdapter.ItemClickListener itemClickListenerSale;

    List<Sale> saleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);
        this.getSupportActionBar().hide();

        back = (Button) findViewById(R.id.backMainBtn);

        recyclerView = (RecyclerView) findViewById(R.id.listSales);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences prefs = getSharedPreferences("PREFS", 0);
        String clientPrefs = prefs.getString("nameUser", "") + " " + prefs.getString("last_nameUser", "");
        getAllSales(clientPrefs);

        back.setOnClickListener( (view) -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });

        Intent intent = new Intent(SaleActivity.this, DetailActivity.class);
        Bundle args = new Bundle();

        itemClickListenerSale = ( (view, position) -> {
            Sale saleBundle = saleList.get(position);
            saleBundle.set_id(saleList.get(position).get_id());
            args.putSerializable("saleObject", (Serializable) saleBundle);
            intent.putExtra("sale", args);
            startActivity(intent);
        });
    }

    public void getAllSales(String client){
        ApiInterface apiInterface = ApiClient.getApiSaleClient().create(ApiInterface.class);
        Call<List<Sale>> call = apiInterface.getAllSale(client);
        call.enqueue(new Callback<List<Sale>>() {
            @Override
            public void onResponse(Call<List<Sale>> call, Response<List<Sale>> response) {
                onAllGetResult(response.body());
            }
            @Override
            public void onFailure(Call<List<Sale>> call, Throwable t) {
                Toast.makeText(SaleActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onAllGetResult(List<Sale> sales){
        adapter = new SalesAdapter(this, sales, itemClickListenerSale);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        saleList = sales;
    }
}