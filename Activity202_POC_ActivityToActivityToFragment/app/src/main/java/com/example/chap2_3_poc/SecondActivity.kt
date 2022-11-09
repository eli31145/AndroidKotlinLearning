package com.example.chap2_3_poc

import android.app.Activity
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentTransaction

interface InputListener{
    fun onSelected(input: String)
}

const val TEXT_SEND_BACK = "TEXT_SENT_BACK"

class SecondActivity: AppCompatActivity(), InputListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity_two)

        val btnOpenFragment = findViewById<Button>(R.id.btn_open_fragment)
        btnOpenFragment.setOnClickListener {
            it.visibility = View.GONE
            val ft: FragmentTransaction  = supportFragmentManager.beginTransaction()
                ft.replace(R.id.frame_layout, SecondActivityFragment()).commit()
        }
    }

    override fun onSelected(input: String) {
        if (input.isNotBlank()){
            intent?.let {
                it.putExtra(TEXT_SEND_BACK, input)
                setResult(Activity.RESULT_OK, it)
                finish()
            }
        } else {
            finish()
        }

    }
}