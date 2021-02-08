package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.ui.dashboard.DashboardFragment;
import com.example.myapplication.ui.favorites.ToCookFragment;
import com.example.myapplication.ui.home.HomeFragment;
import com.example.myapplication.ui.ingredients.IngredientsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeScreen extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private HomeFragment homeFragment;
    private DashboardFragment dashboardFragment;
    private IngredientsFragment ingredientsFragment;
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragmentManager.beginTransaction().hide(activeFragment).show(homeFragment).commit();
                activeFragment = homeFragment;
                return true;

            case R.id.navigation_dashboard:
                fragmentManager.beginTransaction().hide(activeFragment).show(dashboardFragment).commit();
                activeFragment = dashboardFragment;
                return true;

            case R.id.navigation_ingredients:
                fragmentManager.beginTransaction().hide(activeFragment).show(ingredientsFragment).commit();
                activeFragment = ingredientsFragment;
                return true;
            case R.id.navigation_tocook:
                fragmentManager.beginTransaction().hide(activeFragment).show(toCookFragment).commit();
                activeFragment = toCookFragment;
                return true;
        }
        return false;
    }

    private void LoadFragment(String name) {

        fragmentManager.beginTransaction().add(R.id.nav_host_fragment, homeFragment, "1").hide(homeFragment).commit();
        fragmentManager.beginTransaction().add(R.id.nav_host_fragment, toCookFragment, "2").hide(toCookFragment).commit();
        fragmentManager.beginTransaction().add(R.id.nav_host_fragment, ingredientsFragment, "3").hide(ingredientsFragment).commit();
        fragmentManager.beginTransaction().add(R.id.nav_host_fragment, dashboardFragment, "4").detach(dashboardFragment).attach(dashboardFragment).commit();
        Bundle bundle = new Bundle();
        bundle.putString("key",name);
        homeFragment.setArguments(bundle);
    }

    public void initializeViews() {
        homeFragment = new HomeFragment();
        dashboardFragment = new DashboardFragment();
        ingredientsFragment = new IngredientsFragment();
        toCookFragment = new ToCookFragment();
        //ia val primului fragment
        activeFragment = dashboardFragment;
    }
}
