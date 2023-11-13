package com.example.landlord.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.landlord.R
import com.example.landlord.activities.PropertyDetailActivity
import com.example.landlord.models.Property
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapPropertyFragment : Fragment(), OnMapReadyCallback {
    // Declare variables
    private var propertyLists: ArrayList<Property> = ArrayList()
    private lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map_property, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapPropertyFragmentContainerView) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    fun setProperties(properties: ArrayList<Property>) {
        this.propertyLists = properties
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        propertyLists.forEach { property ->
            property.latitude?.toDoubleOrNull()?.let { lat ->
                property.longitude?.toDoubleOrNull()?.let { lng ->
                    val location = LatLng(lat, lng)
                    val marker = googleMap.addMarker(
                        MarkerOptions().position(location).title(property.name)
                    )
                    marker?.tag = property
                }
            }
        }

        // Zoom to the first property
        propertyLists.firstOrNull()?.let { firstProperty ->
            firstProperty.latitude?.toDoubleOrNull()?.let { lat ->
                firstProperty.longitude?.toDoubleOrNull()?.let { lng ->
                    val firstPropertyLocation = LatLng(lat, lng)
                    googleMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            firstPropertyLocation,
                            10f
                        )
                    )
                }
            }
        }

        // Set the click listener for the markers
        googleMap.setOnMarkerClickListener { marker ->
            val property = marker.tag as? Property
            property?.let {
                showDialogWithPropertyDetails(it)
            }
            true
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showDialogWithPropertyDetails(property: Property) {
        // Inflate the layout for the dialog
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.property_dialog, null)
        // Bind the variables to the views in the layout
        val tvPropertyName: TextView = dialogView.findViewById(R.id.tvPropertyName)
        val tvPropertyDetails: TextView = dialogView.findViewById(R.id.tvPropertyDetails)
        val btnViewProperty: Button = dialogView.findViewById(R.id.btnViewProperty)
        val btnClose: Button = dialogView.findViewById(R.id.btnClose)

        // Set the text for the views in the layout
        tvPropertyName.text = property.name
        tvPropertyDetails.text = "Address: ${property.address}\n" +
                "Location: ${property.location}\n" +
                "Price: £ ${property.price}"

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        // Set the click listener for the buttons
        btnViewProperty.setOnClickListener {
            val intent = Intent(requireContext(), PropertyDetailActivity::class.java)
            intent.putExtra("propertyId", property.id)
            startActivity(intent)
            dialog.dismiss()
        }

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

}
