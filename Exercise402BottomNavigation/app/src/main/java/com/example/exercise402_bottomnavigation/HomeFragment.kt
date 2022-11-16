package com.example.exercise402_bottomnavigation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.android.material.button.MaterialButton

class HomeFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        view.findViewById<Button>(R.id.button_home)?.setOnClickListener (
            Navigation.createNavigateOnClickListener(R.id.nav_home_to_content, null)
            )

        /*val btnHome = view.findViewById<MaterialButton>(R.id.button_home)
        btnHome.setOnClickListener{
            Toast.makeText(activity, "test", Toast.LENGTH_SHORT).show()
        }*/
        return view
    }

}