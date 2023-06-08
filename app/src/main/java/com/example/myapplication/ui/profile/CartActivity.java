package com.example.myapplication.ui.profile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.CartAdapter;
import com.example.myapplication.Models.ListCartModel;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class CartActivity extends AppCompatActivity {
    TextView totalAmount;
    RecyclerView recyclerView;
    CartAdapter adapter;
    List<ListCartModel> list;
    FirebaseFirestore database;
    FirebaseAuth auth;
    int totalBill;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_cart);
        totalAmount = findViewById(R.id.actual_total_price_view);
        recyclerView = (RecyclerView) findViewById(R.id.rv_cart_list);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("MyTotalAmount"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new CartAdapter(list);
        recyclerView.setAdapter(adapter);
        showMyCart();
        setupListeners();
    }
    public void showMyCart() {
        database.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot doc: task.getResult().getDocuments()){
                                String productURL, productName, currentDate, currentTime, productTotalQuantity, productTotalPrice,firebaseUid;
                                productURL = doc.getString("productURL");
                                productName = doc.getString("productName");
                                currentDate = doc.getString("currentDate");
                                currentTime = doc.getString("currentTime");
                                productTotalQuantity = doc.getString("productTotalQuantity");
                                productTotalPrice = doc.getString("productTotalPrice");
                                firebaseUid = doc.getId();
                                ListCartModel newList = new ListCartModel(productURL,productName,currentDate,currentTime,productTotalQuantity,productTotalPrice,firebaseUid);
                                list.add(newList);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
             totalBill = intent.getIntExtra("totalAmount",0);
             totalAmount.setText(totalBill+" $");
        }
    };
    public void setupListeners() {
        Button buyNowButton = findViewById(R.id.buy_now_button);
        buyNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddressActivity.class);
                intent.putExtra("totalPrice", totalBill);
                startActivity(intent);
            }
        });

    }

}
