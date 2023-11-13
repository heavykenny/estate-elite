package com.example.landlord.activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.landlord.R
import com.example.landlord.adapters.InquiriesAdapter
import com.example.landlord.models.Inquiry
import com.example.landlord.repositories.InquiryRepository
import com.example.landlord.utils.NavigationUtil
import com.example.landlord.utils.Utils

class InquiriesActivity : ComponentActivity(), InquiriesAdapter.OnIconClickListener {

    // Declare variables for the views
    private var linLayoutMessages: LinearLayout? = null
    private var inquiriesList = ArrayList<Inquiry>()
    private lateinit var inquiriesRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inquiries)

        // Set the toolbar title
        val toolbarTitle = findViewById<TextView>(R.id.toolbarTitle)
        toolbarTitle.text = "Inquiries Contacts"

        linLayoutMessages = findViewById(R.id.linLayoutMessages)

        NavigationUtil().navigate(this, linLayoutMessages)

        // Initialize the views and buttons in the layout
        val noInquiriesLinearLayout = findViewById<LinearLayout>(R.id.noInquiriesLinearLayout)
        noInquiriesLinearLayout.visibility = View.VISIBLE

        inquiriesRecyclerView = findViewById(R.id.inquiriesRecyclerView)

        val loginPropertiesLinearLayout =
            findViewById<LinearLayout>(R.id.loginPropertiesLinearLayout)

        inquiriesRecyclerView.visibility = RecyclerView.GONE
        noInquiriesLinearLayout.visibility = View.VISIBLE
        loginPropertiesLinearLayout.visibility = View.VISIBLE

        if (Utils.isLoggedIn(this)) {
            val user = Utils.getUser(this)
            loginPropertiesLinearLayout.visibility = View.GONE

            // Get the inquiries from the repository
            InquiryRepository(this).getInquiries(user) { inquiries ->
                if (inquiries.isNotEmpty()) {
                    inquiriesList = inquiries as ArrayList<Inquiry>
                    inquiriesRecyclerView.visibility = RecyclerView.VISIBLE
                    inquiriesRecyclerView.layoutManager = LinearLayoutManager(this)
                    inquiriesRecyclerView.adapter = InquiriesAdapter(inquiriesList, this)
                    noInquiriesLinearLayout.visibility = View.GONE
                }
            }
        }

        // Set the click listener for the sign in button
        val signInButton = findViewById<Button>(R.id.signInButton)
        signInButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            val options = ActivityOptions.makeCustomAnimation(this, 0, 0)
            startActivity(intent, options.toBundle())
        }
    }

    override fun onPropertyClicked(position: Int) {
        // Navigate to the messaging activity
        val intent = Intent(this, MessagingActivity::class.java)
        intent.putExtra("propertyId", inquiriesList[position].property_id)
        intent.putExtra("landlordId", inquiriesList[position].landlord_id)
        ContextCompat.startActivity(this, intent, null)
    }
}
