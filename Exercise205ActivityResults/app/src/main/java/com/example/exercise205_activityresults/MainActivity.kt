package com.example.exercise205_activityresults

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat


const val PICK_RAINBOW_COLOR_INTENT = 1
    // Key to return rainbow color name in intent
const val RAINBOW_COLOR_NAME = "RAINBOW_COLOR_NAME"
    // Key to return rainbow color in intent
const val RAINBOW_COLOR = "RAINBOW_COLOR"
const val DEFAULT_COLOR = "#FFFFFF" // White

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.submit_button).setOnClickListener {
            Intent(this,RainbowColorPickerActivity::class.java)
                .also { rainbowColorPickerIntent -> startActivityForResult(rainbowColorPickerIntent, PICK_RAINBOW_COLOR_INTENT) }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == PICK_RAINBOW_COLOR_INTENT && resultCode == Activity.RESULT_OK){
            val backgroundColor = data?.getIntExtra(RAINBOW_COLOR, Color.parseColor(DEFAULT_COLOR))?: Color.parseColor(DEFAULT_COLOR)
            val colorName = data?.getStringExtra(RAINBOW_COLOR_NAME) ?: ""
            val colorMessage = getString(R.string.color_chosen_message, colorName)
            val rainbowColor = findViewById<TextView>(R.id.rainbow_color)

            rainbowColor.setBackgroundColor(ContextCompat.getColor(this,backgroundColor))
                rainbowColor.text = colorMessage
                rainbowColor.visibility = VISIBLE
        }
    }
}