package com.example.myapplication.ui.toShop;

import static com.example.myapplication.Helpers.FirebaseHelper.fruitsDatabase;
import static com.example.myapplication.Helpers.FirebaseHelper.vegetablesDatabase;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.ShopAdapter;
import com.example.myapplication.Models.ListShopModel;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ToShopFragment extends Fragment {
    RecyclerView recyclerView;
    ShopAdapter adapter;
    List<ListShopModel> list = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_shop, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.rv_shop_list);
        list = new ArrayList<>();
        setupCardViewListeners(root);
        setRecyclerView();
        return root;
    }
    public void setupCardViewListeners(View root) {
        CardView fruitsCardView = root.findViewById(R.id.card_fruits);
        fruitsCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFruitsList(v);
            }
        });
        CardView vegetablesCardView = root.findViewById(R.id.card_vegetables);
        vegetablesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVegetablesList();
            }
        });
    }
    public void showFruitsList(View view) {
        fruitsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    String nameRetrieved = String.valueOf(postSnapshot.child("fruitName").getValue());
                    String priceRetrieved = String.valueOf(postSnapshot.child("fruitPrice").getValue());
                    String conditionRetrieved = String.valueOf(postSnapshot.child("fruitCondition").getValue());
                    String imageRetrieved = String.valueOf(postSnapshot.child("image").getValue());

                    ListShopModel listphoto = new ListShopModel(nameRetrieved, priceRetrieved, conditionRetrieved, imageRetrieved);
                    list.add(listphoto);
                }
                setRecyclerView();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors
            }
        });
    }
    public void showVegetablesList() {
        vegetablesDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    String nameRetrieved = String.valueOf(postSnapshot.child("vegetableName").getValue());
                    String priceRetrieved = String.valueOf(postSnapshot.child("vegetablePrice").getValue());
                    String conditionRetrieved = String.valueOf(postSnapshot.child("vegetableCondition").getValue());
                    String imageRetrieved = String.valueOf(postSnapshot.child("image").getValue());

                    ListShopModel listphoto = new ListShopModel(nameRetrieved, priceRetrieved, conditionRetrieved, imageRetrieved);
                    list.add(listphoto);
                }
                setRecyclerView();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors
            }
        });
    }
    public void setRecyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ShopAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}
