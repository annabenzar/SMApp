package com.example.myapplication.ui.profile;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.Adapters.RequestsAdapter;
import com.example.myapplication.Adapters.UsersAdapter;
import com.example.myapplication.Models.UserEntity;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.Helpers.FirebaseHelper.requestsDatabase;
import static com.example.myapplication.Helpers.FirebaseHelper.usersDatabase;

public class RequestsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RequestsAdapter adapter;
    List<UserEntity> list = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        //setare recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.rv_requests_list);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        showAllRequests();
    }

    public void showAllRequests(){

        requestsDatabase.addValueEventListener(new ValueEventListener() {
            final FirebaseUser mUser= FirebaseAuth.getInstance().getCurrentUser(); //user-ul conectat la momentul respectiv
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    final String keySender = postSnapshot.getKey();
                    //daca am gasit cereri in tabel
                    for(DataSnapshot newSnapshot : postSnapshot.getChildren()){
                        String keyReceiver = newSnapshot.getKey();
                        if(mUser.getUid().equals(keyReceiver)){
                            usersDatabase.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                    for(DataSnapshot usersSnapshot: snapshot2.getChildren()){
                                        String newKey = usersSnapshot.getKey();
                                        if(newKey.equals(keySender)){
                                            UserEntity userEntity2 = usersSnapshot.getValue(UserEntity.class); //extrag tot despre cel ce mi-a trimis cererea
                                            // si il adaug in lista de request-uri
                                            list.add(userEntity2);
                                            //setez adapter si recyclerView
                                            adapter = new RequestsAdapter(list);
                                            recyclerView.setAdapter(adapter);
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) { }
                            });
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
