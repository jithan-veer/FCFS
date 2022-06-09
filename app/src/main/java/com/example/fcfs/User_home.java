package com.example.fcfs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class User_home extends AppCompatActivity {

    BottomNavigationView bn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        bn1 = findViewById(R.id.bottom_nav);
//        BottomNavigationView.OnNavigationItemReselectedListener navListener;
        bn1.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new shopsFragment()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =new BottomNavigationView.OnNavigationItemSelectedListener(){

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedfragment = null;
            switch (item.getItemId()) {

                case R.id.shop:
                    selectedfragment = new shopsFragment();
                    break;
                case R.id.bill:
                    selectedfragment=new billFragment();
                    break;
                case R.id.hist:
                    selectedfragment=new shopsFragment();
                    break;
                case R.id.profile:
                    selectedfragment=new ProfileFragment();
                    break;



            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedfragment).commit();
            return true;
        }
    };
}