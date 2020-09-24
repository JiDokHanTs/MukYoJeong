package com.jidokhants.mukyojeong;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentCalendar fragmentCalendar;
    FragmentCommunity fragmentCommunity;
    FragmentNotifications fragmentNotifications;
    FragmentSetting fragmentSetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }
}