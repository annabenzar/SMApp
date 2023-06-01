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
import com.example.myapplication.Models.ListShopModel;
import com.example.myapplication.R;
import com.example.myapplication.OneRecipeActivity;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopViewHolder> {
    private Context context;
    private List<ListShopModel> shopList;

    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_shop_list, parent, false);
        ShopViewHolder viewHolder = new ShopViewHolder(contactView);
        return viewHolder;
    }

    public ShopAdapter(List<ListShopModel> shopList) {
        this.shopList = shopList;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {

        ListShopModel newShopList = shopList.get(position);

        holder.nameView.setText(newShopList.getProductName());
        holder.priceView.setText(newShopList.getProductPrice());
        holder.conditionView.setText(newShopList.getProductCondition());
        //load image
        Glide.with(context).load(newShopList.getProductURL()).into(holder.imageView);

//        final String urlToOneRecipe = newRecipeList.getRecipeURL();
//        final String nameToOneRecipe = newRecipeList.getRecipeName();
//        final String  timeToOneRecipe = newRecipeList.getRecipeTime();
//        final String typeOneRecipe = newRecipeList.getRecipeType();
//        final String ingredientsToOneRecipe = newRecipeList.getRecipeIngredients();
//        final String prepToOneRecipe = newRecipeList.getRecipePrep();
//        final String authorOneRecipe = newRecipeList.getRecipeAuthor();



//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //ce se intampla la click pe un cardview
//                Intent intent = new Intent(v.getContext() , OneRecipeActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("url",urlToOneRecipe);
//                bundle.putString("name",nameToOneRecipe);
//                bundle.putString("time",timeToOneRecipe);
//                bundle.putString("type",typeOneRecipe);
//                bundle.putString("ingredients",ingredientsToOneRecipe);
//                bundle.putString("prep",prepToOneRecipe);
//                bundle.putString("author",authorOneRecipe);
//                intent.putExtras(bundle);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }
}
