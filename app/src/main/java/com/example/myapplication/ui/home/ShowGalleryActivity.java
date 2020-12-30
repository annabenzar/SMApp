package com.example.myapplication.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Adapters.PhotoAdapter;
import com.example.myapplication.Models.ListPhotoModel;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import static com.example.myapplication.Helpers.FirebaseHelper.recipeDatabase;

public class ShowGalleryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    PhotoAdapter adapter;
    List<ListPhotoModel> list = new ArrayList<>();

    ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_gallery);

        recyclerView = (RecyclerView) findViewById(R.id.rv_image_list);

        progressDialog = new ProgressDialog(ShowGalleryActivity.this);
        progressDialog.setMessage("Loading Images From Firebase.");
        progressDialog.show();


        recipeDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    //extragere sub forma ListPhotoModel

                    String nameRetrieved = String.valueOf(postSnapshot.child("imageName").getValue());
                    String timeRetrieved = String.valueOf(postSnapshot.child("imageTime").getValue());
                    String typeRetreived = String.valueOf(postSnapshot.child("imageType").getValue());
                    String imageRetreived = String.valueOf(postSnapshot.child("imageURL").getValue());
                    String ingredientsRetrieved = String.valueOf(postSnapshot.child("imageIngredients").getValue());
                    String prepRetrieved = String.valueOf(postSnapshot.child("imagePrep").getValue());


                    ListPhotoModel listphoto = new ListPhotoModel(imageRetreived,nameRetrieved,timeRetrieved,typeRetreived,ingredientsRetrieved,prepRetrieved);

                    list.add(listphoto);
                }


                setRecyclerView();

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Hiding the progress dialog.
                progressDialog.dismiss();
            }
        });

    }

    public void setRecyclerView(){
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PhotoAdapter(list);
        recyclerView.setAdapter(adapter);
    }

}



