package com.example.myapplication.Adapters;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;


public class FamilyGroupViewHolder extends RecyclerView.ViewHolder {

    public TextView nameFamilyGroupUser, firstNameFamilyGroupUser, statusFamilyGroupUser;
    public Button deleteFamilyGroupUser;

    public FamilyGroupViewHolder(View contactView) {
        super(contactView);
        initializeViews();
    }
    public void initializeViews(){
        nameFamilyGroupUser = itemView.findViewById(R.id.nameUser_familyGroup_view);
        firstNameFamilyGroupUser = itemView.findViewById(R.id.firstNameUser_familyGroup_view);
        statusFamilyGroupUser = itemView.findViewById(R.id.status_familyGroup_view);

        deleteFamilyGroupUser = itemView.findViewById(R.id.detele_familyGroup_btn);
    }
}
