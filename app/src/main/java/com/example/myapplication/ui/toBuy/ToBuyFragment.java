package com.example.myapplication.ui.toBuy;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Adapters.ToBuyAdapter;
import com.example.myapplication.Models.ListIngredientModel;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.Helpers.FirebaseHelper.familyDatabase;
import static com.example.myapplication.Helpers.FirebaseHelper.ingredientsDatabase;

public class ToBuyFragment extends Fragment {

    RecyclerView recyclerView;
    ToBuyAdapter adapter;
    List<ListIngredientModel> list = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_tobuy, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.rv_tobuy_list);
        final String mUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final int[] foundInAFam={0};

        familyDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot postsnapshot :snapshot.getChildren()){
                    for(DataSnapshot newpostsnapshot : postsnapshot.getChildren()){
                        String userId = newpostsnapshot.getKey();
                        if(userId.equals(mUser)){
                            foundInAFam[0]=1;

                            String familyGroupId = postsnapshot.getKey();

                            DatabaseReference oneUserIngredients = ingredientsDatabase.child(familyGroupId);

                            oneUserIngredients.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    list.removeAll(list);

                                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                                        //extragere sub forma ListIngredientModel
                                        String ingredientRetrieved = String.valueOf(postSnapshot.child("ingredient").getValue());
                                        String nameRetrieved = String.valueOf(postSnapshot.child("nameRecipe").getValue());

                                        ListIngredientModel listIngredientModel = new ListIngredientModel(ingredientRetrieved,nameRetrieved);

                                        list.add(listIngredientModel);
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
                    if (foundInAFam[0]==1){
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
        adapter = new ToBuyAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}
