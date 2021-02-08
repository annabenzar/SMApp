package com.example.myapplication.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Models.ListIngredientModel;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static android.content.ContentValues.TAG;
import static com.example.myapplication.Helpers.FirebaseHelper.ingredientsDatabase;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsViewHolder> {
    private Context context;
    private List<ListIngredientModel> ingredientModelList;



    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_ingredients_list, parent, false);
        IngredientsViewHolder viewHolder = new IngredientsViewHolder(contactView);
        return viewHolder;
    }

    public IngredientsAdapter(List<ListIngredientModel> ingredientModelList) {
        this.ingredientModelList = ingredientModelList;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        final ListIngredientModel listIngredientModel = ingredientModelList.get(position);

        holder.ingredient.setText(listIngredientModel.getIngredient());
        holder.nameRecipe.setText(listIngredientModel.getNameRecipe());

        holder.deleteIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ce se intampla la click pe un cardview

                final String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference oneUserIngredients = ingredientsDatabase.child(currentUser);

                oneUserIngredients.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                            //cautare cheie cu ingredientul cautat
                            String ingredientRetrieved = String.valueOf(postSnapshot.child("ingredient").getValue());

                            if(ingredientRetrieved.equals(listIngredientModel.getIngredient())){
                                postSnapshot.child("ingredient").getRef().removeValue();
                                postSnapshot.child("nameRecipe").getRef().removeValue();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredientModelList.size();
    }

}
