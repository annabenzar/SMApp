package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//acces la elementele listei  - preia din choicesList in holder de la o anumita pozitie
//ne ajuta sa afisam lista

public class RoomAdapter extends RecyclerView.Adapter<ListExampleViewHolder> {
    private List<TestEntity> entityList = null;
    private Context context; // up-calls(launching activities,braodcasting,receiving intents)

    @NonNull
    @Override
    //acelasi la toate
    //pune viewholder in pozitia in care trebuie
    //instantiaza xml pentru viewholder si viewholderul
    public ListExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_example_list, parent, false);
        //ajuta la afisarea elementelor dintr-un rand
        ListExampleViewHolder viewHolder = new ListExampleViewHolder(contactView);
        return viewHolder;
    }

    //constructor
    public RoomAdapter(List<TestEntity> waterGlassesList) {
        this.entityList = waterGlassesList;
    }

    @Override
    //efectueaza diferite operatii
    public void onBindViewHolder(@NonNull ListExampleViewHolder holder, final int position) {
        final TestEntity glassModel = entityList.get(position);
        holder.setValues(glassModel.getName(),glassModel.getFirstname());
    }

    @Override
    public int getItemCount() {
        int n=0;
        try {
            n= entityList.size();
        }catch(NullPointerException ignored){

        }
        return n;
    }
}