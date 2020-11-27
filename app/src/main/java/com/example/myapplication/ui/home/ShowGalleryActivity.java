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
import static com.example.myapplication.Helpers.FirebaseHelper.urlDatabase;

public class ShowGalleryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter ;
    List<ListPhotoModel> list = new ArrayList<>();

    ProgressDialog progressDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_gallery);

        recyclerView = (RecyclerView) findViewById(R.id.rv_image_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShowGalleryActivity.this));

        progressDialog = new ProgressDialog(ShowGalleryActivity.this);
        progressDialog.setMessage("Loading Images From Firebase.");
        progressDialog.show();


        urlDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    ListPhotoModel listphoto = postSnapshot.getValue(ListPhotoModel.class);

                    list.add(listphoto);
                }

                adapter = new PhotoAdapter(list);

                recyclerView.setAdapter(adapter);

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Hiding the progress dialog.
                progressDialog.dismiss();
            }
        });

    }
}



