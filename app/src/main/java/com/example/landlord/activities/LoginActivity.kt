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

class LoginActivity : ComponentActivity() {
    // Declare the variables
    private var registerTextView: TextView? = null
    private var loginButton: Button? = null
    private var emailEditText: EditText? = null
    private var passwordEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Bind the variables to the views
        registerTextView = findViewById(R.id.registerTextView)
        loginButton = findViewById(R.id.loginButton)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        // Set the click listeners
        registerTextView!!.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        loginButton!!.setOnClickListener {
            UserRepository(this).login(
                emailEditText!!.text.toString(),
                passwordEditText!!.text.toString()
            ) { user ->
                if (user != null) {
                    // Save user to shared preferences
                    Utils.saveUser(this, user)
                    intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    // Show error message
                    Toast.makeText(this, "Login failed", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}