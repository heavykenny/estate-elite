package com.example.landlord.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.landlord.R
import com.example.landlord.utils.NavigationUtil
import com.example.landlord.utils.Utils

class SettingsActivity : ComponentActivity() {
    // Declare the variables for the views
    private var txtViewLogout: TextView? = null
    private var txtViewLogin: TextView? = null
    private var linLayoutProfile: LinearLayout? = null
    private var txtViewSwitchToUserAccount: TextView? = null
    private var txtViewSwitchToAdminAccount: TextView? = null
    private var txtViewSwitchToLandlordAccount: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Bind the variables to the views
        txtViewSwitchToAdminAccount = findViewById(R.id.txtViewSwitchToAdminAccount)
        txtViewSwitchToUserAccount = findViewById(R.id.txtViewSwitchToUserAccount)
        txtViewSwitchToLandlordAccount = findViewById(R.id.txtViewSwitchToLandlordAccount)

        // Set the toolbar title
        val toolbarTitle = findViewById<TextView>(R.id.toolbarTitle)
        toolbarTitle.text = "User Account Settings"

        linLayoutProfile = findViewById(R.id.linLayoutProfile)
        NavigationUtil().navigate(this, linLayoutProfile)

        txtViewLogout = findViewById(R.id.txtViewLogout)
        txtViewLogout!!.visibility = TextView.GONE
        txtViewLogin = findViewById(R.id.txtViewLogin)

        val languageSpinner: Spinner = findViewById(R.id.languageSpinner)
        val languages = arrayOf("English", "French", "Spanish")
        val languageAdapter = ArrayAdapter(this, R.layout.spinner_item, languages)
        languageSpinner.adapter = languageAdapter
        languageAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedLanguage = languages[position]
                // Handle the selected language
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        val currencySpinner: Spinner = findViewById(R.id.currencySpinner)
        val currencies = arrayOf("USD", "EUR", "GBP")
        val currencyAdapter = ArrayAdapter(this, R.layout.spinner_item, currencies)
        currencySpinner.adapter = currencyAdapter
        currencyAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        currencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedCurrency = currencies[position]
                // Handle the selected currency
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        // Check if the user is logged in
        if (Utils.isLoggedIn(this)) {
            val user = Utils.getUser(this)
            txtViewLogout!!.visibility = TextView.VISIBLE
            txtViewLogin!!.visibility = TextView.GONE
            if (user.iSAdmin) {
                txtViewSwitchToAdminAccount!!.visibility = TextView.VISIBLE
            } else if (user.isLandlord) {
                txtViewSwitchToLandlordAccount!!.visibility = TextView.VISIBLE
            }
        } else {
            txtViewLogin!!.visibility = TextView.VISIBLE
        }

        // Set the click listeners
        txtViewLogout!!.setOnClickListener {
            Utils.logout(this)
            txtViewLogout!!.visibility = TextView.GONE
        }

        txtViewLogin!!.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        txtViewSwitchToUserAccount?.setOnClickListener {
            Utils.switchToUserAccount(this)
            txtViewSwitchToUserAccount?.visibility = TextView.GONE
        }

        val txtViewSwitchToAdminAccount = findViewById<TextView>(R.id.txtViewSwitchToAdminAccount)
        txtViewSwitchToAdminAccount.setOnClickListener {
            Utils.switchToAdminAccount(this)
        }

        val txtViewSwitchToLandlordAccount =
            findViewById<TextView>(R.id.txtViewSwitchToLandlordAccount)
        txtViewSwitchToLandlordAccount.setOnClickListener {
            Utils.switchToLandlordAccount(this)
        }

        val txtViewDashboard = findViewById<TextView>(R.id.txtViewDashboard)
        txtViewDashboard.setOnClickListener {
            Utils.switchToDashboard(this)
        }
    }
}
