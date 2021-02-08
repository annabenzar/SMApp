package com.example.myapplication.Adapters;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class FavoritesViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView nameView;
    public TextView timeView;
    public TextView typeView;
    public Button button;

    public FavoritesViewHolder(@NonNull View itemView) {
        super(itemView);
        initializeViews();
    }

    public void initializeViews(){
        imageView = itemView.findViewById(R.id.image_view_favorites);
        nameView = itemView.findViewById(R.id.name_view_favorites);
        timeView = itemView.findViewById(R.id.time_view_favorites);
        typeView = itemView.findViewById(R.id.type_view_favorites);
        button = itemView.findViewById(R.id.btnDelete_view_favorites);

    }

}
