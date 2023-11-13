package com.example.landlord.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.landlord.R
import com.example.landlord.repositories.PropertyRepository
import com.example.landlord.utils.Utils


class AdminAddPropertyActivity : AppCompatActivity() {
    // Declare the variables
    private val selectImage = 1
    private lateinit var imagePath: String
    private lateinit var typeIdSpinner: Spinner
    private lateinit var propertyStatusSpinner: Spinner

    // Declare the button as a global variable
    private var imagePathButton: Button? = null
    private var nameEditText: EditText? = null
    private var locationEditText: EditText? = null
    private var priceEditText: EditText? = null
    private var descriptionEditText: EditText? = null
    private var addPropertyButton: Button? = null
    private var bedroomsEditText: EditText? = null
    private var bathroomsEditText: EditText? = null
    private var addressEditText: EditText? = null
    private var pointsOfInterestEditText: EditText? = null
    private var latitudeEditText: EditText? = null
    private var longitudeEditText: EditText? = null
    private var addPropertyTitle: TextView? = null

    // Set the default value for the property variables
    private var propertyTypeId: Int = 1
    private var typeId: Int = 1
    private var propertyId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_add_property)

        // Initialize the views and buttons in the layout
        nameEditText = findViewById(R.id.nameEditText)
        locationEditText = findViewById(R.id.locationEditText)
        priceEditText = findViewById(R.id.priceEditText)
        descriptionEditText = findViewById(R.id.descriptionEditText)
        addPropertyButton = findViewById(R.id.addPropertyButton)
        bedroomsEditText = findViewById(R.id.bedroomsEditText)
        bathroomsEditText = findViewById(R.id.bathroomsEditText)
        addressEditText = findViewById(R.id.addressEditText)
        pointsOfInterestEditText = findViewById(R.id.pointsOfInterestEditText)
        latitudeEditText = findViewById(R.id.latitudeEditText)
        longitudeEditText = findViewById(R.id.longitudeEditText)
        addPropertyTitle = findViewById(R.id.addPropertyTitle)
        imagePathButton = findViewById(R.id.imagePathButton)

        setupSpinners() // Call the function to setup the spinners

        // Get the property id from the intent
        propertyId = intent.getIntExtra("propertyId", 0)
        if (propertyId != 0) {
            addPropertyTitle?.text = "Edit Property"
            addPropertyButton?.text = "Edit Property"
            loadPropertyDetails(propertyId)
        }

        // Set the click listener for the add property button
        imagePathButton?.setOnClickListener { openGalleryForImage() }

        // Set the click listener for the add property button
        addPropertyButton?.setOnClickListener {
            if (propertyId == 0) {
                addProperty()
            } else {
                updateProperty(propertyId)
            }

        }
    }

    private fun setupSpinners() {
        typeIdSpinner = findViewById(R.id.typeIdSpinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.property_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            typeIdSpinner.adapter = adapter
        }
        typeIdSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                typeId = position + 1
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                typeId = 0
            }
        }

        // Setup for Property Status Spinner
        propertyStatusSpinner = findViewById(R.id.propertyStatusSpinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.property_statuses,
            android.R.layout.simple_spinner_item
        ).also { statusAdapter ->
            statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            propertyStatusSpinner.adapter = statusAdapter
        }

        // Set the on item selected listener for the property status spinner
        propertyStatusSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                propertyTypeId =
                    position + 1 // Set the property type id based on the selected item
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                propertyTypeId = 0 // Set the default value
            }
        }
    }

    private fun updateProperty(propertyId: Int) {
        // Get the values from the EditTexts
        val name = nameEditText?.text.toString()
        val location = locationEditText?.text.toString()
        val price = priceEditText?.text.toString()
        val description = descriptionEditText?.text.toString()
        val bedrooms = bedroomsEditText?.text.toString()
        val bathrooms = bathroomsEditText?.text.toString()
        val address = addressEditText?.text.toString()
        val pointsOfInterest = pointsOfInterestEditText?.text.toString()
        val latitude = latitudeEditText?.text.toString()
        val longitude = longitudeEditText?.text.toString()

        // Call the update property function in the repository
        val user = Utils.getUser(this)
        PropertyRepository(this).updateProperty(
            propertyId,
            imagePath,
            name,
            location,
            price,
            address,
            pointsOfInterest,
            description,
            latitude,
            longitude,
            bedrooms,
            bathrooms,
            typeId,
            propertyTypeId,
            user
        ) {
            val intent = Intent(this, AdminDashboardActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addProperty() {
        // Get the values from the EditTexts
        val name = nameEditText?.text.toString()
        val location = locationEditText?.text.toString()
        val price = priceEditText?.text.toString()
        val description = descriptionEditText?.text.toString()
        val bedrooms = bedroomsEditText?.text.toString()
        val bathrooms = bathroomsEditText?.text.toString()
        val address = addressEditText?.text.toString()
        val pointsOfInterest = pointsOfInterestEditText?.text.toString()
        val latitude = latitudeEditText?.text.toString()
        val longitude = longitudeEditText?.text.toString()

        // Call the add property function in the repository
        val user = Utils.getUser(this)
        PropertyRepository(this).addProperty(
            imagePath,
            name,
            location,
            price,
            address,
            pointsOfInterest,
            description,
            latitude,
            longitude,
            bedrooms,
            bathrooms,
            typeId,
            propertyTypeId,
            user
        ) {
            val intent = Intent(this, AdminDashboardActivity::class.java)
            startActivity(intent)
        }
    }

    // Function to open the gallery for image selection
    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, selectImage)
    }

    // Function to load the property details
    private fun loadPropertyDetails(propertyId: Int) {
        if (propertyId != 0) {
            PropertyRepository(this).getProperty(propertyId) { property ->
                // Set the values to the EditTexts
                nameEditText?.setText(property?.name)
                locationEditText?.setText(property?.location)
                priceEditText?.setText(property?.price.toString())
                descriptionEditText?.setText(property?.description)
                bedroomsEditText?.setText(property?.bedrooms.toString())
                bathroomsEditText?.setText(property?.bathrooms.toString())
                addressEditText?.setText(property?.address)
                pointsOfInterestEditText?.setText(property?.points_of_interest)
                latitudeEditText?.setText(property?.latitude.toString())
                longitudeEditText?.setText(property?.longitude.toString())

                // Set the property type and status spinners
                typeIdSpinner.setSelection(
                    getIndexFromValue(
                        R.array.property_types,
                        property?.type_id
                    )
                )
                propertyStatusSpinner.setSelection(
                    getIndexFromValue(
                        R.array.property_statuses,
                        property?.property_type_id
                    )
                )
            }
        }
    }

    // Function to get the index from the value
    private fun getIndexFromValue(arrayId: Int, value: Int?): Int {
        val array = resources.getStringArray(arrayId)
        return array.indexOf(value.toString())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == selectImage && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            imagePath = getPathFromUri(selectedImageUri!!)
            // Set the path to the EditText
            imagePathButton!!.text = "Image Selected"
        }
    }

    // Function to get the path from the uri
    private fun getPathFromUri(uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            it.moveToFirst()
            return it.getString(columnIndex)
        }
        return uri.path ?: ""
    }
}