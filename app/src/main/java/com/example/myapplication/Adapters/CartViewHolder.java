package com.example.myapplication.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;

public class CartViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView nameView, dateView, actualDateView, timeView, actualTimeView, totalQuantity, actualTotalQuantity, totalPrice, actualTotalPrice;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        initializeViews();
    }

    public void initializeViews(){
        imageView = itemView.findViewById(R.id.cart_image_view);
        nameView = itemView.findViewById(R.id.cart_name_view);
        dateView = itemView.findViewById(R.id.cart_date_view);
        actualDateView = itemView.findViewById(R.id.actual_date_view);
        timeView = itemView.findViewById(R.id.cart_time_view);
        actualTimeView = itemView.findViewById(R.id.actual_time_view);
        totalQuantity = itemView.findViewById(R.id.cart_quantity_view);
        actualTotalQuantity = itemView.findViewById(R.id.actual_quantity_view);
        totalPrice = itemView.findViewById(R.id.cart_price_view);
        actualTotalPrice = itemView.findViewById(R.id.actual_price_view);
    }

}
