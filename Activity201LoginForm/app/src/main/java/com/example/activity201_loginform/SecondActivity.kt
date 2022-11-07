package com.example.activity201_loginform

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

const val IS_LOGGED_IN = "IS_LOGGED_IN"

class SecondActivity:AppCompatActivity() {
    private val USER_NAME_CORRECT_VALUE ="someusername"
    private val PASSWORD_CORRECT_VALUE = "somepassword"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        intent?.let {
            val checkUsername = intent.getStringExtra(USER_NAME_KEY)
            val checkPassword = intent.getStringExtra(PASSWORD_KEY)

            if (checkUsername.equals(USER_NAME_CORRECT_VALUE) && checkPassword.equals(PASSWORD_CORRECT_VALUE)){
                it.putExtra(IS_LOGGED_IN, true)

                setResult(Activity.RESULT_OK, it)
                finish()
            } else {
                it.putExtra(IS_LOGGED_IN, false)

                setResult(Activity.RESULT_OK, it)
                finish()
            }

        }
    }
}