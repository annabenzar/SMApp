package com.example.myapplication.Adapters;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class RequestsViewHolder extends RecyclerView.ViewHolder {

    public TextView userNameRequest, userFirstNameRequest;
    public Button acceptButton;
    public ImageView profilePicRequest;

    public RequestsViewHolder(@NonNull View itemView) {
        super(itemView);
        initializeViews();
    }

    public void initializeViews(){
        profilePicRequest = itemView.findViewById(R.id.profilePic_requests);
        userNameRequest = itemView.findViewById(R.id.nameUserRequest_view);
        userFirstNameRequest = itemView.findViewById(R.id.firstNameUserRequest_view);
        acceptButton = itemView.findViewById(R.id.button_accept);
    }
}
