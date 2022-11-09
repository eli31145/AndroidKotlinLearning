package com.example.chap2_3_poc

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentContainerView

const val TO_SECOND_ACTIVITY = 1

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_to_second_activity).setOnClickListener {
            Intent(this, SecondActivity::class.java).also {
                openSecondActivityIntent -> startActivityForResult(openSecondActivityIntent, TO_SECOND_ACTIVITY)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == TO_SECOND_ACTIVITY && resultCode == Activity.RESULT_OK){
            findViewById<TextView>(R.id.tv_display_text).text = data?.getStringExtra(TEXT_SEND_BACK)
        } else {
            Toast.makeText(this, "retrieval failed", Toast.LENGTH_SHORT).show()
        }
    }

}