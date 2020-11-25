package com.example.myapplication.firstActivities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ListExampleViewHolder;
import com.example.myapplication.R;

import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<ListExampleViewHolder> {
    private List<TestEntity> testEntityList = null;
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
        this.testEntityList = waterGlassesList;
    }

    @Override
    //efectueaza diferite operatii
    public void onBindViewHolder(@NonNull ListExampleViewHolder holder, final int position) {
        final TestEntity glassModel = testEntityList.get(position);
        //holder.setValues(glassModel.getName(),glassModel.getFirstname());
    }

    @Override
    public int getItemCount() {
        int n = 0;
        try {
            n = testEntityList.size();
        } catch (NullPointerException ignored) {

        }
        return n;
    }
}
