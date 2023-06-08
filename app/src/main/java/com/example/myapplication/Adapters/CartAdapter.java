package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Models.ListCartModel;
import com.example.myapplication.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
    private Context context;
    private List<ListCartModel> cartList;
    private int totalPrice =0;
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_cart_list, parent, false);
        CartViewHolder viewHolder = new CartViewHolder(contactView);
        return viewHolder;
    }
    public CartAdapter(List<ListCartModel> cartList) {
        this.cartList = cartList;
    }
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        ListCartModel newCartList = cartList.get(position);
        Glide.with(context).load(newCartList.getProductURL()).into(holder.imageView);
        holder.nameView.setText(newCartList.getProductName());
        holder.actualDateView.setText(newCartList.getCurrentDate());
        holder.actualTimeView.setText(newCartList.getCurrentTime());
        holder.actualTotalQuantity.setText(newCartList.getProductTotalQuantity()+" kg");
        holder.actualTotalPrice.setText(newCartList.getProductTotalPrice()+" $");

        String totalPriceString = newCartList.getProductTotalPrice();
        int currentPrice  = Integer.parseInt(totalPriceString);
        totalPrice = totalPrice + currentPrice;

        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount",totalPrice);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
    @Override
    public int getItemCount() {
        return cartList.size();
    }
}
