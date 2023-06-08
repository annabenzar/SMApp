package com.example.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.ListAddressModel;
import com.example.myapplication.R;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressViewHolder> {
    private Context context;
    private List<ListAddressModel> adressList;
    SelectedAddress selectedAddress;
    private RadioButton selectedRadioButton;
    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_address_list, parent, false);
        AddressViewHolder viewHolder = new AddressViewHolder(contactView);
        return viewHolder;
    }
    public AddressAdapter(Context context, List<ListAddressModel> adressList, SelectedAddress selectedAddress) {
        this.context = context;
        this.adressList = adressList;
        this.selectedAddress = selectedAddress;
    }
    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        ListAddressModel newAdressList = adressList.get(position);
        holder.adressText.setText(newAdressList.getUserAddress());
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(ListAddressModel address:adressList){
                    address.setSelected(false);
                }
                adressList.get(position).setSelected(true);
                if(selectedRadioButton!=null){
                    selectedRadioButton.setChecked(false);
                }
                selectedRadioButton = (RadioButton) v;
                selectedRadioButton.setChecked(true);
                selectedAddress.setAddress(adressList.get(position).getUserAddress());
            }
        });
    }
    @Override
    public int getItemCount() {
        return adressList.size();
    }

    public interface SelectedAddress{
        void setAddress(String address);

    }
}
