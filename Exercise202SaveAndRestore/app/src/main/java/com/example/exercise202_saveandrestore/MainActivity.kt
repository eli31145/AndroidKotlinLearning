package com.example.exercise202_saveandrestore

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {

    private val discountButton: Button
    get() = findViewById(R.id.discount_button)

    private val firstName: EditText
    get() = findViewById(R.id.first_name)

    private val lastName: EditText
    get() = findViewById(R.id.last_name)

    private val email: EditText
    get() = findViewById(R.id.email)

    private val discountCodeConfirmation: TextView
    get() = findViewById(R.id.discount_code_confirmation)

    private val discountCode: TextView
    get() = findViewById(R.id.discount_code)

    //the way we did below is correct, but it will return null reference as activity_main layout is
    //yet to be set. Only if we call get() when we need it will it work

    /*private val firstName: EditText = findViewById(R.id.first_name)
    private val lastName: EditText = findViewById(R.id.last_name)
    private val email: EditText = findViewById(R.id.email)
    private val discountCodeConfirmation: TextView = findViewById(R.id.discount_code_confirmation)
    private val discountCode: TextView = findViewById(R.id.discount_code)*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        discountButton.setOnClickListener {
            val firstName = firstName.text.toString().trim()
            val lastName = lastName.text.toString().trim()
            val email = email.text.toString().trim()

            if(firstName.isBlank() || lastName.isBlank() || email.isBlank()){
                Toast.makeText(this, getString(R.string.add_text_validation), Toast.LENGTH_SHORT).show()
            } else {
                val fullName = firstName.plus(" ").plus(lastName)
                discountCodeConfirmation.text = getString(R.string.discount_code_confirmation, fullName)
                //generates discount code
                discountCode.text = UUID.randomUUID().toString().take(8)
                    .uppercase(Locale.getDefault())

                hideKeyboard()
                clearInputFields()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState")

        outState.putString(DISCOUNT_CODE, discountCode.text.toString())
        outState.putString(DISCOUNT_CONFIRMATION_MESSAGE, discountCodeConfirmation.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(TAG, "onRestoreInstanceState")

        discountCode.text = savedInstanceState.getString(DISCOUNT_CODE, "")
        discountCodeConfirmation.text = savedInstanceState.getString(DISCOUNT_CONFIRMATION_MESSAGE, "")
    }

    //what is this doing
    private fun hideKeyboard(){
        if(currentFocus != null){
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    private fun clearInputFields(){
        firstName.text.clear()
        lastName.text.clear()
        email.text.clear()
    }

    companion object {
        private const val TAG = "SaveAndRestoreExercise"
        private const val DISCOUNT_CODE = "DISCOUNT_CODE"
        private const val DISCOUNT_CONFIRMATION_MESSAGE = "DISCOUNT_CONFIRMATION_MESSAGE"
    }

}