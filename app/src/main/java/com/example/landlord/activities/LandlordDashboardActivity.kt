package com.example.landlord.activities

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.landlord.R
import com.example.landlord.fragments.AdminLandlordFragment
import com.example.landlord.fragments.AdminPropertyFragment
import com.example.landlord.fragments.LandlordProfileFragment

class LandlordDashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landlord_dashboard)

        // Set the toolbar title
        val toolbarTitle = findViewById<TextView>(R.id.toolbarTitle)
        toolbarTitle.text = "Landlord Dashboard"

        // Set the fragment container
        findViewById<LinearLayout>(R.id.managePropertiesLayout).setFragmentOnClick(
            AdminPropertyFragment()
        )

        findViewById<LinearLayout>(R.id.manageInquiriesLayout).setFragmentOnClick(
            AdminPropertyFragment()
        )

        findViewById<LinearLayout>(R.id.landlordLinLayoutProperties).setOnClickListener {
            highlightSelected(it)
            replaceFragment(AdminPropertyFragment())
        }

        findViewById<LinearLayout>(R.id.landlordLinLayoutInquiries).setOnClickListener {
            highlightSelected(it)
            replaceFragment(AdminLandlordFragment())
        }

        findViewById<LinearLayout>(R.id.adminLinLayoutProfile).setOnClickListener {
            findViewById<LinearLayout>(R.id.landlordDashboardLinearLayout).visibility = View.GONE
            highlightSelected(it)
            replaceFragment(LandlordProfileFragment())
        }
    }

    // Set the fragment container
    private fun View.setFragmentOnClick(fragment: Fragment) {
        this.setOnClickListener {
            replaceFragment(fragment)
        }
    }

    // Highlight the selected menu item
    private fun highlightSelected(view: View) {
        listOf(
            R.id.landlordLinLayoutInquiries,
            R.id.landlordLinLayoutProperties,
            R.id.adminLinLayoutProfile
        ).forEach { id ->
            findViewById<LinearLayout>(id).setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.oxford_blue_a
                )
            )
        }
        // Highlight the selected menu item
        view.setBackgroundColor(ContextCompat.getColor(this, R.color.french_blue))
    }

    private fun replaceFragment(fragment: Fragment) {
        // Replace the fragment in the fragment container
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
