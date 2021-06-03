package com.example.myapplication.ui.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.RecipesAdapter;
import com.example.myapplication.Models.ListRecipeModel;
import com.example.myapplication.Models.UserEntity;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.Helpers.FirebaseHelper.recipeDatabase;
import static com.example.myapplication.Helpers.FirebaseHelper.requestsDatabase;
import static com.example.myapplication.Helpers.FirebaseHelper.usersDatabase;

public class RecipesFragment extends Fragment {

    RecyclerView recyclerView;
    RecipesAdapter adapter;
    List<ListRecipeModel> list = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_recipes, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.rv_recipes_list);

        recipeDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                list.removeAll(list);

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    //extragere sub forma ListRecipeModel
                    String nameRetrieved = String.valueOf(postSnapshot.child("recipeName").getValue());
                    String timeRetrieved = String.valueOf(postSnapshot.child("recipeTime").getValue());
                    String typeRetreived = String.valueOf(postSnapshot.child("recipeType").getValue());
                    String imageRetreived = String.valueOf(postSnapshot.child("recipeURL").getValue());
                    String ingredientsRetrieved = String.valueOf(postSnapshot.child("recipeIngredients").getValue());
                    String prepRetrieved = String.valueOf(postSnapshot.child("recipePrep").getValue());
                    String authorRetrieved = String.valueOf(postSnapshot.child("recipeAuthor").getValue());

                    ListRecipeModel listphoto = new ListRecipeModel(imageRetreived,nameRetrieved,timeRetrieved,typeRetreived,ingredientsRetrieved,prepRetrieved,authorRetrieved);

                    list.add(listphoto);
                }
                setRecyclerView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        requestsDatabase.addValueEventListener(new ValueEventListener() {
            final FirebaseUser mUser= FirebaseAuth.getInstance().getCurrentUser();
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    final String keySender = postSnapshot.getKey();
                    //Toast.makeText(context, "Datasnapshot keys"+key, Toast.LENGTH_SHORT).show();
                    //daca am cereri trimise
                    for(DataSnapshot newSnapshot : postSnapshot.getChildren()){
                            String keyReceiver = newSnapshot.getKey();
                            if(mUser.getUid().equals(keyReceiver)){
                                usersDatabase.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                        for(DataSnapshot usersSnapshot: snapshot2.getChildren()){
                                            String newKey = usersSnapshot.getKey();
                                            if(newKey.equals(keySender)){
                                                UserEntity userEntity2 = usersSnapshot.getValue(UserEntity.class);
                                                    String senderName = userEntity2.getName();
                                                    String senderFirstName = userEntity2.getFirstname();
                                                Toast.makeText(getContext(),"You have a family request from "+ senderName+" "+senderFirstName,Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return root;
    }

    public void setRecyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecipesAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}
