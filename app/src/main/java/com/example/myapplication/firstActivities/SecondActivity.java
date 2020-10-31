package com.example.myapplication.firstActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    //pentru afisare
    private RecyclerView exampleListRv;
    private ListExampleAdapter listExampleAdapter;

    //pentru adaugare in lista
    private List<ListExampleModel> exampleModelList = new ArrayList<>();
    private Button addButton;
    private EditText et_name,et_firstname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initializeViews();
        getIntent();
        setOnClickListeners();
        setRecyclerView();
    }

    private void initializeViews() {
        et_name = findViewById(R.id.et_name);
        et_firstname = findViewById(R.id.et_firstname);
        addButton = findViewById(R.id.addButton);
        exampleListRv = findViewById(R.id.rv_secondary_list);
        Toast.makeText(SecondActivity.this, "Short press on item to delete it!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(SecondActivity.this, "Enter LastName!!!", Toast.LENGTH_SHORT).show();
                    return;
                }if(firstname.isEmpty()){
                    Toast.makeText(SecondActivity.this, "Enter FirstName !!!", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    //adaugare in lista model
                    exampleModelList.add(new ListExampleModel(name, firstname));
                    listExampleAdapter.notifyDataSetChanged();
                }
            }
        });
    }
    //afisare lista cu recyclerview
    private void setRecyclerView() {
        //examplemodellist pasata ca parametru adapterului
        listExampleAdapter = new ListExampleAdapter(exampleModelList);

        //se seteaza layoutmanager si adapter-ul pentru recyclerview
        exampleListRv.setLayoutManager(new LinearLayoutManager(this));
        exampleListRv.setAdapter(listExampleAdapter);
    }
    public void toThirdActivity(View view){
        Intent intent = new Intent(this, ThirdActivity.class);
        startActivity(intent);
    }
}