package com.example.landlord.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.landlord.R
import com.example.landlord.activities.AdminDashboardActivity
import com.example.landlord.utils.Utils

class AdminProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.admin_profile_fragment, container, false)

        // Bind the variables to the views in the layout
        val txtViewDashboard = view.findViewById<TextView>(R.id.txtViewDashboard)
        val txtViewManageProperty = view.findViewById<TextView>(R.id.txtViewManageProperty)
        val txtViewManageUsers = view.findViewById<TextView>(R.id.txtViewManageUsers)
        val txtViewManageLandlords = view.findViewById<TextView>(R.id.txtViewManageLandlords)
        val txtViewLogout = view.findViewById<TextView>(R.id.txtViewLogout)
        val txtViewSwitchToUserAccount =
            view.findViewById<TextView>(R.id.txtViewSwitchToUserAccount)

        // Add click listeners to the views in the layout
        txtViewSwitchToUserAccount!!.setOnClickListener {
            Utils.switchToUserAccount(requireActivity())
        }

        txtViewDashboard.setOnClickListener {
            val intent = Intent(activity, AdminDashboardActivity::class.java)
            startActivity(intent)
        }

        txtViewManageProperty.setOnClickListener {
            val intent = Intent(activity, AdminDashboardActivity::class.java)
            startActivity(intent)
        }

        txtViewManageUsers.setOnClickListener {
            val intent = Intent(activity, AdminDashboardActivity::class.java)
            startActivity(intent)
        }

        txtViewManageLandlords.setOnClickListener {
            val intent = Intent(activity, AdminDashboardActivity::class.java)
            startActivity(intent)
        }

        txtViewLogout.setOnClickListener {
            Utils.logout(requireActivity())
        }

        return view
    }
}
