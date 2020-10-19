package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//acces la elementele listei  - preia din choicesList in holder de la o anumita pozitie
//ne ajuta sa afisam lista

public class ListExampleAdapter extends RecyclerView.Adapter<ListExampleViewHolder> {
    private List<ListExampleModel> choicesList = null;
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
    public ListExampleAdapter(List<ListExampleModel> waterGlassesList) {
        this.choicesList = waterGlassesList;
    }

    @Override
    //efectueaza diferite operatii
    public void onBindViewHolder(@NonNull ListExampleViewHolder holder, int position) {
        final ListExampleModel glassModel = choicesList.get(position);
        holder.setValues(glassModel.getName(),glassModel.getFirstname());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        int n=0;
        try {
            n= choicesList.size();
        }catch(NullPointerException ignored){

        }
        return n;
    }
}