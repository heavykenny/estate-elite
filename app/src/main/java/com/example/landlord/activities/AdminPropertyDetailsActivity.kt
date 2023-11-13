package com.example.landlord.activities

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.landlord.R
import com.example.landlord.fragments.PropertyDetailFragment

class AdminPropertyDetailsActivity : AppCompatActivity() {

    // Get the property id from the intent
    private val propertyId: Int by lazy {
        intent.getIntExtra("propertyId", 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_property_details)

        // Set the toolbar title
        val toolbarTitle = findViewById<TextView>(R.id.toolbarTitle)
        toolbarTitle.text = "Property Details"

        // Set the fragment
        val fragmentContainer = findViewById<FrameLayout>(R.id.fragmentContainer)

        // If the fragment container is not null, set the fragment
        if (fragmentContainer != null) {
            if (savedInstanceState != null) {
                return
            }

            // Create a new Fragment to be placed in the activity layout
            val propertyFragment = PropertyDetailFragment.newInstance(propertyId)
            propertyFragment.arguments = intent.extras

            // Add the fragment to the 'fragment_container' FrameLayout
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, propertyFragment)
                .commit()
        }
    }
}