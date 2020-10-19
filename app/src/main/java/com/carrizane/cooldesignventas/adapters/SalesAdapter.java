package com.carrizane.cooldesignventas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.carrizane.cooldesignventas.R;
import com.carrizane.cooldesignventas.model.Sale;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.SalesHolderView> {
    private Context context;
    private List<Sale> saleList;
    ItemClickListener itemClickListener;

    public SalesAdapter(Context context, List<Sale> saleList, ItemClickListener itemClickListener) {
        this.context = context;
        this.saleList = saleList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public SalesHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sales_recycler_item, parent, false);
        return new SalesHolderView(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SalesHolderView holder, int position) {
        Sale sale = saleList.get(position);
        holder.name.setText(sale.get_id().substring(0, 7));
        DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        Date date = null;
        try {
            date = inputFormat.parse(sale.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.date.setText(outputFormat.format(date));
        holder.total.setText(sale.getTotal());
        holder.number.setText(String.valueOf(sale.getProducts().size()));
    }

    @Override
    public int getItemCount() {
        return saleList.size();
    }

    class SalesHolderView extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name, date, total, number;
        CardView card_sale;
        ItemClickListener itemClickListener;

        public SalesHolderView(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            name = itemView.findViewById(R.id.nameSale);
            date = itemView.findViewById(R.id.dateSale);
            total = itemView.findViewById(R.id.totalSale);
            number = itemView.findViewById(R.id.numberSale);

            card_sale = itemView.findViewById(R.id.cardSale);

            this.itemClickListener = itemClickListener;
            card_sale.setOnClickListener(this);
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
