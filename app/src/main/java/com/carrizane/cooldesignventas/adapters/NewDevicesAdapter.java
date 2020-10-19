package com.carrizane.cooldesignventas.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.carrizane.cooldesignventas.R;
import com.carrizane.cooldesignventas.model.Product;

import java.util.List;

public class NewDevicesAdapter extends RecyclerView.Adapter<NewDevicesAdapter.MyNewDevicesHolder> {

    private Context context;
    private List<Product> productList;
    ItemClickListener itemClickListener;

    public NewDevicesAdapter(Context context, List<Product> productList, ItemClickListener itemClickListener) {
        this.context = context;
        this.productList = productList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyNewDevicesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_devices_recycler_item, parent, false);
        return new MyNewDevicesHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyNewDevicesHolder holder, int position) {
        Product product = productList.get(position);
        holder.name.setText(product.getName());
        holder.trademark.setText(product.getTrademark());
        holder.price.setText(String.valueOf(product.getPrice()));
        if (product.getStock() == 0){
            holder.stock.setText("Sold Out");
            holder.stock.setBackgroundResource(R.color.soldOut);
            holder.stock.setTextColor(Color.WHITE);
            holder.stock.setPadding(5, 5, 5, 5);
        }else{
            holder.stock.setText(String.valueOf(product.getStock()));
        }
        Glide.with(context).load(product.getUrl()).error(R.drawable.samsung_logo).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class MyNewDevicesHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image;
        TextView name, price, stock, trademark;
        CardView card;
        ItemClickListener itemClickListener;

        public MyNewDevicesHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            image = itemView.findViewById(R.id.imageProduct);
            name = itemView.findViewById(R.id.nameProduct);
            price = itemView.findViewById(R.id.priceProduct);
            stock = itemView.findViewById(R.id.stockProduct);
            trademark = itemView.findViewById(R.id.trademarkProduct);

            card = itemView.findViewById(R.id.cardProduct);
            this.itemClickListener = itemClickListener;
            card.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }

}
