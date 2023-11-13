package com.example.landlord.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.landlord.R
import com.example.landlord.models.Property
import com.example.landlord.repositories.PropertyRepository
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var propertyLists: List<Property>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        //
        PropertyRepository(this).getProperties { properties ->
            propertyLists = properties as ArrayList<Property>
            val mapFragment =
                supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

            mapFragment.getMapAsync(this)
        }
    }

    // This method is called when the map is ready to be used.
    override fun onMapReady(googleMap: GoogleMap) {
        propertyLists.forEach { property ->
            property.latitude?.toDoubleOrNull()?.let { lat ->
                property.longitude?.toDoubleOrNull()?.let { lng ->
                    val location = LatLng(lat, lng)
                    val marker =
                        googleMap.addMarker(MarkerOptions().position(location).title(property.name))
                    if (marker != null) {
                        marker.tag = property
                    }
                }
            }
        }

        // Optionally, move the camera to the first property's location
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
        val customView = LayoutInflater.from(this).inflate(R.layout.property_dialog, null)

        val tvPropertyName: TextView = customView.findViewById(R.id.tvPropertyName)
        val tvPropertyDetails: TextView = customView.findViewById(R.id.tvPropertyDetails)
        val btnViewProperty: Button = customView.findViewById(R.id.btnViewProperty)
        val btnClose: Button = customView.findViewById(R.id.btnClose)

        tvPropertyName.text = property.name
        tvPropertyDetails.text = "Address: ${property.address}\n" +
                "Location: ${property.location}\n" +
                "Price: £ ${property.price}"

        val dialog = AlertDialog.Builder(this)
            .setView(customView)
            .create()

        btnViewProperty.setOnClickListener {
            val intent = Intent(this, PropertyDetailActivity::class.java)
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
