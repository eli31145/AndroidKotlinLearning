package com.example.activity201_loginform_singletop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible

const val USER_NAME_KEY = "USER_NAME_KEY"
const val PASSWORD_KEY = "PASSWORD_KEY"

const val USER_NAME_CORRECT_VALUE = "someusername"
const val PASSWORD_CORRECT_VALUE = "somepassword"

const val IS_LOGGED_IN = "IS_LOGGED_IN"
const val LOGGED_IN_USERNAME = "LOGGED_IN_USERNAME"

class MainActivity : AppCompatActivity() {

    private val submitButton: Button
        get() = findViewById(R.id.button_submit)

    private val username: EditText
        get() = findViewById(R.id.et_username)

    private val password: EditText
        get() = findViewById(R.id.et_password)

    private val header: TextView
        get() = findViewById(R.id.tv_enter_info)

    private var isLoggedIn = false
    private var loggedInUser = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        submitButton.setOnClickListener {
            val usernameForm = username.text.toString().trim()
            val passwordForm = password.text.toString().trim()

            if (usernameForm.isNotBlank() && passwordForm.isNotBlank()){
                Intent(this, MainActivity::class.java).also { loginIntent ->
                    loginIntent.putExtra(USER_NAME_KEY, usernameForm)
                    loginIntent.putExtra(PASSWORD_KEY, passwordForm)
                    startActivity(loginIntent)
                }
            } else {
                Toast.makeText(this, "complete login", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        intent?.let {
            val usernameForm = it.getStringExtra(USER_NAME_KEY) ?: ""
            val passwordForm = it.getStringExtra(PASSWORD_KEY) ?: ""

            val loggedInCorrectly = hasEnteredCorrectCredentials(usernameForm, passwordForm)
            if(loggedInCorrectly){
                isLoggedIn = true
                setLoggedIn(usernameForm)
            } else {
                Toast.makeText(this, "login details incorrect", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setLoggedIn(usernameForm: String) {
        loggedInUser = usernameForm
        val welcomeMessage = getString(R.string.welcome_text, loggedInUser)
        username.isVisible = false
        password.isVisible = false
        submitButton.isVisible = false
        header.text = welcomeMessage
    }

    private fun hasEnteredCorrectCredentials(usernameForm: String, passwordForm: String): Boolean {
        return usernameForm.contentEquals(USER_NAME_CORRECT_VALUE) && passwordForm.contentEquals(
            PASSWORD_CORRECT_VALUE)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBoolean(IS_LOGGED_IN, isLoggedIn)
        outState.putString(LOGGED_IN_USERNAME,loggedInUser)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        isLoggedIn = savedInstanceState.getBoolean(IS_LOGGED_IN, false)
        loggedInUser = savedInstanceState.getString(LOGGED_IN_USERNAME, "")

        if (isLoggedIn && loggedInUser.isNotBlank()){
            setLoggedIn(loggedInUser)
        }
    }


}