package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.ui.recipes.RecipesFragment;
import com.example.myapplication.ui.toCook.ToCookFragment;
import com.example.myapplication.ui.profile.ProfileFragment;
import com.example.myapplication.ui.toBuy.ToBuyFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeScreen extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ProfileFragment profileFragment;
    private RecipesFragment recipesFragment;
    private ToBuyFragment toBuyFragment;
    private ToCookFragment toCookFragment;

    private BottomNavigationView navView;
    //tine evidenta fragmentului curent
    private Fragment activeFragment;
    //ajuta la lucrul cu fragmente
    final FragmentManager fragmentManager = getSupportFragmentManager();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        navView = findViewById(R.id.bnv_main_menu);
        navView.setOnNavigationItemSelectedListener(this);

        //transmitere username ca parametru
        String name = getIntent().getStringExtra("name");

        initializeViews();
        LoadFragment(name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_nav_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.logout_item:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this,FirebaseLoginActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragmentManager.beginTransaction().hide(activeFragment).show(profileFragment).commit();
                activeFragment = profileFragment;
                return true;

            case R.id.navigation_dashboard:
                fragmentManager.beginTransaction().hide(activeFragment).show(recipesFragment).commit();
                activeFragment = recipesFragment;
                return true;

            case R.id.navigation_ingredients:
                fragmentManager.beginTransaction().hide(activeFragment).show(toBuyFragment).commit();
                activeFragment = toBuyFragment;
                return true;
            case R.id.navigation_tocook:
                fragmentManager.beginTransaction().hide(activeFragment).show(toCookFragment).commit();
                activeFragment = toCookFragment;
                return true;
        }
        return false;
    }

    private void LoadFragment(String name) {

        fragmentManager.beginTransaction().add(R.id.nav_host_fragment, profileFragment, "1").hide(profileFragment).commit();
        fragmentManager.beginTransaction().add(R.id.nav_host_fragment, toCookFragment, "2").hide(toCookFragment).commit();
        fragmentManager.beginTransaction().add(R.id.nav_host_fragment, toBuyFragment, "3").hide(toBuyFragment).commit();
        fragmentManager.beginTransaction().add(R.id.nav_host_fragment, recipesFragment, "4").detach(recipesFragment).attach(recipesFragment).commit();
        Bundle bundle = new Bundle();
        bundle.putString("key",name);
        profileFragment.setArguments(bundle);
    }

    public void initializeViews() {
        profileFragment = new ProfileFragment();
        recipesFragment = new RecipesFragment();
        toBuyFragment = new ToBuyFragment();
        toCookFragment = new ToCookFragment();
        //ia val primului fragment
        activeFragment = recipesFragment;
    }
}
