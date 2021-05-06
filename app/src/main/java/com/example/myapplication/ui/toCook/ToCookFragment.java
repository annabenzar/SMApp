package com.example.myapplication.ui.toCook;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.ToCookAdapter;
import com.example.myapplication.Models.ListRecipeModel;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.Helpers.FirebaseHelper.familyDatabase;
import static com.example.myapplication.Helpers.FirebaseHelper.favoritesDatabase;

public class ToCookFragment extends Fragment {

    RecyclerView recyclerView;
    ToCookAdapter adapter;
    List<ListRecipeModel> list = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_tocook, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.rv_tocook_list);
        final String mUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final int[] foundInAFam={0};

        //obtinerea id-ului familiei mele
        familyDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postsnapshot : snapshot.getChildren()){
                    for(DataSnapshot newpostsnapshot : postsnapshot.getChildren()){
                        String userID = newpostsnapshot.getKey();
                        if(userID.equals(mUser)){
                            foundInAFam[0]=1;
                            //preiau id-ul familiei
                            String familyGroupId = postsnapshot.getKey();

                            //extrag dupa el din favoritesTable
                            DatabaseReference onefavoritesRecipes = favoritesDatabase.child(familyGroupId);

                            onefavoritesRecipes.addValueEventListener(new ValueEventListener() {
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

                            break;
                        }
                    }
                    if(foundInAFam[0]==1){
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }

    public void setRecyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ToCookAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}
