package com.example.landlord.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.landlord.R
import com.example.landlord.activities.LoginActivity
import com.example.landlord.activities.MessagingActivity
import com.example.landlord.repositories.PropertyRepository
import com.example.landlord.utils.Utils
import com.example.landlord.utils.Utils.Companion.capitalizeFirst
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class PropertyDetailFragment : Fragment(), OnMapReadyCallback {
    // Declare variables
    private val propertyId: Int by lazy {
        arguments?.getInt("propertyId") ?: 0
    }

    // Bind the variables to the views in the layout
    private lateinit var propertyName: TextView
    private lateinit var propertyPrice: TextView
    private lateinit var bedroomTextView: TextView
    private lateinit var bathroomTextView: TextView
    private lateinit var rentSaleLeaseTextView: TextView
    private lateinit var dateListedTextView: TextView
    private lateinit var landlordNameTextView: TextView
    private lateinit var propertyLocation: TextView
    private lateinit var callIconImageView: ImageView
    private lateinit var emailIconImageView: ImageView
    private lateinit var shareIconImageView: ImageView
    private lateinit var pointsOfInterestTextView: TextView
    private lateinit var propertyDescriptionTextView: TextView
    private lateinit var propertyAddressTextView: TextView
    private lateinit var messageLandlordButton: Button


    override fun onCreateView(
        inflater: LayoutInflater, viewGroup: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_property_detail, viewGroup, false)

        propertyName = view.findViewById(R.id.propertyNameTextView)
        propertyPrice = view.findViewById(R.id.propertyPriceTextView)
        bedroomTextView = view.findViewById(R.id.bedroomTextView)
        bathroomTextView = view.findViewById(R.id.bathroomTextView)
        rentSaleLeaseTextView = view.findViewById(R.id.rentSaleLeaseTextView)
        dateListedTextView = view.findViewById(R.id.dateListedTextView)
        landlordNameTextView = view.findViewById(R.id.landlordNameTextView)
        propertyLocation = view.findViewById(R.id.propertyLocationTextView)
        callIconImageView = view.findViewById(R.id.callIconImageView)
        emailIconImageView = view.findViewById(R.id.emailIconImageView)
        shareIconImageView = view.findViewById(R.id.shareIconImageView)
        pointsOfInterestTextView = view.findViewById(R.id.pointsOfInterestTextView)
        propertyDescriptionTextView = view.findViewById(R.id.propertyDescriptionTextView)
        propertyAddressTextView = view.findViewById(R.id.propertyAddressTextView)
        messageLandlordButton = view.findViewById(R.id.messageLandlordButton)

        return view
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Bind the variables to the views in the layout
        PropertyRepository(requireContext()).getProperty(propertyId) { property ->
            propertyName.text = property?.name
            propertyPrice.text = "£" + property?.price
            bedroomTextView.text = property?.bedrooms.toString() + " beds"
            bathroomTextView.text = property?.bathrooms.toString() + " baths"
            rentSaleLeaseTextView.text = property?.property_type.toString().capitalizeFirst()
            dateListedTextView.text =
                Utils.formatTimestampForDisplay(property?.created_at.toString())
            landlordNameTextView.text =
                "Listed by: " + property?.landlord_first_name + " " + property?.landlord_last_name
            propertyLocation.text = "Location: " + property?.location
            pointsOfInterestTextView.text = "Point of Interest: " + property?.points_of_interest
            propertyDescriptionTextView.text = "Property Description: " + property?.description
            propertyAddressTextView.text = "Address: " + property?.address

            // Set the click listeners for the icons
            callIconImageView.setOnClickListener {
                Utils.callLandlord(requireActivity(), property)
            }

            emailIconImageView.setOnClickListener {
                Utils.emailLandlord(requireActivity(), property)
            }

            shareIconImageView.setOnClickListener {
                Utils.shareProperty(requireActivity(), property)
            }

            val mapFragment =
                childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
            mapFragment.getMapAsync(this)

            // Set the click listener for the message landlord button
            messageLandlordButton.setOnClickListener {
                if (Utils.isLoggedIn(requireActivity())) {
                    val intent = Intent(requireActivity(), MessagingActivity::class.java)
                    intent.putExtra("propertyId", property?.id)
                    intent.putExtra("landlordId", property?.landlord_id)
                    startActivity(intent)
                } else {
                    val intent = Intent(requireActivity(), LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
        }

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        PropertyRepository(requireContext()).getProperty(propertyId) { property ->
            property?.let {
                val lat = it.latitude?.toDoubleOrNull()
                val lng = it.longitude?.toDoubleOrNull()

                if (lat != null && lng != null) {
                    val propertyLocation = LatLng(lat, lng)
                    googleMap.addMarker(MarkerOptions().position(propertyLocation).title(it.name))
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(propertyLocation, 15f))
                }
            }
        }
    }

    companion object {
        /**
         * Creates a new instance of PropertyDetailFragment with a given propertyId.
         *
         * @param propertyId The ID of the property to be displayed in the fragment.
         * @return A new instance of PropertyDetailFragment with propertyId passed as an argument.
         */
        fun newInstance(propertyId: Int): PropertyDetailFragment {
            val fragment = PropertyDetailFragment() // Create a new fragment instance
            val args = Bundle() // Create a new Bundle for fragment arguments
            args.putInt("propertyId", propertyId) // Put the property ID into the Bundle
            fragment.arguments = args // Set the arguments for the fragment
            return fragment // Return the fragment with arguments
        }
    }

}
