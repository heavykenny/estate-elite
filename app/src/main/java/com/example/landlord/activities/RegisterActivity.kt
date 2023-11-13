package com.example.landlord.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.landlord.R
import com.example.landlord.repositories.UserRepository
import com.example.landlord.utils.Utils

class RegisterActivity : ComponentActivity() {
    // Declare the variables for the views
    private var fullnameEditText: EditText? = null
    private var emailEditText: EditText? = null
    private var registerPasswordEditText: EditText? = null
    private var registerButton: Button? = null
    private var loginTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Bind the variables to the views
        fullnameEditText = findViewById(R.id.fullnameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        registerPasswordEditText = findViewById(R.id.registerPasswordEditText)
        registerButton = findViewById(R.id.registerButton)
        loginTextView = findViewById(R.id.loginTextView)

        // Set the click listener for the register button
        registerButton!!.setOnClickListener {
            // Split the fullname into first and last name
            val fullname = fullnameEditText!!.text.toString().split(" ")

            // Register the user
            UserRepository(this).register(
                fullname[0],
                fullname[1],
                emailEditText!!.text.toString(),
                registerPasswordEditText!!.text.toString()
            ) { user ->
                if (user != null) {
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_LONG).show()
                    Utils.saveUser(this, user)
                    intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Registration failed", Toast.LENGTH_LONG).show()
                }
            }
        }

        loginTextView!!.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
