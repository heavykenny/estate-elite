package com.example.landlord.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.landlord.R
import com.example.landlord.activities.InquiriesActivity
import com.example.landlord.activities.LandlordDashboardActivity
import com.example.landlord.utils.Utils

class LandlordManageInquiriesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.landlord_profile_fragment, container, false)

        // Bind the variables to the views in the layout
        val txtViewDashboard = view.findViewById<TextView>(R.id.txtViewDashboard)
        val txtViewManageProperty = view.findViewById<TextView>(R.id.txtViewManageProperty)
        val txtViewManageInquiries = view.findViewById<TextView>(R.id.txtViewManageInquiries)
        val txtViewLogout = view.findViewById<TextView>(R.id.txtViewLogout)
        val txtViewSwitchToUserAccount =
            view.findViewById<TextView>(R.id.txtViewSwitchToUserAccount)

        txtViewSwitchToUserAccount!!.setOnClickListener {
            Utils.switchToUserAccount(requireActivity())
        }

        txtViewDashboard.setOnClickListener {
            val intent = Intent(activity, LandlordDashboardActivity::class.java)
            startActivity(intent)
        }

        txtViewManageProperty.setOnClickListener {
            val intent = Intent(activity, LandlordDashboardActivity::class.java)
            startActivity(intent)
        }

        txtViewManageInquiries.setOnClickListener {
            val intent = Intent(activity, InquiriesActivity::class.java)
            startActivity(intent)
        }

        txtViewLogout.setOnClickListener {
            Utils.logout(requireActivity())
        }

        return view
    }
}
