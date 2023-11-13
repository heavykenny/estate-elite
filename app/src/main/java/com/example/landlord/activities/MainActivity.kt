package com.example.landlord.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.landlord.R
import com.example.landlord.fragments.MapPropertyFragment
import com.example.landlord.fragments.PropertiesListFragment
import com.example.landlord.models.Property
import com.example.landlord.repositories.PropertyRepository
import com.example.landlord.utils.NavigationUtil
import com.example.landlord.utils.Utils

class MainActivity : AppCompatActivity(), PropertiesListFragment.PropertiesCountCallback {
    // Declare the variables
    private lateinit var propertiesListFragment: PropertiesListFragment
    private lateinit var mapPropertyFragment: MapPropertyFragment
    private lateinit var totalPropertiesTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var sortSpinner: Spinner
    private lateinit var toggleButton: Button

    // Create an empty ArrayList of Property objects
    private var propertyLists = ArrayList<Property>()

    override fun onCreate(savedInstanceState: Bundle?) {
        // Check if user is logged in and is admin
        if (Utils.isLoggedIn(this)) {
            val user = Utils.getUser(this)
            // If user is admin and is active, navigate to admin dashboard
            if (user.iSAdmin && Utils.isAdminActive(this)) {
                val intent = Intent(this, AdminDashboardActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)

                // If user is landlord and is active, navigate to landlord dashboard
            } else if (Utils.isLandlordActive(this)) {
                val intent = Intent(this, LandlordDashboardActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set the toolbar title
        val toolbarTitle = findViewById<TextView>(R.id.toolbarTitle)
        toolbarTitle.text = "Estate Elite"

        val homeLinearLayout = findViewById<LinearLayout>(R.id.linLayoutHome)
        NavigationUtil().navigate(this, homeLinearLayout)

        // Bind the variables to the views
        totalPropertiesTextView = findViewById(R.id.totalPropertiesTextView)
        sortSpinner = findViewById(R.id.sortSpinner)
        progressBar = findViewById(R.id.progressBar)
        toggleButton = findViewById(R.id.mapListToggleButton)

        setupSortSpinner() // Setup the sort spinner

        propertiesListFragment = PropertiesListFragment()  // Create the instance
        mapPropertyFragment = MapPropertyFragment()        // Create the instance

        // Initially show the propertiesListFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, propertiesListFragment)
            .commit()

        // Set the click listener for the toggle button
        toggleButton.setOnClickListener {
            toggleView()
        }

        // Load the data
        loadData()
    }

    private fun setupSortSpinner() {
        val sorts = arrayOf(
            "Price: Low to High",
            "Price: High to Low",
            "Date: Newest First",
            "Date: Oldest First",
            "Size: Largest First",
            "Size: Smallest First"
        )
        val sortAdapter = ArrayAdapter(this, R.layout.spinner_item, sorts)
        sortSpinner.adapter = sortAdapter
        sortAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun loadData() {
        // Show the progress bar
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.VISIBLE

        PropertyRepository(this).getProperties { properties ->
            progressBar.visibility = View.GONE
            // Set the properties
            propertyLists = properties as ArrayList<Property>
            propertiesListFragment.setProperties(propertyLists)
            mapPropertyFragment.setProperties(properties)
        }
    }

    private fun toggleView() {
        // Toggle the view
        if (propertiesListFragment.isVisible) {
            toggleButton.text = "List"
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, mapPropertyFragment)
                .addToBackStack(null)
                .commit()
        } else {
            toggleButton.text = "Map"
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, propertiesListFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun updatePropertiesCount(count: Int) {
        // Update the total properties count
        totalPropertiesTextView.text = "$count properties"
    }
}

