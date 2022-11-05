package com.example.exercise204_introductiontointents

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        intent?.let {
            val fullName = intent.getStringExtra(MainActivity.FULL_NAME_KEY)
            findViewById<TextView>(R.id.welcome_text).text = getString(R.string.welcome_text, fullName)
        }
    }
}