package com.example.apprunner;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    DrawerLayout drawer;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    TextView tv_FName,tv_LName,tv_stuts;
    String first_name,last_name,type,email,password;

    public static final String url = "http://10.0.2.2:3000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar_organizer);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_organizer);
        navigationView = findViewById(R.id.nav_view_organizer);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        // load default fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer_organizer,new MainFragment());
        fragmentTransaction.commit();

        Bundle bundle = new Bundle();
        bundle.putString("first_name", getIntent().getExtras().getString("first_name"));
        bundle.putString("last_name",getIntent().getExtras().getString("last_name"));
        bundle.putString("type",getIntent().getExtras().getString("type"));
        MainFragment mainFragment = new MainFragment();
        mainFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_organizer,mainFragment).commit();

        first_name = getIntent().getExtras().getString("first_name");
        last_name = getIntent().getExtras().getString("last_name");
        type = getIntent().getExtras().getString("type");


        FloatingActionButton floating_action_button = findViewById(R.id.floating_action_button);
        floating_action_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivityAddEvent();
            }
        });

    }

    private void openMainActivityAddEvent() {
        Intent intent = new Intent(MainActivity.this, MainActivityAddEvent.class);
        intent.putExtra("first_name", first_name);
        intent.putExtra("last_name", last_name);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        Bundle bundle = new Bundle();
        bundle.putString("first_name",first_name);
        bundle.putString("last_name",last_name);
        bundle.putString("type",type);
        if(item.getItemId() == R.id.home) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_organizer,new MainFragment());
            fragmentTransaction.commit();
            Toast.makeText(MainActivity.this, "HOME", Toast.LENGTH_SHORT).show();
            //Toast.makeText(MainActivity.this, first_name + last_name, Toast.LENGTH_SHORT).show();
            MainFragment mainFragment = new MainFragment();
            mainFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_organizer,mainFragment).commit();
        }

        if(item.getItemId() == R.id.event) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_organizer,new ProfileEventOrganizerFragment());
            fragmentTransaction.commit();
            Toast.makeText(MainActivity.this, "EVENT", Toast.LENGTH_SHORT).show();
            //Toast.makeText(MainActivity.this, first_name + last_name, Toast.LENGTH_SHORT).show();
            ProfileEventOrganizerFragment profileEventOrganizerFragment = new ProfileEventOrganizerFragment();
            profileEventOrganizerFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_organizer,profileEventOrganizerFragment).commit();
        }

        if(item.getItemId() == R.id.status_event) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_organizer,new StatusEventFragment());
            fragmentTransaction.commit();
            Toast.makeText(MainActivity.this, "STATUS EVENT", Toast.LENGTH_SHORT).show();
            StatusEventFragment statusEventFragment = new StatusEventFragment();
            statusEventFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_organizer,statusEventFragment).commit();
        }

        if(item.getItemId() == R.id.status_reward) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_organizer,new StatusRewardFragment());
            fragmentTransaction.commit();
            Toast.makeText(MainActivity.this, "STATUS REWARD", Toast.LENGTH_SHORT).show();
            StatusRewardFragment statusRewardFragment = new StatusRewardFragment();
            statusRewardFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_organizer,statusRewardFragment).commit();
        }

        if(item.getItemId() == R.id.help) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer_organizer,new HelpFragment());
            fragmentTransaction.commit();
            Toast.makeText(MainActivity.this, "HELP", Toast.LENGTH_SHORT).show();
            HelpFragment helpFragment = new HelpFragment();
            helpFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer_organizer,helpFragment).commit();
        }

        return true;
    }

}

