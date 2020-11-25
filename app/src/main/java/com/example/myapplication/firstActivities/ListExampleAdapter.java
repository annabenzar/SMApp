package com.example.myapplication.firstActivities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ListExampleViewHolder;
import com.example.myapplication.R;

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
    public void onBindViewHolder(@NonNull ListExampleViewHolder holder, final int position) {
        final ListExampleModel glassModel = choicesList.get(position);
        //holder.setValues(glassModel.getName(),glassModel.getFirstname());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder a_builder = new AlertDialog.Builder(context);
                a_builder.setMessage("Delete the item?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //sterge el
                                Toast.makeText(context,"Item Deleted",Toast.LENGTH_LONG).show();
                                choicesList.remove(position);
                                notifyItemRemoved(position);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = a_builder.create();
                alert.setTitle("Dialog");
                alert.show();

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