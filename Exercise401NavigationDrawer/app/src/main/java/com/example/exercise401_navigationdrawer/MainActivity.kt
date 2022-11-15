package com.example.exercise401_navigationdrawer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //programmatically change windowNoTitle instead of adding it inside Themes.xml. If want to do it programmatically
        //have to add it before setContentView()
        //requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)

        //configures toolbar aka appbar by referencing from layout and set
        setSupportActionBar(findViewById(R.id.toolbar))
        //retrieve NavHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        //creating top level destinations and adding them to drawer inside activity_main
        //specifies drawer_layout as the overall View in charge for the Drawer
        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_home, R.id.nav_recent, R.id.nav_favorites, R.id.nav_archive,
            R.id.nav_bin), findViewById(R.id.drawer_layout))

        //sets up app bar with navigation graph so any changes made to destinations are reflected in app bar
            //without this hamburger menu won't even be shown
        setupActionBarWithNavController(navController, appBarConfiguration)

        //specifies the item within the nav drawer that should be highlighted when user clicks on it
            //without this when click on menu item no action will be taken
        findViewById<NavigationView>(R.id.nav_view).setupWithNavController(navController)
    }

    //handles up button press for secondary destination to back stack
        //if just call super implementation, phone back button can work but app bar back button won't work
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration)||
                super.onSupportNavigateUp()
    }
    //selects specific (overflow) menu to add to app bar
        //without this overflow menu won't appear
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
    //handles what to do when (overflow) menu item is selected, to navigate to destination
        //without this once select menu option nothing happens
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.nav_host_fragment))
    }

}