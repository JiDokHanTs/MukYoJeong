package com.jidokhants.mukyojeong;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
        try {
            if(!isExistFoodDB()){
                copyFoodDB();
            }
        }catch (Exception e){
        }
    }
    public boolean isExistFoodDB(){
        String FILEPATH ="/data/data/" + getPackageName()+"/databases/"+"mukyojeong.db";
        File file = new File(FILEPATH);
        if(file.exists()){
            return true;
        }
        return false;
    }
    public void copyFoodDB(){
        Log.d("FoodDBCopy", "called");
        AssetManager assetManager = getApplicationContext().getAssets();
        String folderPath = "/data/data/" + getPackageName() + "/databases";
        String filePath = "/data/data/"+getPackageName()+"/databases/mukyojeong.db";

        File folder = new File(folderPath);
        File file = new File(filePath);

        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            InputStream is = assetManager.open("databases/"+"mukyojeong.db");
            BufferedInputStream bis = new BufferedInputStream(is);

            if (!folder.exists()){
                folder.mkdirs();
            }
            if(file.exists()){
                file.delete();
                file.createNewFile();
            }

            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            int read = -1;
            byte[] buffer = new byte[1024];
            while((read = bis.read(buffer, 0, 1024))!= -1){
                bos.write(buffer, 0, read);
            }

            bos.flush();

            bos.close();
            fos.close();
            bis.close();
            is.close();

        } catch (IOException e){
            Log.e("ErrorMessage : " , e.getMessage());
        }
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

    public void replaceCalendarFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment).commit();
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