package com.example.exercise403_tabnavigation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        val tabs = findViewById<TabLayout>(R.id.tabs)

        //setup data to be displayed in swipeable ViewPager to come from MGPAdapter
        viewPager.adapter = MovieGenresPagerAdapter(this, supportFragmentManager)

        //setup TabLayout to display configured ViewPager
            //TabLayout.MODE_FIXED will make tabs be laid out uniformly
            // with a width that's equal to the width of the screen
        //tabs?.tabMode = TabLayout.MODE_FIXED
        tabs?.tabMode = TabLayout.MODE_SCROLLABLE
        tabs?.setupWithViewPager(viewPager)
    }
}