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
import com.example.myapplication.Models.ListPhotoModel;
import com.example.myapplication.R;
import com.example.myapplication.ui.home.OneRecipeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static com.example.myapplication.Helpers.FirebaseHelper.favoritesDatabase;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesViewHolder>{

    private Context context;
    private List<ListPhotoModel> photoList;




    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_favorites_list, parent, false);
        FavoritesViewHolder viewHolder = new FavoritesViewHolder(contactView);
        return viewHolder;
    }

    public FavoritesAdapter(List<ListPhotoModel> photoList) {
        this.photoList = photoList;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {
        final ListPhotoModel newPhotoList = photoList.get(position);

        holder.nameView.setText(newPhotoList.getImageName());
        holder.timeView.setText(newPhotoList.getImageTime());
        holder.typeView.setText(newPhotoList.getImageType());
        //load image
        Glide.with(context).load(newPhotoList.getImageURL()).into(holder.imageView);

        final String urlToOneRecipe = newPhotoList.getImageURL();
        final String nameToOneRecipe = newPhotoList.getImageName();
        final String  timeToOneRecipe = newPhotoList.getImageTime();
        final String typeOneRecipe = newPhotoList.getImageType();
        final String ingredientsToOneRecipe = newPhotoList.getImageIngredients();
        final String prepToOneRecipe = newPhotoList.getImagePrep();
        final String authorOneRecipe = newPhotoList.getImageAuthor();



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

                            if(recipeRetrieved.equals(newPhotoList.getImageName())){
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
        return photoList.size();
    }
}
