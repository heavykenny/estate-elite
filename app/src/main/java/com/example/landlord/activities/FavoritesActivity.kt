package com.example.landlord.activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.landlord.R
import com.example.landlord.adapters.FavoritesPropertiesAdapter
import com.example.landlord.models.Property
import com.example.landlord.repositories.PropertyRepository
import com.example.landlord.utils.NavigationUtil
import com.example.landlord.utils.Utils

class FavoritesActivity : AppCompatActivity(), FavoritesPropertiesAdapter.OnIconClickListener {
    // Declare variables for the views
    private var propertyLists = ArrayList<Property>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        // Set the toolbar title
        val toolbarTitle = findViewById<TextView>(R.id.toolbarTitle)
        toolbarTitle.text = "Saved properties"

        // Initialize the views and buttons in the layout
        val signInButton = findViewById<Button>(R.id.signInButton)
        val searchPropertiesButton = findViewById<Button>(R.id.searchPropertiesButton)
        val favLinearLayout = findViewById<LinearLayout>(R.id.linLayoutFavorites)
        val noSavedPropertiesLinearLayout =
            findViewById<LinearLayout>(R.id.noSavedPropertiesLinearLayout)
        val savedPropertiesLinearLayout =
            findViewById<LinearLayout>(R.id.savedPropertiesLinearLayout)

        NavigationUtil().navigate(this, favLinearLayout)

        // Spinner for sorting
        val sortSpinner: Spinner = findViewById(R.id.sortSpinner)
        val sorts = arrayOf(
            "Price: Low to High",
            "Price: High to Low",
            "Date: Newest First",
            "Date: Oldest First",
            "Size: Largest First",
            "Size: Smallest First"
        )

        // Create an ArrayAdapter using a simple spinner layout and languages array
        val sortAdapter = ArrayAdapter(this, R.layout.spinner_item, sorts)
        sortSpinner.adapter = sortAdapter
        sortAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        noSavedPropertiesLinearLayout.visibility = LinearLayout.VISIBLE

        // Set the click listener for the add landlord button
        if (Utils.isLoggedIn(this)) {
            signInButton.visibility = Button.GONE
            searchPropertiesButton.visibility = Button.VISIBLE

            // Get the user's favorite properties
            val user = Utils.getUser(this)
            PropertyRepository(this).getFavoriteProperties(user) { properties ->
                // If the properties list is not empty, set the recycler view
                if (properties.isNotEmpty()) {
                    propertyLists = properties as ArrayList<Property>
                    recyclerView = findViewById(R.id.favoritePropertiesRecyclerView)
                    // Set visibility of the views
                    recyclerView.visibility = RecyclerView.VISIBLE
                    searchPropertiesButton.visibility = Button.GONE
                    savedPropertiesLinearLayout.visibility = LinearLayout.GONE
                    noSavedPropertiesLinearLayout.visibility = LinearLayout.GONE

                    recyclerView.layoutManager = LinearLayoutManager(this)
                    recyclerView.adapter = FavoritesPropertiesAdapter(propertyLists, this)

                }
            }
        }

        signInButton.setOnClickListener {
            // Navigate to the login activity
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            val options = ActivityOptions.makeCustomAnimation(this, 0, 0)
            startActivity(intent, options.toBundle())
        }


        searchPropertiesButton.setOnClickListener {
            // Navigate to the main activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onPropertyClicked(position: Int) {
        // Navigate to the property details activity
        val intent = Intent(this, PropertyDetailActivity::class.java)
        intent.putExtra("propertyId", propertyLists[position].id)
        ContextCompat.startActivity(this, intent, null)
    }
}