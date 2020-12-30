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

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoViewHolder> {
    private Context context;
    private List<ListPhotoModel> photoList;



    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_image_list, parent, false);
        PhotoViewHolder viewHolder = new PhotoViewHolder(contactView);
        return viewHolder;
    }

    public PhotoAdapter(List<ListPhotoModel> photoList) {
        this.photoList = photoList;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        ListPhotoModel newPhotoList = photoList.get(position);

        holder.nameView.setText(newPhotoList.getImageName());
        holder.timeView.setText(newPhotoList.getImageTime());
        holder.typeView.setText(newPhotoList.getImageType());
        //load image
        Glide.with(context).load(newPhotoList.getImageURL()).into(holder.imageView);

        final String urlToOneRecipe = newPhotoList.getImageURL();
        final String nameToOneRecipe = newPhotoList.getImageName();
        final String  timeToOneRecipe = newPhotoList.getImageTime();
        final String ingredientsToOneRecipe = newPhotoList.getImageIngredients();
        final String prepToOneRecipe = newPhotoList.getImagePrep();



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ce se intampla la click pe un cardview
                Intent intent = new Intent(v.getContext() , OneRecipeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url",urlToOneRecipe);
                bundle.putString("name",nameToOneRecipe);
                bundle.putString("time",timeToOneRecipe);
                bundle.putString("ingredients",ingredientsToOneRecipe);
                bundle.putString("prep",prepToOneRecipe);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }
}
