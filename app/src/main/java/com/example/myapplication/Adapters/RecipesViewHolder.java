package com.example.myapplication.Adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;

public class RecipesViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView nameView;
    public TextView timeView;
    public TextView typeView;

    public RecipesViewHolder(@NonNull View itemView) {
        super(itemView);
        initializeViews();
    }

    public void initializeViews(){
        imageView = itemView.findViewById(R.id.image_view_upload);
        nameView = itemView.findViewById(R.id.name_view_upload);
        timeView = itemView.findViewById(R.id.time_view_upload);
        typeView = itemView.findViewById(R.id.type_view_upload);
    }

}
