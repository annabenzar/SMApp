package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.ui.dashboard.DashboardFragment;
import com.example.myapplication.ui.home.HomeFragment;
import com.example.myapplication.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeScreen extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private HomeFragment homeFragment;
    private DashboardFragment dashboardFragment;
    private NotificationsFragment notificationsFragment;

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

            case R.id.navigation_notifications:
                fragmentManager.beginTransaction().hide(activeFragment).show(notificationsFragment).commit();
                activeFragment = notificationsFragment;
                return true;
        }
        return false;
    }

    private void LoadFragment(String name) {

        fragmentManager.beginTransaction().add(R.id.nav_host_fragment, notificationsFragment, "3").hide(notificationsFragment).commit();
        fragmentManager.beginTransaction().add(R.id.nav_host_fragment, dashboardFragment, "2").hide(dashboardFragment).commit();
        fragmentManager.beginTransaction().add(R.id.nav_host_fragment, homeFragment, "1").detach(homeFragment).attach(homeFragment).commit();
        Bundle bundle = new Bundle();
        bundle.putString("key",name);
        homeFragment.setArguments(bundle);
    }

    public void initializeViews() {
        homeFragment = new HomeFragment();
        dashboardFragment = new DashboardFragment();
        notificationsFragment = new NotificationsFragment();

        //ia val primului fragment
        activeFragment = homeFragment;
    }
}
