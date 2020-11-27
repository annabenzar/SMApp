package com.example.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Models.ListPhotoModel;
import com.example.myapplication.R;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoViewHolder> {
    private Context context;
    private List<ListPhotoModel> photoList;

    public PhotoAdapter(List<ListPhotoModel> photoList) {
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_image_list, parent, false);
        PhotoViewHolder viewHolder = new PhotoViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        ListPhotoModel newPhotoList = photoList.get(position);
        //load image
        Glide.with(context).load(newPhotoList.getImageURL()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }
}
