package com.example.myapplication.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Adapters.UsersAdapter;
import com.example.myapplication.FirebaseLoginActivity;
import com.example.myapplication.Models.ListUserModel;
import com.example.myapplication.Models.UserEntity;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.myapplication.Helpers.FirebaseHelper.usersDatabase;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UsersAdapter adapter;
    List<UserEntity> list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //setare recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.rv_searchResult_list);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        showAllUsers();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
       MenuInflater inflater = getMenuInflater();
       inflater.inflate(R.menu.top_nav_search,menu);

        MenuItem item = menu.findItem(R.id.search_item);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);

        //listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            //cand user-ul apasa pe searchButton
            public boolean onQueryTextSubmit(String query) {
                showAllUsers();
                return false;
            }

            @Override
            //cand introduce text
            public boolean onQueryTextChange(String newText) {
                searchUsers(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.logout_item:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, FirebaseLoginActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAllUsers() {
        //afisare intreaga lista de useri
        final FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        usersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    UserEntity userEntity = postSnapshot.getValue(UserEntity.class);
                    if(!(userEntity.getId().equals(fUser.getUid()))){
                        list.add(userEntity);
                    }
                    adapter = new UsersAdapter(list);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void searchUsers(final String s) {

        final FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        usersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    UserEntity userEntity = postSnapshot.getValue(UserEntity.class);

                    //nu afisez user-ul curent, cel care e pe aplicatie si cauta alti useri
                    if(!(userEntity.getId().equals(fUser.getUid()))){
                        if((userEntity.getName().toLowerCase().contains(s))||(userEntity.getFirstname().toLowerCase().contains(s))){
                            list.add(userEntity);
                        }
                    }
                    adapter = new UsersAdapter(list);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


}
