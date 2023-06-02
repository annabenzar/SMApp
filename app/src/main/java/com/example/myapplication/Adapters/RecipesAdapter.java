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

import java.util.List;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesViewHolder> {
    private Context context;
    private List<ListRecipeModel> recipeList;

    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_recipe_list, parent, false);
        RecipesViewHolder viewHolder = new RecipesViewHolder(contactView);
        return viewHolder;
    }

    public RecipesAdapter(List<ListRecipeModel> recipeList) {
        this.recipeList = recipeList;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder holder, int position) {

        ListRecipeModel newRecipeList = recipeList.get(position);

        holder.nameView.setText(newRecipeList.getRecipeName());
        holder.timeView.setText(newRecipeList.getRecipeTime());
        holder.typeView.setText(newRecipeList.getRecipeType());
        //load image
        Glide.with(context).load(newRecipeList.getRecipeURL()).into(holder.imageView);

        final String urlToOneRecipe = newRecipeList.getRecipeURL();
        final String nameToOneRecipe = newRecipeList.getRecipeName();
        final String timeToOneRecipe = newRecipeList.getRecipeTime();
        final String typeOneRecipe = newRecipeList.getRecipeType();
        final String ingredientsToOneRecipe = newRecipeList.getRecipeIngredients();
        final String prepToOneRecipe = newRecipeList.getRecipePrep();
        final String authorOneRecipe = newRecipeList.getRecipeAuthor();



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
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }
}
