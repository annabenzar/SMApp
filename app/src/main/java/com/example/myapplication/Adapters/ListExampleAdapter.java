package com.example.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.ListExampleModel;
import com.example.myapplication.R;

import java.util.List;

public class ListExampleAdapter extends RecyclerView.Adapter<ListExampleViewHolder> {
    private List<ListExampleModel> exampleList;
    private Context context;

    @NonNull
    @Override
    public ListExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_example_list, parent, false);
        ListExampleViewHolder viewHolder = new ListExampleViewHolder(contactView);
        return viewHolder;
    }

    public ListExampleAdapter(List<ListExampleModel> exampleList) {
        this.exampleList = exampleList;
    }

    @Override
    public void onBindViewHolder(@NonNull ListExampleViewHolder holder, int position) {
        final ListExampleModel listModel = exampleList.get(position);

        holder.nameTv.setText(listModel.getName());
        holder.firstnameTv.setText(listModel.getFirstname());

        //holder.setValues(listModel.getFirstname(), listModel.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ce se intampla la click pe un cardvie
            }
        });
    }

    @Override
    public int getItemCount() {
        return exampleList.size();
    }
}