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
import com.example.landlord.fragments.AdminProfileFragment
import com.example.landlord.fragments.AdminPropertyFragment
import com.example.landlord.fragments.AdminUserFragment
import com.example.landlord.utils.Utils

class AdminDashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        val toolbarTitle = findViewById<TextView>(R.id.toolbarTitle)
        toolbarTitle.text = "Admin Dashboard"

        // Set the fragment on click listeners
        findViewById<LinearLayout>(R.id.manageLandlordsLayout).setFragmentOnClick(
            AdminLandlordFragment()
        )
        findViewById<LinearLayout>(R.id.managePropertiesLayout).setFragmentOnClick(
            AdminPropertyFragment()
        )
        findViewById<LinearLayout>(R.id.manageUsersLayout).setFragmentOnClick(AdminUserFragment())

        // Set the bottom navigation on click listeners
        findViewById<LinearLayout>(R.id.adminLinLayoutProperties).setOnClickListener {
            highlightSelected(it)
            replaceFragment(AdminPropertyFragment())
        }

        findViewById<LinearLayout>(R.id.adminLinLayoutLandlords).setOnClickListener {
            highlightSelected(it)
            replaceFragment(AdminLandlordFragment())
        }

        findViewById<LinearLayout>(R.id.adminLinLayoutProfile).setOnClickListener {
            findViewById<LinearLayout>(R.id.adminDashboardLinearLayout).visibility = View.GONE
            highlightSelected(it)
            replaceFragment(AdminProfileFragment())
        }
    }

    // Set the fragment on click listeners
    private fun View.setFragmentOnClick(fragment: Fragment) {
        this.setOnClickListener {
            highlightSelected(it)
            replaceFragment(fragment)
        }
    }

    // Highlight the selected bottom navigation item
    private fun highlightSelected(view: View) {
        listOf(
            R.id.adminLinLayoutProperties,
            R.id.adminLinLayoutLandlords,
            R.id.adminLinLayoutProfile
        ).forEach { id ->
            findViewById<LinearLayout>(id).setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.oxford_blue_a
                )
            )
        }

        view.setBackgroundColor(ContextCompat.getColor(this, R.color.french_blue))
    }

    // Replace the fragment
    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
