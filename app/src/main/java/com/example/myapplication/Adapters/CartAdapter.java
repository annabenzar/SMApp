package com.example.myapplication.Adapters;

import static com.example.myapplication.Helpers.FirebaseHelper.familyDatabase;
import static com.example.myapplication.Helpers.FirebaseHelper.ingredientsDatabase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Models.ListCartModel;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        holder.quantityTextCart.setText(newCartList.getProductTotalQuantity());

        String productQuantityString = newCartList.getProductTotalQuantity();
        final int[] productQuantityInt = {Integer.parseInt(productQuantityString)};

        calculateTotalPrice();
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

                                calculateTotalPrice();
                            }
                        });
            }
        });
        holder.plusbuttonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productQuantityInt[0]++;
                holder.quantityTextCart.setText(String.valueOf(productQuantityInt[0]));
                increaseQuantityAndPrice(newCartList, position);
            }
        });
        holder.minusButtonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productQuantityInt[0] > 1) {
                    productQuantityInt[0]--;
                    holder.quantityTextCart.setText(String.valueOf(productQuantityInt[0]));
                    decreaseQuantityAndPrice(newCartList, position);
                }
            }
        });
    }
    public void increaseQuantityAndPrice(ListCartModel newCartList, int position){
        firestore.collection("AddToCart")
                .document(auth.getCurrentUser().getUid())
                .collection("User")
                .document(newCartList.getFirebaseUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            String productTotalPrice = document.getString("productTotalPrice");
                            String productTotalQuantity = document.getString("productTotalQuantity");
                            int productPriceFromDocInt, productQuantityFromDocInt;
                            productPriceFromDocInt = Integer.parseInt(productTotalPrice);
                            productQuantityFromDocInt = Integer.parseInt(productTotalQuantity);
                            int oneProductPrice = productPriceFromDocInt/productQuantityFromDocInt;

                            //actualizam valorile
                            int totalQuantityInt = productQuantityFromDocInt + 1;
                            int totalPriceInt = productPriceFromDocInt + oneProductPrice;

                            addUpdatedFieldsToFirestore(newCartList, totalQuantityInt,totalPriceInt, position);
                            calculateTotalPrice();
                        }
                    }
                });
    }
    public void decreaseQuantityAndPrice(ListCartModel newCartList,int position){
        firestore.collection("AddToCart")
                .document(auth.getCurrentUser().getUid())
                .collection("User")
                .document(newCartList.getFirebaseUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            String productTotalPrice = document.getString("productTotalPrice");
                            String productTotalQuantity = document.getString("productTotalQuantity");
                            int productPriceFromDocInt, productQuantityFromDocInt;
                            productPriceFromDocInt = Integer.parseInt(productTotalPrice);
                            productQuantityFromDocInt = Integer.parseInt(productTotalQuantity);
                            int oneProductPrice = productPriceFromDocInt/productQuantityFromDocInt;

                            //actualizam valorile
                            int totalQuantityInt = productQuantityFromDocInt - 1;
                            int totalPriceInt = productPriceFromDocInt - oneProductPrice;

                            addUpdatedFieldsToFirestore(newCartList, totalQuantityInt,totalPriceInt, position);
                            calculateTotalPrice();
                        }
                    }
                });
    }
    public void addUpdatedFieldsToFirestore(ListCartModel newCartList, int totalQuantityInt , int totalPriceInt, int position){
        Map<String, Object> updateFields = new HashMap<>();
        updateFields.put("productTotalQuantity", String.valueOf(totalQuantityInt));
        updateFields.put("productTotalPrice", String.valueOf(totalPriceInt));

        firestore.collection("AddToCart")
                .document(auth.getCurrentUser().getUid())
                .collection("User")
                .document(newCartList.getFirebaseUid())
                .update(updateFields)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Handle completion
                        cartList.get(position).setProductTotalQuantity(String.valueOf(totalQuantityInt));
                        cartList.get(position).setProductTotalPrice(String.valueOf(totalPriceInt));
                        notifyItemChanged(position);
                    }
                });

    }
    @Override
    public int getItemCount() {
        return cartList.size();
    }
    private void calculateTotalPrice() {
        totalPrice = 0;
        for (ListCartModel cartItem : cartList) {
            int itemPrice = Integer.parseInt(cartItem.getProductTotalPrice());
            totalPrice += itemPrice;
        }
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount", totalPrice);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
