package com.example.myapplication.ui.ingredients;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Adapters.IngredientsAdapter;
import com.example.myapplication.Models.ListIngredientModel;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.Helpers.FirebaseHelper.ingredientsDatabase;

public class IngredientsFragment extends Fragment {

    RecyclerView recyclerView;
    IngredientsAdapter adapter;
    List<ListIngredientModel> list = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_ingredients, container, false);


        recyclerView = (RecyclerView) root.findViewById(R.id.rv_ingredient_list);
        final String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference oneUserIngredients = ingredientsDatabase.child(currentUser);

        oneUserIngredients.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                list.removeAll(list);

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    //extragere sub forma ListPhotoModel
                    String ingredientRetrieved = String.valueOf(postSnapshot.child("ingredient").getValue());
                    String nameRetrieved = String.valueOf(postSnapshot.child("nameRecipe").getValue());

                    ListIngredientModel listIngredientModel = new ListIngredientModel(ingredientRetrieved,nameRetrieved);

                    list.add(listIngredientModel);
                }


                setRecyclerView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return root;
    }

    public void setRecyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new IngredientsAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}
