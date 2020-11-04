package com.example.apprunner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;

public class SecondActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public static final String url = "http://10.0.2.2:3000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        toolbar = findViewById(R.id.toolbar_user);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_user);
        navigationView = findViewById(R.id.nav_view_user);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer_user,new SecondFragment());
        fragmentTransaction.commit();

        Bundle bundle = new Bundle();
        bundle.putString("first_name", getIntent().getExtras().getString("first_name"));
        bundle.putString("last_name",getIntent().getExtras().getString("last_name"));
        bundle.putString("type",getIntent().getExtras().getString("type"));
        bundle.putString("email", getIntent().getExtras().getString("email"));
        bundle.putString("password",getIntent().getExtras().getString("password"));
        SecondFragment secondFragment = new SecondFragment();
        secondFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_user,secondFragment).commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if(item.getItemId() == R.id.home) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_user,new SecondFragment());
            fragmentTransaction.commit();
        }

        if(item.getItemId() == R.id.event) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_user,new ProfileEventUserFragment());
            fragmentTransaction.commit();
        }

        if(item.getItemId() == R.id.submit_payment) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_user,new SubmitPaymentUserFragment());
            fragmentTransaction.commit();
        }

        if(item.getItemId() == R.id.status_payment) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_user,new StatusPaymentUserFragment());
            fragmentTransaction.commit();
        }

        if(item.getItemId() == R.id.uplodestatistics) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_user,new UplodeStatisticsFragment());
            fragmentTransaction.commit();
        }

        if(item.getItemId() == R.id.edit_profile) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_user,new EditProfileUserFragment());
            fragmentTransaction.commit();
        }

        if(item.getItemId() == R.id.statistics_event) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_user,new StatisticsEventFragment());
            fragmentTransaction.commit();
        }

        if(item.getItemId() == R.id.status_reward) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_user,new CheckStatusRewardFragment());
            fragmentTransaction.commit();
        }

        if(item.getItemId() == R.id.help) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_user,new HelpFragment());
            fragmentTransaction.commit();
        }

        return true;
    }
}