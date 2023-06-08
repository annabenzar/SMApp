package com.example.myapplication.ui.profile;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.AddressAdapter;
import com.example.myapplication.Models.ListAddressModel;
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


public class AddressActivity extends AppCompatActivity implements AddressAdapter.SelectedAddress{
    RecyclerView recyclerView;
    AddressAdapter adapter;
    List<ListAddressModel> list;
    FirebaseFirestore database;
    FirebaseAuth auth;
    Button addAddressButton, payButton;
    String mAddress = "";
    int totalBill;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_address);
        recyclerView = (RecyclerView) findViewById(R.id.rv_addresses_list);
        addAddressButton =findViewById(R.id.add_adress_button);
        payButton =findViewById(R.id.got_to_payment_button);
        totalBill = getIntent().getIntExtra("totalPrice",0);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new AddressAdapter(getApplicationContext(),list,this);
        recyclerView.setAdapter(adapter);
        showMyAdresses();
        setupListeners();
    }
    public void showMyAdresses() {
        database.collection("CurrentUserAddress").document(auth.getCurrentUser().getUid())
                .collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(DocumentSnapshot doc: task.getResult().getDocuments()){
                                ListAddressModel newList = doc.toObject(ListAddressModel.class);
                                list.add(newList);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }


    public void setupListeners() {
        addAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddAdressActivity.class);
                startActivity(intent);
            }
        });
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PaymentActivity.class);
                intent.putExtra("totalPrice", totalBill);
                startActivity(intent);
            }
        });
    }

    @Override
    public void setAddress(String address) {
        mAddress = address;
    }
}
