package com.example.exercise304_dynamicfragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentContainerView

interface StarSignListener{
    fun onSelected(id: Int)
}

class MainActivity : AppCompatActivity(), StarSignListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null){
            findViewById<FragmentContainerView>(R.id.fragment_container)?.let {
                frameLayout ->
                val listFragment = ListFragment()
                //adding listFragment to the ViewGroup FrameLayout with the ID of the fragment_container.
                supportFragmentManager.beginTransaction().add(frameLayout.id, listFragment).commit()
            }
        }
    }

    override fun onSelected(id: Int) {
        findViewById<FragmentContainerView>(R.id.fragment_container)?.let { frameLayout ->
            val detailFragment = DetailFragment.newInstance(id)
            //replace ListFragment with DetailFragment using .replace()
            supportFragmentManager.beginTransaction().replace(frameLayout.id, detailFragment)
                    //so app does not exit when back button pressed, instead go back to previous fragment
                .addToBackStack(null)
                .commit()
        }
    }
}