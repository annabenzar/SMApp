package com.example.myapplication.Adapters;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;

public class PhotoViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;

    public PhotoViewHolder(@NonNull View itemView) {
        super(itemView);
        initializeViews();
    }

    public void initializeViews(){
        imageView = itemView.findViewById(R.id.image_view_upload);
    }
}
