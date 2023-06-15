package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Models.ListShopModel;
import com.example.myapplication.OneProductActivity;
import com.example.myapplication.R;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopViewHolder> {
    private Context context;
    private List<ListShopModel> shopList;
    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_shop_list, parent, false);
        ShopViewHolder viewHolder = new ShopViewHolder(contactView);
        return viewHolder;
    }
    public ShopAdapter(List<ListShopModel> shopList) {
        this.shopList = shopList;
    }
    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {

        ListShopModel newShopList = shopList.get(position);

        holder.nameView.setText(newShopList.getProductName());
        holder.priceView.setText(newShopList.getProductPrice());
        holder.conditionView.setText(newShopList.getProductCondition());
        //load image
        Glide.with(context).load(newShopList.getProductURL()).into(holder.imageView);

        final String productURL = newShopList.getProductURL();
        final String productName = newShopList.getProductName();
        final String productPrice = newShopList.getProductPrice();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), OneProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", productURL);
                bundle.putString("name", productName);
                bundle.putString("price", productPrice);

                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });
    }
    @Override
    public int getItemCount() {
        return shopList.size();
    }
}
