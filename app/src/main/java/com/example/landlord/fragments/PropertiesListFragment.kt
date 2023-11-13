package com.example.landlord.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.landlord.R
import com.example.landlord.activities.LoginActivity
import com.example.landlord.activities.PropertyDetailActivity
import com.example.landlord.adapters.PropertiesAdapter
import com.example.landlord.models.Property
import com.example.landlord.repositories.PropertyRepository
import com.example.landlord.utils.Utils

class PropertiesListFragment : Fragment(), PropertiesAdapter.OnIconClickListener {
    // Declare variables
    private var propertyLists = ArrayList<Property>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_properties_list, container, false)
        recyclerView = view.findViewById(R.id.featuredPropertiesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = PropertiesAdapter(propertyLists, this)
        return view
    }

    interface PropertiesCountCallback {
        fun updatePropertiesCount(count: Int)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchProperties()
    }

    private fun fetchProperties() {
        progressBar = requireActivity().findViewById(R.id.progressBar)
        progressBar.visibility = View.VISIBLE

        PropertyRepository(requireContext()).getProperties { properties ->
            progressBar.visibility = View.GONE

            // Update the properties count in the home activity
            (activity as? PropertiesCountCallback)?.updatePropertiesCount(properties.size)

            propertyLists = properties as ArrayList<Property>
            // Set the adapter for the recycler view
            recyclerView.visibility = RecyclerView.VISIBLE
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = PropertiesAdapter(propertyLists, this)
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setProperties(properties: ArrayList<Property>) {
        this.propertyLists = properties
        recyclerView?.adapter?.notifyDataSetChanged()
    }

    override fun onCallIconClicked(position: Int) {
        Utils.callLandlord(requireActivity(), propertyLists[position])
    }

    override fun onEmailIconClicked(position: Int) {
        Utils.emailLandlord(requireActivity(), propertyLists[position])
    }

    override fun onPropertyClicked(position: Int) {
        val intent = Intent(requireContext(), PropertyDetailActivity::class.java)
        intent.putExtra("propertyId", propertyLists[position].id)
        ContextCompat.startActivity(requireContext(), intent, null)
    }

    override fun onFavoriteClicked(position: Int) {
        val property = propertyLists[position]
        // check if user is login in shared preference
        if (Utils.isLoggedIn(requireActivity())) {
            val user = Utils.getUser(requireActivity())
            PropertyRepository(requireContext()).favoriteProperty(property.id, user) { isSaved ->
                if (isSaved) {
                    Toast.makeText(requireContext(), "Property saved", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), "Property not saved", Toast.LENGTH_LONG).show()
                }
            }
        } else {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            ContextCompat.startActivity(requireContext(), intent, null)
        }
    }
}

