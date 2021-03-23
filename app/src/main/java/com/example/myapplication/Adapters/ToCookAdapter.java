package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Models.ListRecipeModel;
import com.example.myapplication.R;
import com.example.myapplication.OneRecipeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static com.example.myapplication.Helpers.FirebaseHelper.favoritesDatabase;

public class ToCookAdapter extends RecyclerView.Adapter<ToCookViewHolder>{

    private Context context;
    private List<ListRecipeModel> toCookList;




    @NonNull
    @Override
    public ToCookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_tocook_list, parent, false);
        ToCookViewHolder viewHolder = new ToCookViewHolder(contactView);
        return viewHolder;
    }

    public ToCookAdapter(List<ListRecipeModel> toCookList) {
        this.toCookList = toCookList;
    }

    @Override
    public void onBindViewHolder(@NonNull ToCookViewHolder holder, int position) {
        final ListRecipeModel newToCookList = toCookList.get(position);

        holder.nameView.setText(newToCookList.getRecipeName());
        holder.timeView.setText(newToCookList.getRecipeTime());
        holder.typeView.setText(newToCookList.getRecipeType());
        //load image
        Glide.with(context).load(newToCookList.getRecipeURL()).into(holder.imageView);

        final String urlToOneRecipe = newToCookList.getRecipeURL();
        final String nameToOneRecipe = newToCookList.getRecipeName();
        final String  timeToOneRecipe = newToCookList.getRecipeTime();
        final String typeOneRecipe = newToCookList.getRecipeType();
        final String ingredientsToOneRecipe = newToCookList.getRecipeIngredients();
        final String prepToOneRecipe = newToCookList.getRecipePrep();
        final String authorOneRecipe = newToCookList.getRecipeAuthor();



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ce se intampla la click pe un cardview
                Intent intent = new Intent(v.getContext() , OneRecipeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url",urlToOneRecipe);
                bundle.putString("name",nameToOneRecipe);
                bundle.putString("time",timeToOneRecipe);
                bundle.putString("type",typeOneRecipe);
                bundle.putString("ingredients",ingredientsToOneRecipe);
                bundle.putString("prep",prepToOneRecipe);
                bundle.putString("author",authorOneRecipe);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ce se intampla la click pe un cardview
                final String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                DatabaseReference favoriteRecipes = favoritesDatabase.child(currentUser);

                favoriteRecipes.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                            //cautare cheie cu ingredientul cautat
                            String recipeRetrieved = String.valueOf(postSnapshot.child("imageName").getValue());

                            if(recipeRetrieved.equals(newToCookList.getRecipeName())){
                                postSnapshot.child("imageAuthor").getRef().removeValue();
                                postSnapshot.child("imageIngredients").getRef().removeValue();
                                postSnapshot.child("imageName").getRef().removeValue();
                                postSnapshot.child("imagePrep").getRef().removeValue();
                                postSnapshot.child("imageTime").getRef().removeValue();
                                postSnapshot.child("imageType").getRef().removeValue();
                                postSnapshot.child("imageURL").getRef().removeValue();
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
        return toCookList.size();
    }
}
