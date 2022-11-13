package com.example.exercise303_dualpanelayouts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

const val STAR_SIGN_ID = "STAR_SIGN_ID"

interface StarSignListener{
    fun onSelected(id: Int)
}

class MainActivity : AppCompatActivity(), StarSignListener {

    var isDualPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //checks whether DetailFragment is in the Activity's inflated layout by checking whether the star_sign_detail
        //fragment exists
        isDualPane = findViewById<View>(R.id.star_sign_detail) != null
    }

    override fun onSelected(id: Int) {
        if(isDualPane){
            //retrieve star sign ID passed from ListFragment back to MainActivity by Listener and pass to DetailFragment
                //only pass the ID if isDualPane
            val detailFragment = supportFragmentManager.findFragmentById(R.id.star_sign_detail) as DetailFragment
            detailFragment.setStarSignData(id)
        } else {
            //open up DetailActivity by sending in an intent containing the ID chosen
            val detailIntent = Intent(this, DetailActivity::class.java).apply {
                putExtra(STAR_SIGN_ID, id)
            }
            startActivity(detailIntent)
        }
    }
}