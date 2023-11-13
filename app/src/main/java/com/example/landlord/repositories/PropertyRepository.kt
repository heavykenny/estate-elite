package com.example.landlord.repositories

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.example.landlord.R
import com.example.landlord.logics.NetworkManager
import com.example.landlord.models.Property
import com.example.landlord.models.User
import org.json.JSONException
import org.json.JSONObject

class PropertyRepository(private val context: Context) {

    private val networkManager = NetworkManager(context)

    private val backEndAPI: String by lazy {
        "${context.resources.getString(R.string.base_api_url)}/property"
    }
    
    fun getProperty(propertyId: Int, onPropertyLoaded: (Property?) -> Unit) {

        var property: Property? = null

        networkManager.makeRequest(
            Request.Method.GET,
            "$backEndAPI/viewProperty/$propertyId",
            mapOf(),
            onSuccess = { response ->
                try {
                    val responseObject = JSONObject(response)

                    if (!responseObject.getBoolean("status")) {
                        Toast.makeText(
                            context,
                            responseObject.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                        onPropertyLoaded(property)
                        return@makeRequest
                    }
                    
                    val propertyObj = responseObject.getJSONObject("data")

                    property = Property(
                        propertyObj.getInt("id"),
                        propertyObj.getString("image_path"),
                        propertyObj.getString("name"),
                        propertyObj.getInt("landlord_id"),
                        propertyObj.getString("location"),
                        propertyObj.getString("price"),
                        propertyObj.getInt("type_id"),
                        propertyObj.getInt("bedrooms"),
                        propertyObj.getInt("bathrooms"),
                        propertyObj.getInt("property_type_id"),
                        propertyObj.getString("address"),
                        propertyObj.getString("points_of_interest"),
                        propertyObj.getString("description"),
                        propertyObj.getString("latitude"),
                        propertyObj.getString("longitude"),
                        propertyObj.getString("created_at"),
                        propertyObj.getString("updated_at"),
                        propertyObj.getString("deleted_at"),
                        propertyObj.getString("landlord_first_name"),
                        propertyObj.getString("landlord_last_name"),
                        propertyObj.getString("landlord_email"),
                        propertyObj.getString("landlord_phone_number"),
                        propertyObj.getString("type_name"),
                        propertyObj.getString("property_type")
                    )

                    onPropertyLoaded(property)
                } catch (e: JSONException) {
                    Log.d("Error", e.toString())
                }
            },
            onError = { error ->
                Log.d("Error", error)
                onPropertyLoaded(null)
            }
        )
    }

    fun getProperties(onPropertiesLoaded: (List<Property>) -> Unit) {
        val propertyLists = ArrayList<Property>()

        networkManager.makeRequest(
            Request.Method.GET,
            "$backEndAPI/viewProperties",
            mapOf(),
            onSuccess = { response ->
                try {
                    val responseObject = JSONObject(response)

                    if (!responseObject.getBoolean("status")) {
                        Toast.makeText(
                            context,
                            responseObject.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                        return@makeRequest
                    }

                    val propertiesArray = responseObject.getJSONArray("data")

                    for (i in 0 until propertiesArray.length()) {
                        val property = propertiesArray.getJSONObject(i)

                        val propertyList = Property(
                            property.getInt("id"),
                            property.getString("image_path"),
                            property.getString("name"),
                            property.getInt("landlord_id"),
                            property.getString("location"),
                            property.getString("price"),
                            property.getInt("type_id"),
                            property.getInt("bedrooms"),
                            property.getInt("bathrooms"),
                            property.getInt("property_type_id"),
                            property.getString("address"),
                            property.getString("points_of_interest"),
                            property.getString("description"),
                            property.getString("latitude"),
                            property.getString("longitude"),
                            property.getString("created_at"),
                            property.getString("updated_at"),
                            property.getString("deleted_at"),
                            property.getString("landlord_first_name"),
                            property.getString("landlord_last_name"),
                            property.getString("landlord_email"),
                            property.getString("landlord_phone_number"),
                            property.getString("type_name"),
                            property.getString("property_type")
                        )
                        propertyLists.add(propertyList)
                    }

                    onPropertiesLoaded(propertyLists)
                } catch (e: JSONException) {
                    Log.d("Error", e.toString())
                }
            },
            onError = { error ->
                Log.d("Error", error)
            }
        )
    }

    fun favoriteProperty(propertyId: Int?, user: User, onPropertySaved: (Boolean) -> Unit) {
        networkManager.makeRequest(
            Request.Method.POST,
            "$backEndAPI/favoriteProperty",
            mapOf(
                "property_id" to propertyId.toString()
            ),
            onSuccess = { response ->
                try {
                    val responseObject = JSONObject(response)

                    if (!responseObject.getBoolean("status")) {
                        Toast.makeText(
                            context,
                            responseObject.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                        onPropertySaved(false)
                        return@makeRequest
                    }

                    onPropertySaved(true)
                } catch (e: JSONException) {
                    Log.d("Error", e.toString())
                }
            },
            onError = { error ->
                Log.d("Error", error)
                onPropertySaved(false)
            },
            authToken = user.token
        )

    }

    fun getFavoriteProperties(user: User, onPropertiesLoaded: (List<Property>) -> Unit) {
        val propertyLists = ArrayList<Property>()

        networkManager.makeRequest(
            Request.Method.GET,
            "$backEndAPI/getFavorites",
            mapOf(),
            onSuccess = { response ->
                try {
                    val responseObject = JSONObject(response)

                    if (!responseObject.getBoolean("status")) {
                        Toast.makeText(
                            context,
                            responseObject.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                        return@makeRequest
                    }

                    val propertiesArray = responseObject.getJSONArray("data")

                    for (i in 0 until propertiesArray.length()) {
                        val property = propertiesArray.getJSONObject(i)

                        val propertyList = Property(
                            property.getInt("id"),
                            property.getString("image_path"),
                            property.getString("name"),
                            property.getInt("landlord_id"),
                            property.getString("location"),
                            property.getString("price"),
                            property.getInt("type_id"),
                            property.getInt("bedrooms"),
                            property.getInt("bathrooms"),
                            property.getInt("property_type_id"),
                            property.getString("address"),
                            property.getString("points_of_interest"),
                            property.getString("description"),
                            property.getString("latitude"),
                            property.getString("longitude"),
                            property.getString("created_at"),
                            property.getString("updated_at"),
                            property.getString("deleted_at"),
                            property.getString("landlord_first_name"),
                            property.getString("landlord_last_name"),
                            property.getString("landlord_email"),
                            property.getString("landlord_phone_number"),
                            property.getString("type_name"),
                            property.getString("property_type")
                        )
                        propertyLists.add(propertyList)
                    }

                    onPropertiesLoaded(propertyLists)
                } catch (e: JSONException) {
                    Log.d("Error", e.toString())
                }
            },
            onError = { error ->
                Log.d("Error", error)
            },
            authToken = user.token
        )
    }

    fun addProperty(
        image_path: String,
        name: String,
        location: String,
        price: String,
        address: String,
        points_of_interest: String,
        description: String,
        latitude: String,
        longitude: String,
        bedrooms: String,
        bathrooms: String,
        type_id: Int?,
        property_type_id: Int?,
        user: User,
        onPropertyLoaded: (Boolean) -> Unit
    ) {
        networkManager.makeRequest(
            Request.Method.POST,
            "$backEndAPI/createProperty",
            mapOf(
                "image_path" to image_path,
                "name" to name,
                "location" to location,
                "price" to price,
                "address" to address,
                "points_of_interest" to points_of_interest,
                "description" to description,
                "latitude" to latitude,
                "longitude" to longitude,
                "bedrooms" to bedrooms,
                "bathrooms" to bathrooms,
                "type_id" to type_id.toString(),
                "property_type_id" to property_type_id.toString()
            ),
            onSuccess = { response ->
                try {
                    val responseObject = JSONObject(response)

                    if (!responseObject.getBoolean("status")) {
                        Toast.makeText(
                            context,
                            responseObject.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                        onPropertyLoaded(false)
                    } else {
                        Toast.makeText(
                            context,
                            responseObject.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                        onPropertyLoaded(true)
                    }

                } catch (e: JSONException) {
                    Log.d("Error", e.toString())
                }
            },
            onError = { error ->
                Log.d("Error", error)
                onPropertyLoaded(false)
            },
            authToken = user.token
        )
    }

    fun updateProperty(
        propertyId: Int?,
        image_path: String,
        name: String,
        location: String,
        price: String,
        address: String,
        points_of_interest: String,
        description: String,
        latitude: String,
        longitude: String,
        bedrooms: String,
        bathrooms: String,
        type_id: Int?,
        property_type_id: Int?,
        user: User,
        onPropertyLoaded: (Boolean) -> Unit
    ) {
        networkManager.makeRequest(
            Request.Method.POST,
            "$backEndAPI/updateProperty",
            mapOf(
                "property_id" to propertyId.toString(),
                "image_path" to image_path,
                "name" to name,
                "location" to location,
                "price" to price,
                "address" to address,
                "points_of_interest" to points_of_interest,
                "description" to description,
                "latitude" to latitude,
                "longitude" to longitude,
                "bedrooms" to bedrooms,
                "bathrooms" to bathrooms,
                "type_id" to type_id.toString(),
                "property_type_id" to property_type_id.toString()
            ),
            onSuccess = { response ->
                try {
                    val responseObject = JSONObject(response)

                    if (!responseObject.getBoolean("status")) {
                        Toast.makeText(
                            context,
                            responseObject.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                        onPropertyLoaded(false)
                    } else {
                        Toast.makeText(
                            context,
                            responseObject.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                        onPropertyLoaded(true)
                    }

                } catch (e: JSONException) {
                    Log.d("Error", e.toString())
                }
            },
            onError = { error ->
                Log.d("Error", error)
                onPropertyLoaded(false)
            },
            authToken = user.token
        )
    }
}