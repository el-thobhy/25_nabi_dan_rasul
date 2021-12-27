package com.belajar.dicoding.submission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.belajar.dicoding.submission.fragment.AboutFragment;
import com.belajar.dicoding.submission.fragment.FavoritFragment;
import com.belajar.dicoding.submission.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        navigationView =findViewById(R.id.top_nav);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container,new HomeFragment(),"main_fragment").commit();
        navigationView.setSelectedItemId(R.id.nav_utama);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_utama:
                        Fragment fragment1=new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.body_container,fragment1,"main_fragment").commit();
                        Log.d("fragment check", "utama_fragment");
                        break;
                    case R.id.nav_fav:
                        Fragment fragment2=new FavoritFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.body_container,fragment2).commit();
                        break;
                    case R.id.nav_about:
                        Fragment fragment3=new AboutFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.body_container,fragment3).commit();
                        break;
                }
                return true;
            }
        });

        ActionBar actionBar;
        actionBar=getSupportActionBar();
        ColorDrawable colorDrawable=new ColorDrawable(Color.parseColor("#18305E"));
        actionBar.setBackgroundDrawable(colorDrawable);
    }

}