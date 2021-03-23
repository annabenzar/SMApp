package com.example.myapplication.Adapters;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;

public class ToBuyViewHolder extends RecyclerView.ViewHolder {
    public TextView ingredient;
    public TextView nameRecipe;
    public Button deleteIngredientButton;

    public ToBuyViewHolder(@NonNull View itemView){
        super(itemView);
        initializeViews();
    }

    public void initializeViews(){
        ingredient = itemView.findViewById(R.id.ingredient_view);
        nameRecipe = itemView.findViewById(R.id.nameRecipe_view);
        deleteIngredientButton =itemView.findViewById(R.id.ingredient_btnDelete);
    }

}
