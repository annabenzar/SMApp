package com.example.myapplication.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;

public class ShopViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView nameView;
    public TextView priceView;
    public TextView conditionView;

    public ShopViewHolder(@NonNull View itemView) {
        super(itemView);
        initializeViews();
    }

    public void initializeViews(){
        imageView = itemView.findViewById(R.id.shop_image_view);
        nameView = itemView.findViewById(R.id.shop_name_view);
        priceView = itemView.findViewById(R.id.shop_price_view);
        conditionView = itemView.findViewById(R.id.shop_condition_view);
    }

}
