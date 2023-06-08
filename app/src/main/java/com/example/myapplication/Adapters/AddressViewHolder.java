package com.example.myapplication.Adapters;

import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;

public class AddressViewHolder extends RecyclerView.ViewHolder {
    public TextView adressText;
    public RadioButton  radioButton;

    public AddressViewHolder(@NonNull View itemView) {
        super(itemView);
        initializeViews();
    }

    public void initializeViews(){
        adressText = itemView.findViewById(R.id.address_add);
        radioButton = itemView.findViewById(R.id.select_address);
    }

}
