package com.example.myapplication;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.FamilyGroupAdapter;
import com.example.myapplication.Models.ListFamilyGroupUserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.Helpers.FirebaseHelper.familyDatabase;
import static com.example.myapplication.Helpers.FirebaseHelper.usersDatabase;

public class FamilyGroupActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FamilyGroupAdapter adapter;
    //de facut adapter cu ViewHolder
    List<ListFamilyGroupUserModel> list = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_group);

        //setare recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.rv_familyGroup_list);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        showAllFamilyMembers();
    }

    public void showAllFamilyMembers() {

        final FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser(); //user-ul curent conectat
        final int[] connectedUserFound = {0};

        //CAUTARE IN FAMILY TABLE
        familyDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                //parcurgere familii
                for (DataSnapshot postsnapshot : snapshot.getChildren()) {
                    //parcurgere fiecare familie in parte
                    for (DataSnapshot newpostsnapshot : postsnapshot.getChildren()) {
                        String databaseUser = newpostsnapshot.getKey();
                        if (databaseUser.equals(mUser.getUid())) {
                            connectedUserFound[0] = 1;//daca m-am gasit pe mine, user-ul conectat
                            //mai parcurg o data familia respectiva si caut userii in tabela de userii
                            for (DataSnapshot anothersnapshot : postsnapshot.getChildren()) {
                                //preiau statusul la fiecare si id-ul pentru cautare in userTable
                                final String idForUserTable = anothersnapshot.getKey();
                                final String status = String.valueOf(anothersnapshot.child("status").getValue());

                                //parcurg tabela de useri sa preiau numele si prenumele in stringuri
                                usersDatabase.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                        for (DataSnapshot usersSnapshot : snapshot2.getChildren()) {
                                            String userKey = usersSnapshot.getKey();

                                            if (userKey.equals(idForUserTable)) {
                                                String nameRetrieved = String.valueOf(usersSnapshot.child("name").getValue());
                                                String firstNameRetrieved = String.valueOf(usersSnapshot.child("firstname").getValue());
                                                String idRetrieved = String.valueOf(usersSnapshot.child("id").getValue());
                                                ListFamilyGroupUserModel listFamilyGroupUserModel = new ListFamilyGroupUserModel(nameRetrieved, firstNameRetrieved, status, idRetrieved);
                                                list.add(listFamilyGroupUserModel);
                                            }
                                            adapter = new FamilyGroupAdapter(list);
                                            recyclerView.setAdapter(adapter);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                    }
                                });
                            }
                        }
                        //daca m-am gasit intr-o familie, nu mai caut in acea familie si nici in alta
                        if (connectedUserFound[0] == 1) {
                            break;
                        }
                    }
                    //daca am parcurs familia si user-ul conectat era in ea, ma opresc
                    //din parcurs restu famililor
                    if (connectedUserFound[0] == 1) {
                        break;
                    }
                }
                if (connectedUserFound[0] == 0) {
                    recyclerView.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}
