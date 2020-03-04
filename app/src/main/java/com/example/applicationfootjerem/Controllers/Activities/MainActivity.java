package com.example.applicationfootjerem.Controllers.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.applicationfootjerem.Adapters.OngletAdapter;
import com.example.applicationfootjerem.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureViewPagerAndTabs();
    }

    //Fais la liaison avec les onglets et le ViewPager
    private void configureViewPagerAndTabs(){
        // 1 - Get ViewPager from layout
        ViewPager pager = (ViewPager)findViewById(R.id.mainViewPager);
        // 2 - Set Adapter OngletAdapter and glue it together
        pager.setAdapter(new OngletAdapter(getSupportFragmentManager()) {
        });

        // 1 - Get TabLayout from layout
        TabLayout tabs = (TabLayout)findViewById(R.id.activityTabLayout);
        // 2 - Assembler les onglets et le ViewPager
        tabs.setupWithViewPager(pager);
        // 3 - Pour que les onglets aient tous la même taille
        tabs.setTabMode(TabLayout.MODE_FIXED);
    }
}
