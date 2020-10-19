package com.carrizane.cooldesignventas.adapters;

import android.content.Context;
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

public class AllDevicesAdapter extends RecyclerView.Adapter<AllDevicesAdapter.AllDevicesHolder> {
    private Context context;
    private List<Product> productList;
    ItemClickListener itemClickListener;

    public AllDevicesAdapter(Context context, List<Product> productList, ItemClickListener itemClickListener) {
        this.context = context;
        this.productList = productList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public AllDevicesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.devices_horizontal_recycler_item, parent, false);
        return new AllDevicesHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AllDevicesHolder holder, int position) {
        Product product = productList.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(String.valueOf(product.getPrice()));
        Glide.with(context).load(product.getUrl()).error(R.drawable.samsung_logo).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class AllDevicesHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name, price;
        ImageView image;
        CardView cardHorizontal;
        ItemClickListener itemClickListener;

        public AllDevicesHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            name = itemView.findViewById(R.id.nameProductH);
            price = itemView.findViewById(R.id.priceProductH);
            image = itemView.findViewById(R.id.imageProductH);

            cardHorizontal = itemView.findViewById(R.id.cardProductH);

            this.itemClickListener = itemClickListener;
            cardHorizontal.setOnClickListener(this);
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
