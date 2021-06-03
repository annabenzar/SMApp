package com.example.myapplication.Adapters;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class UsersViewHolder extends RecyclerView.ViewHolder {

    public TextView nameUserView, firstNameUserView,alreadFam, userInOtherGroup;
    public Button addFamButton,acceptFamButton, cancelFamButton;
    public ImageView profilePicImage;

    public UsersViewHolder(@NonNull View itemView) {
        super(itemView);
        initializeViews();
    }
    public void initializeViews(){
        profilePicImage = itemView.findViewById(R.id.profilePic_view_adapter);

        nameUserView = itemView.findViewById(R.id.nameUser_view);
        firstNameUserView = itemView.findViewById(R.id.firstNameUser_view);
        alreadFam = itemView.findViewById(R.id.alreadyFam_view);
        userInOtherGroup = itemView.findViewById(R.id.alreadyInOtherGroup_view);

        addFamButton = itemView.findViewById(R.id.button_addFam);
        acceptFamButton = itemView.findViewById(R.id.button_search_accept);
        cancelFamButton = itemView.findViewById(R.id.button_cancelFam);
    }
}
