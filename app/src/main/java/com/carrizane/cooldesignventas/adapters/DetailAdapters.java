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
import com.carrizane.cooldesignventas.model.ProductSale;

import java.util.List;

public class DetailAdapters extends RecyclerView.Adapter<DetailAdapters.DetailHolderView> {
    private Context context;
    private List<ProductSale> listDetail;
    private ItemClickListener itemClickListener;

    public DetailAdapters(Context context, List<ProductSale> listDetail, ItemClickListener itemClickListener) {
        this.context = context;
        this.listDetail = listDetail;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public DetailHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_devices_recycler_item, parent, false);
        return new DetailHolderView(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailHolderView holder, int position) {
        ProductSale ps = listDetail.get(position);
        holder.name.setText(ps.getName());
        holder.quantity.setText(String.valueOf(ps.getQuantity()));
        holder.total.setText(String.valueOf(ps.getPrice()));
        Glide.with(context).load(ps.getUrl()).error(R.drawable.samsung_logo).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return listDetail.size();
    }

    class DetailHolderView extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name, quantity, total;
        ImageView image;
        CardView cardDetail;
        ItemClickListener itemClickListener;

        public DetailHolderView(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            name = itemView.findViewById(R.id.nameDetail);
            quantity = itemView.findViewById(R.id.quantityDetail);
            total = itemView.findViewById(R.id.totalDetail);

            image = itemView.findViewById(R.id.imageDetail);
            cardDetail = itemView.findViewById(R.id.cardDetail);

            this.itemClickListener = itemClickListener;
            cardDetail.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }

}
