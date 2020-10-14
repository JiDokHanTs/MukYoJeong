package com.jidokhants.mukyojeong;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentCalendar fragmentCalendar;
    FragmentCommunity fragmentCommunity;
    FragmentNotifications fragmentNotifications;
    FragmentSetting fragmentSetting;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorPrimaryDark));
        toggle.syncState();


        fragmentCalendar = new FragmentCalendar();
        fragmentCommunity = new FragmentCommunity();
        fragmentNotifications = new FragmentNotifications();
        fragmentSetting = new FragmentSetting();


        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragmentCalendar).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.calnedarItem:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout, fragmentCalendar).commitAllowingStateLoss();
                        break;
                    case R.id.communityItem:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout, fragmentCommunity).commitAllowingStateLoss();
                        break;
                    case R.id.notificationItem:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout, fragmentNotifications).commitAllowingStateLoss();
                        break;
                    case R.id.settingItem:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout, fragmentSetting).commitAllowingStateLoss();
                        break;
                }
                return true;
            }
        });

        NavigationView leftNavigationView = findViewById(R.id.left_nav_view);
        leftNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()) {
                    case R.id.calnedarItem:
                        fragment = fragmentCalendar;
                        break;
                    case R.id.communityItem:
                        fragment = fragmentCommunity;
                        break;
                    case R.id.notificationItem:
                        fragment = fragmentNotifications;
                        break;
                    case R.id.settingItem:
                        fragment = fragmentSetting;
                        break;
                }
                BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
                bottomNavigationView.setSelectedItemId(menuItem.getItemId());

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
//                menuItem.setChecked(true);
                TextView toolbarTitle =  findViewById(R.id.toolbar_title);
                toolbarTitle.setText(menuItem.getTitle());
                drawerLayout.closeDrawers();
                return true;
            }
        });

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public void replaceCommunityFragment(int cmntSign, Fragment fragment){
        Bundle args = new Bundle();
        args.putInt("CMNT_SIGN", cmntSign);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment).commit();
    }
}