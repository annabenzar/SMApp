package com.example.myapplication.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class UsersViewHolder extends RecyclerView.ViewHolder {

    public TextView nameUserView, firstNameUserView,addFamButton;

    public UsersViewHolder(@NonNull View itemView) {
        super(itemView);
        initializeViews();
    }
    public void initializeViews(){
        nameUserView = itemView.findViewById(R.id.nameUser_view);
        firstNameUserView = itemView.findViewById(R.id.firstNameUser_view);
        addFamButton = itemView.findViewById(R.id.button_addFam);
    }
}
