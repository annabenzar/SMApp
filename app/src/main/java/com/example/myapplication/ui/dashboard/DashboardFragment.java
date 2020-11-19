package com.example.myapplication.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.firstActivities.ListExampleAdapter;
import com.example.myapplication.firstActivities.ListExampleModel;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private RecyclerView exampleListRv;
    private ListExampleAdapter listExampleAdapter;
    private List<ListExampleModel> exampleModelList = new ArrayList<>();
    private Button addButton;
    private EditText et_name,et_firstname;


    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        et_name = root.findViewById(R.id.et_name);
        et_firstname = root.findViewById(R.id.et_firstname);
        addButton = root.findViewById(R.id.addButton);
        exampleListRv = root.findViewById(R.id.rv_secondary_list);
        //Toast.makeText(getContext(), "Short press on item to delete it!", Toast.LENGTH_SHORT).show();

        setOnClickListeners();
        setRecyclerView();
        return root;
    }
        private void setOnClickListeners() {
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //AICI SE EXECUTA CODUL DUPA CLICK
                    String name, firstname;
                    //preluare din EditText
                    name = et_name.getText().toString();
                    firstname = et_firstname.getText().toString();
                    //verificare campuri goale
                    if (name.isEmpty()) {
                        Toast.makeText(getContext(), "Enter LastName!!!", Toast.LENGTH_SHORT).show();
                        return;
                    }if(firstname.isEmpty()){
                        Toast.makeText(getContext(), "Enter FirstName !!!", Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        //adaugare in lista model
                        exampleModelList.add(new ListExampleModel(name, firstname));
                        listExampleAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

    private void setRecyclerView() {
        //examplemodellist pasata ca parametru adapterului
        listExampleAdapter = new ListExampleAdapter(exampleModelList);

        //se seteaza layoutmanager si adapter-ul pentru recyclerview
        exampleListRv.setLayoutManager(new LinearLayoutManager(getContext()));
        exampleListRv.setAdapter(listExampleAdapter);
    }

    }
