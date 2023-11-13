package com.example.landlord.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.landlord.R
import com.example.landlord.repositories.AdminRepository

class AdminAddLandlordActivity : AppCompatActivity() {
    // Declare variables for the views
    private var firstNameEditText: EditText? = null
    private var lastNameEditText: EditText? = null
    private var landlordEmailEditText: EditText? = null
    private var landlordPhoneEditText: EditText? = null
    private var addLandlordButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_add_landlord)

        // Initialize the views and buttons in the layout
        firstNameEditText = findViewById(R.id.firstNameEditText)
        lastNameEditText = findViewById(R.id.lastNameEditText)
        landlordEmailEditText = findViewById(R.id.landlordEmailEditText)
        landlordPhoneEditText = findViewById(R.id.landlordPhoneEditText)
        addLandlordButton = findViewById(R.id.addLandlordButton)

        // Set the click listener for the add landlord button
        addLandlordButton?.setOnClickListener {
            adminAddLandlord()
        }
    }

    private fun adminAddLandlord() {
        // Get the values from the edit texts
        val firstName = firstNameEditText?.text.toString()
        val lastName = lastNameEditText?.text.toString()
        val email = landlordEmailEditText?.text.toString()
        val phone = landlordPhoneEditText?.text.toString()

        // Call the add landlord function in the repository
        AdminRepository(this).addLandlord(firstName, lastName, email, phone) { user ->
            if (user != null) {
                // If the user is not null, then the landlord was added successfully
                Toast.makeText(this, "Landlord added successfully", Toast.LENGTH_LONG).show()
                val intent = Intent(this, AdminDashboardActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Landlord addition failed", Toast.LENGTH_LONG).show()
            }
        }
    }
}