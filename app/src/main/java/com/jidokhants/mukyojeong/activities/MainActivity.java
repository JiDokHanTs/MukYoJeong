package com.jidokhants.mukyojeong.activities;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jidokhants.mukyojeong.R;
import com.jidokhants.mukyojeong.data.MukDBHelper;
import com.jidokhants.mukyojeong.fragments.calendar.main.FragmentCalendar;
import com.jidokhants.mukyojeong.fragments.community.FragmentCommunity;
import com.jidokhants.mukyojeong.fragments.community.FragmentWrite;
import com.jidokhants.mukyojeong.fragments.notification.FragmentNotifications;
import com.jidokhants.mukyojeong.fragments.setting.FragmentSetting;
import com.jidokhants.mukyojeong.model.Food;
import com.jidokhants.mukyojeong.model.Ingredient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG="MAINACTIVITY";

    private FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentCalendar fragmentCalendar;
    FragmentCommunity fragmentCommunity;
    FragmentNotifications fragmentNotifications;
    FragmentSetting fragmentSetting;

    FragmentWrite fragmentWrite;

    DrawerLayout drawerLayout;

    FirebaseUser currentUser;

    MukDBHelper mukDBHelper;
    TextView toolbarTitle;

    SearchView searchView;
    private DatabaseReference databaseReference;
    private Menu mMenu;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            if(!isExistFoodDB()){
                copyFoodDB();
            }
        }catch (Exception e){
        }
        toolbar = findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setActionbarshow();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorPrimaryDark));
        toggle.syncState();

        mukDBHelper = MukDBHelper.getInstance(MainActivity.this);
        fragmentCalendar = FragmentCalendar.newInstance();
        fragmentCommunity = FragmentCommunity.newInstance(null);

        fragmentWrite = new FragmentWrite();
        fragmentNotifications = new FragmentNotifications();
        fragmentSetting = new FragmentSetting();


        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragmentCalendar).commitAllowingStateLoss();

        showSearchMenu(false);
        showWriteMenu(false);
        toolbarTitle =  findViewById(R.id.toolbar_title);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                String textTitle ="";
                toolbar.collapseActionView();
                switch (menuItem.getItemId()) {
                    case R.id.calnedarItem:
                        fragmentCalendar = FragmentCalendar.newInstance();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frameLayout, fragmentCalendar).commitAllowingStateLoss();
                        break;
                    case R.id.communityItem:
                        fragmentCommunity = FragmentCommunity.newInstance(null);
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
                    default:
                       textTitle = "MukYoJeong";
                    break;
                }

                if (menuItem.getTitle()==null){
                    toolbarTitle.setText(textTitle);
                }
                toolbarTitle.setText(menuItem.getTitle());
                return true;
            }
        });

        NavigationView leftNavigationView = findViewById(R.id.left_nav_view);
        leftNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                if (searchView!=null){
                    setSearchviewClose();
                }
                switch (menuItem.getItemId()) {
                    case R.id.calnedarItem:
                        fragmentCalendar = FragmentCalendar.newInstance();
                        fragment = fragmentCalendar;
                        break;
                    case R.id.communityItem:
                        fragmentCommunity = FragmentCommunity.newInstance(null);
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
                toolbarTitle.setText(menuItem.getTitle());
                drawerLayout.closeDrawers();
                return true;
            }
        });

        View navHeader = leftNavigationView.getHeaderView(0);
        TextView tvProfileName = navHeader.findViewById(R.id.nav_profile_name);
        TextView tvProfileEmail = navHeader.findViewById(R.id.nav_profile_email);
        ImageView tvProfileImg = navHeader.findViewById(R.id.nav_profile_img);

        String name="";
        String email="";
        Uri photoUri;
        if(currentUser != null){
            name = currentUser.getDisplayName();
            email = currentUser.getEmail();
            photoUri = currentUser.getPhotoUrl();
            tvProfileName.setText(name);
            tvProfileEmail.setText(email);
            Glide.with(this).load(photoUri).into(tvProfileImg);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView)searchItem.getActionView();

        ImageView resetIcon = (ImageView) searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        resetIcon.setImageResource(R.drawable.ic_action_reset);

        EditText editText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);

        editText.setTextColor(Color.BLACK);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null){
                    Log.d(TAG, "Query: "+query);
                    fragmentCommunity = FragmentCommunity.newInstance(query);
                    replaceCommunityFragment(fragmentCommunity);
                    searchView.clearFocus();
                 }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_write:
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentWrite = FragmentWrite.newInstance();
                fragmentManager.beginTransaction().replace(R.id.frameLayout, fragmentWrite).commit();
                getSupportActionBar().hide();
                FrameLayout frameLayout = findViewById(R.id.frameLayout);
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)frameLayout.getLayoutParams();
                params.topMargin = 0;
                params.bottomMargin = 0;
                frameLayout.setLayoutParams(params);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void setActionbarshow(){
        TypedValue tv = new TypedValue();
        int actionbarSize = 0;
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            actionbarSize = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }

        FrameLayout frameLayout = findViewById(R.id.frameLayout);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)frameLayout.getLayoutParams();
        params.topMargin = actionbarSize;
        params.bottomMargin = actionbarSize;
        frameLayout.setLayoutParams(params);
    }
    public void showSearchMenu(boolean isShow){
        if (mMenu == null)return;
        mMenu.setGroupVisible(R.id.action_search_group, isShow);
    }
    public void showWriteMenu(boolean isShow){
        if (mMenu == null)return;
        mMenu.setGroupVisible(R.id.action_write_group, isShow);
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
    public void replaceCommunityPostFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment).commit();
    }
    public void replaceCalendarFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment).commit();
    }

    public void replaceCommunityFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment).commit();
    }

//    public void callWriteFragmentTransfer(ArrayList<Ingredient>ingredients){
//        fragmentWrite.updateIngredientEdittext(ingredients);
//    }
//    public ArrayList<Ingredient> getIngredientsFromWriter(){
//        return fragmentWrite.getIngredientsFromEdit();
//    }
    public void setSearchviewClose(){
        searchView.setIconified(true);
    }
    public void ingredientsFrom(ArrayList<Ingredient> temp){
        fragmentWrite.setIngredients(temp);
    }
    public void updateFoodsInLocal(){
        final int savedFoodIndex = mukDBHelper.getMaxFoodId();
        Log.d("UPDATEFOODINLOCAL", "called");
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("foods").child("index").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int lastIndex = Integer.valueOf(snapshot.getValue().toString());
                Log.d("UPDATE FOOD DB", "1: "+lastIndex+", 2: "+savedFoodIndex);
                if (lastIndex - savedFoodIndex > 0){
                    databaseReference.child("foods").child("food").limitToLast(lastIndex - savedFoodIndex).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                Food food = dataSnapshot.getValue(Food.class);
                                Log.d("UPDATE FOODS", food.getName());
                                mukDBHelper.insertFood(food);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d("FOODDB UPDATE ERROR", error.getMessage());
                        }
                    });
                }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        databaseReference.child("foods").child("index").setValue(29926)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Log.d("FB", "저장 ok");
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d("FB", "저장 no");
//            }
//        });
    }

}