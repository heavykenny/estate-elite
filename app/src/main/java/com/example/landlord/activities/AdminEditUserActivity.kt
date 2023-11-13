package com.example.landlord.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.landlord.R
import com.example.landlord.models.User
import com.example.landlord.repositories.UserRepository

class AdminEditUserActivity : AppCompatActivity() {
    // Declare variables for the views
    private var firstNameEditText: EditText? = null
    private var lastNameEditText: EditText? = null
    private var editEmailEditText: EditText? = null
    private var phoneEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var addressEditText: EditText? = null
    private var saveChangesButton: Button? = null
    private var userDetails: User? = null

    private val userId: Int by lazy {
        intent.getIntExtra("userId", 0)
    }

    private lateinit var roleSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_edit_user)

        // Initialize the views and buttons in the layout
        firstNameEditText = findViewById(R.id.firstNameEditText)
        lastNameEditText = findViewById(R.id.lastNameEditText)
        editEmailEditText = findViewById(R.id.editEmailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        phoneEditText = findViewById(R.id.phoneEditText)
        addressEditText = findViewById(R.id.addressEditText)
        saveChangesButton = findViewById(R.id.saveChangesButton)
        roleSpinner = findViewById(R.id.roleSpinner)

        // Set the click listener for the add landlord button
        ArrayAdapter.createFromResource(
            this,
            R.array.role_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            roleSpinner.adapter = adapter
        }

        // Set the click listener for the add landlord button
        roleSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                userDetails?.role_id = position.toString()
            }
        }

        // Call the add landlord function in the repository
        UserRepository(this).getUserById(userId) { user ->
            userDetails = user
            // If the user is not null, then the landlord was added successfully
            // Set the values of the edit texts
            firstNameEditText?.setText(user?.first_name)
            lastNameEditText?.setText(user?.last_name)
            editEmailEditText?.setText(user?.email)
            phoneEditText?.setText(user?.phone_number)
            addressEditText?.setText(user?.address)
            roleSpinner.setSelection(user?.role_id!!.toInt())
        }

        // Set the click listener for the add landlord button
        saveChangesButton?.setOnClickListener {
            val firstName = firstNameEditText?.text.toString()
            val lastName = lastNameEditText?.text.toString()
            val email = editEmailEditText?.text.toString()
            val phone = phoneEditText?.text.toString()
            val address = addressEditText?.text.toString()
            val password = passwordEditText?.text.toString()

            // Call the add landlord function in the repository
            userDetails?.first_name = firstName
            userDetails?.last_name = lastName
            userDetails?.email = email
            userDetails?.phone_number = phone
            userDetails?.address = address

            // Call the add landlord function in the repository
            if (userDetails != null) {
                UserRepository(this).updateUser(
                    userDetails!!, password
                ) { user ->
                    if (user != null) {
                        Toast.makeText(this, "User updated successfully", Toast.LENGTH_LONG).show()
                        intent = Intent(this, AdminDashboardActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "User update failed", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}