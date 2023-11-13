package com.example.landlord.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.landlord.R
import com.example.landlord.activities.AdminAddLandlordActivity
import com.example.landlord.adapters.LandlordsAdapter
import com.example.landlord.models.User
import com.example.landlord.repositories.AdminRepository
import com.example.landlord.utils.Utils
import com.google.android.material.floatingactionbutton.FloatingActionButton


class AdminLandlordFragment : Fragment(), LandlordsAdapter.OnLandlordClickListener {

    // Declare variables
    private lateinit var landlordsRecyclerView: RecyclerView
    private lateinit var addLandlordButton: FloatingActionButton
    private lateinit var landlordsAdapter: LandlordsAdapter

    private var landlordsList = ArrayList<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_landlord, container, false)
        // Bind the variables to the views in the layout
        landlordsRecyclerView = view.findViewById(R.id.landlordsRecyclerView)
        addLandlordButton = view.findViewById(R.id.addLandlordButton)

        // Validate if the user is logged in
        if (Utils.isLoggedIn(requireActivity())) {
            val user = Utils.getUser(requireActivity())
            // Get the landlords from the database
            AdminRepository(requireContext()).getLandlords(user) {
                // Check if the landlords were retrieved successfully
                landlordsList = it
                landlordsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                landlordsAdapter = LandlordsAdapter(landlordsList, this)
                landlordsRecyclerView.adapter = landlordsAdapter
            }
        }

        addLandlordButton.setOnClickListener {
            val intent = Intent(requireContext(), AdminAddLandlordActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    override fun onLandlordClicked(position: Int) {
        Utils.editUser(requireActivity(), landlordsList[position])
    }

    override fun onEditUserClicked(position: Int) {
        Utils.editUser(requireActivity(), landlordsList[position])
    }

    override fun onDeleteUserClicked(position: Int) {
        Utils.deleteUser(requireActivity(), landlordsList[position])
    }
}