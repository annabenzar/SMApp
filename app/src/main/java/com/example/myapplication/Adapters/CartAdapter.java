package com.example.myapplication.Adapters;

import static com.example.myapplication.Helpers.FirebaseHelper.familyDatabase;
import static com.example.myapplication.Helpers.FirebaseHelper.ingredientsDatabase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Models.ListCartModel;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
    private Context context;
    private List<ListCartModel> cartList;
    private int totalPrice =0;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_cart_list, parent, false);
        CartViewHolder viewHolder = new CartViewHolder(contactView);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
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
        holder.deletePrduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("AddToCart")
                        .document(auth.getCurrentUser().getUid())
                        .collection("User")
                        .document(newCartList.getFirebaseUid())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                cartList.remove(position);
                                notifyItemRemoved(position);

                                String totalPriceString = newCartList.getProductTotalPrice();
                                int currentPrice  = Integer.parseInt(totalPriceString);
                                totalPrice = totalPrice - currentPrice;

                                Intent intent = new Intent("MyTotalAmount");
                                intent.putExtra("totalAmount", totalPrice);
                                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            }
                        });
            }
        });
    }
    @Override
    public int getItemCount() {
        return cartList.size();
    }
}
