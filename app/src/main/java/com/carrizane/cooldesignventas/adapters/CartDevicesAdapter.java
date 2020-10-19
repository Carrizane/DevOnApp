package com.carrizane.cooldesignventas.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.carrizane.cooldesignventas.R;
import com.carrizane.cooldesignventas.model.Cart;
import com.carrizane.cooldesignventas.model.Product;

import java.util.List;

public class CartDevicesAdapter extends RecyclerView.Adapter<CartDevicesAdapter.CartDevicesHolder> {
    private Context context;
    private List<Cart> cartList;
    private ItemClickListener itemClickListener;

    public CartDevicesAdapter(Context context, List<Cart> cartList, ItemClickListener itemClickListener) {
        this.context = context;
        this.cartList = cartList;
        this.itemClickListener = itemClickListener;
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        itemClickListener = listener;
    }

    @NonNull
    @Override
    public CartDevicesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_recycler_item, parent, false);
        return new CartDevicesHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CartDevicesHolder holder, int position) {
        Cart cart = cartList.get(position);
        holder.name.setText(cart.getName());
        holder.total.setText(String.valueOf(cart.getPrice()*cart.getQuantity()));
        holder.quantity.setText(String.valueOf(cart.getQuantity()));
        Glide.with(context).load(cart.getUrl()).error(R.drawable.samsung_logo).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    class CartDevicesHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name, total, quantity;
        ImageView image;
        CardView cardCart;
        ItemClickListener itemClickListener;
        Button delete;

        public CartDevicesHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            name = itemView.findViewById(R.id.nameCart);
            total = itemView.findViewById(R.id.totalProduct);
            quantity = itemView.findViewById(R.id.quantityProduct);

            image = itemView.findViewById(R.id.imageCart);
            cardCart = itemView.findViewById(R.id.cardCart);

            delete = itemView.findViewById(R.id.deleteProduct);

            this.itemClickListener = itemClickListener;

            delete.setOnClickListener( (view) -> {
                if (itemClickListener != null){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        itemClickListener.onDeleteClick(position);
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public interface ItemClickListener{
        void onItemClick(View view, int position);
        void onDeleteClick(int position);
    }

}
