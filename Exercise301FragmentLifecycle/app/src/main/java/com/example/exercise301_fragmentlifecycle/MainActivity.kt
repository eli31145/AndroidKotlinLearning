package com.example.exercise301_fragmentlifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "ActivityonStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "ActivityonResume")
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}