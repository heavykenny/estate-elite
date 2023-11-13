package com.example.landlord.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.landlord.R
import com.example.landlord.activities.AdminAddPropertyActivity
import com.example.landlord.activities.AdminPropertyDetailsActivity
import com.example.landlord.adapters.PropertiesEditAdapter
import com.example.landlord.models.Property
import com.example.landlord.repositories.AdminRepository
import com.example.landlord.utils.Utils
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AdminPropertyFragment : Fragment(), PropertiesEditAdapter.OnPropertyClickListener {

    private lateinit var propertiesRecyclerView: RecyclerView
    private lateinit var addPropertyButton: FloatingActionButton
    private var propertyLists = ArrayList<Property>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_admin_property, container, false)

        propertiesRecyclerView = view.findViewById(R.id.propertiesRecyclerView)
        addPropertyButton = view.findViewById(R.id.addPropertyButton)

        // Set the click listener for the add property button
        addPropertyButton.setOnClickListener {
            val intent = Intent(requireContext(), AdminAddPropertyActivity::class.java)
            startActivity(intent)
        }

        // Get the properties from the database
        if (Utils.isLoggedIn(requireActivity())) {
            val user = Utils.getUser(requireActivity())
            AdminRepository(requireContext()).getProperties(user) {
                propertyLists = it
                propertiesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                propertiesRecyclerView.adapter = PropertiesEditAdapter(propertyLists, this)
            }
        }

        return view
    }

    override fun onViewPropertyClicked(position: Int) {
        val intent = Intent(activity, AdminPropertyDetailsActivity::class.java)
        intent.putExtra("propertyId", propertyLists[position].id)
        ContextCompat.startActivity(requireContext(), intent, null)
    }

    override fun onDeletePropertyClicked(position: Int) {
        val user = Utils.getUser(requireActivity())
        AdminRepository(requireContext()).deleteProperty(propertyLists[position], user) {
            propertyLists.removeAt(position)
            propertiesRecyclerView.adapter?.notifyItemRemoved(position)
        }
    }

    override fun onPropertyClicked(position: Int) {
        val intent = Intent(activity, AdminPropertyDetailsActivity::class.java)
        intent.putExtra("propertyId", propertyLists[position].id)
        ContextCompat.startActivity(requireContext(), intent, null)
    }

    override fun onEditPropertyClicked(position: Int) {
        val intent = Intent(activity, AdminAddPropertyActivity::class.java)
        intent.putExtra("propertyId", propertyLists[position].id)
        ContextCompat.startActivity(requireContext(), intent, null)
    }
}
