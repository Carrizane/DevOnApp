package com.carrizane.cooldesignventas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.carrizane.cooldesignventas.adapters.AllDevicesAdapter;
import com.carrizane.cooldesignventas.adapters.DetailAdapters;
import com.carrizane.cooldesignventas.model.Product;
import com.carrizane.cooldesignventas.model.ProductSale;
import com.carrizane.cooldesignventas.model.Sale;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DetailAdapters adapters;
    DetailAdapters.ItemClickListener itemClickListener;

    TextView name, date;
    Button back;

    List<ProductSale> psList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        this.getSupportActionBar().hide();

        name = findViewById(R.id.nameDetail);
        date = findViewById(R.id.dateDetail);
        back = findViewById(R.id.backBtn);

        back.setOnClickListener( (view) -> {
            startActivity(new Intent(getApplicationContext(), SaleActivity.class));
        });

        recyclerView = findViewById(R.id.listDetails);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("sale");
        Sale sale = (Sale) args.getSerializable("saleObject");
        psList = sale.getProducts();

        name.setText(sale.get_id().substring(0, 7));
        Log.i("Id_traido", sale.get_id());
        date.setText(parseDate(sale.getDate()));
        onGetIntent(psList);
    }

    public void onGetIntent(List<ProductSale> products){
        adapters = new DetailAdapters(this, products, itemClickListener);
        adapters.notifyDataSetChanged();
        recyclerView.setAdapter(adapters);
    }

    public String parseDate(String date){
        DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        Date d = null;
        try {
            d = inputFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputFormat.format(d);
    }
}