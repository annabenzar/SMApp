package com.example.myapplication.ui.favorites;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapters.FavoritesAdapter;
import com.example.myapplication.Adapters.PhotoAdapter;
import com.example.myapplication.Models.ListPhotoModel;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.Helpers.FirebaseHelper.favoritesDatabase;

public class ToCookFragment extends Fragment {

    RecyclerView recyclerView;
    FavoritesAdapter adapter;
    List<ListPhotoModel> list = new ArrayList<>();

    ProgressDialog progressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_tocook, container, false);


        recyclerView = (RecyclerView) root.findViewById(R.id.rv_tocook_list);
        final String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference onefavoritesRecipes = favoritesDatabase.child(currentUser);
        onefavoritesRecipes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                list.removeAll(list);

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    //extragere sub forma ListPhotoModel

                    String nameRetrieved = String.valueOf(postSnapshot.child("imageName").getValue());
                    String timeRetrieved = String.valueOf(postSnapshot.child("imageTime").getValue());
                    String typeRetreived = String.valueOf(postSnapshot.child("imageType").getValue());
                    String imageRetreived = String.valueOf(postSnapshot.child("imageURL").getValue());
                    String ingredientsRetrieved = String.valueOf(postSnapshot.child("imageIngredients").getValue());
                    String prepRetrieved = String.valueOf(postSnapshot.child("imagePrep").getValue());
                    String authorRetrieved = String.valueOf(postSnapshot.child("imageAuthor").getValue());

                    ListPhotoModel listphoto = new ListPhotoModel(imageRetreived,nameRetrieved,timeRetrieved,typeRetreived,ingredientsRetrieved,prepRetrieved,authorRetrieved);

                    list.add(listphoto);
                }


                setRecyclerView();
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
        adapter = new FavoritesAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}
