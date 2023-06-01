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
        imageView = itemView.findViewById(R.id.image_view_upload);
        nameView = itemView.findViewById(R.id.name_view_upload);
        priceView = itemView.findViewById(R.id.price_view_upload);
        conditionView = itemView.findViewById(R.id.condition_view_upload);
    }

}
