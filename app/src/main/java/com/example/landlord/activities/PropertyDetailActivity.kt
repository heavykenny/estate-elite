package com.example.landlord.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.landlord.R
import com.example.landlord.fragments.PropertyDetailFragment
import com.example.landlord.utils.NavigationUtil

class PropertyDetailActivity : AppCompatActivity() {
    // Get the property id from the intent
    private val propertyId: Int by lazy {
        intent.getIntExtra("propertyId", 0)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_detail)

        // Set the toolbar title
        val toolbarTitle = findViewById<TextView>(R.id.toolbarTitle)
        toolbarTitle.text = "Property Details"

        // Set the navigation
        val homeLinearLayout = findViewById<LinearLayout>(R.id.linLayoutHome)
        NavigationUtil().navigate(this, homeLinearLayout)

        // Set the fragment container
        val fragmentContainer = findViewById<FrameLayout>(R.id.fragmentContainer)
        if (fragmentContainer != null) {
            if (savedInstanceState != null) {
                return
            }

            // Create a new fragment to be placed in the activity layout and pass the property id
            val propertyFragment = PropertyDetailFragment.newInstance(propertyId)
            propertyFragment.arguments = intent.extras
            supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, propertyFragment)
                .commit()
        }
    }

}
