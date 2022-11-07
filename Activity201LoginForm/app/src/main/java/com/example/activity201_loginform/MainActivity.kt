package com.example.activity201_loginform

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet.VISIBLE

const val USER_NAME_KEY = "USER_NAME_KEY"
const val PASSWORD_KEY = "PASSWORD_KEY"
const val CHECK_DETAILS_INTENT = 1

private var isLoggedIn = false
private var username = ""

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button_submit).setOnClickListener {
            username = findViewById<EditText>(R.id.et_username).text.toString().trim()
            val password = findViewById<EditText>(R.id.et_password).text.toString().trim()

            if (username.isNotBlank() && password.isNotBlank()){
                Intent(this, SecondActivity::class.java).also { loginIntent ->
                    loginIntent.putExtra(USER_NAME_KEY,username)
                    loginIntent.putExtra(PASSWORD_KEY, password)
                    startActivityForResult(loginIntent, CHECK_DETAILS_INTENT)
                }
            } else {
                Toast.makeText(this, "complete login", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == CHECK_DETAILS_INTENT && resultCode == Activity.RESULT_OK){
            val success = data?.getBooleanExtra(IS_LOGGED_IN, false) ?: false
            if (success) {
                isLoggedIn = true
                findViewById<TextView>(R.id.tv_correct_login).visibility = View.VISIBLE
            } else {
                Toast.makeText(this, "login details incorrect", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "login failed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean(IS_LOGGED_IN, isLoggedIn)
        outState.putString(USER_NAME_KEY, username)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        isLoggedIn = savedInstanceState.getBoolean(IS_LOGGED_IN, false)
        username = savedInstanceState.getString(USER_NAME_KEY, "")

        if (isLoggedIn){
            findViewById<TextView>(R.id.tv_correct_login).visibility = View.VISIBLE
        } else {
            Toast.makeText(this, "login failed", Toast.LENGTH_SHORT).show()
        }
    }



}