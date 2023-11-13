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

class AdminRepository(private val context: Context) {
    private val networkManager = NetworkManager(context)

    private val backEndAPI: String by lazy {
        "${context.resources.getString(R.string.base_api_url)}/admin"
    }

    // Function to get all landlords
    fun getLandlords(user: User, onLandlordsLoaded: (landlords: ArrayList<User>) -> Unit) {
        val landlordLists = ArrayList<User>()
        networkManager.makeRequest(
            Request.Method.GET,
            "$backEndAPI/getLandlords",
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
                        onLandlordsLoaded(ArrayList())
                        return@makeRequest
                    }

                    val landlordsArray = responseObject.getJSONArray("data")

                    for (i in 0 until landlordsArray.length()) {
                        val landlordObj = landlordsArray.getJSONObject(i)
                        val landlord = User(
                            id = landlordObj.getInt("id"),
                            first_name = landlordObj.getString("first_name"),
                            last_name = landlordObj.getString("last_name"),
                            email = landlordObj.getString("email"),
                            hashed_password = "",
                            phone_number = landlordObj.getString("phone_number"),
                            address = landlordObj.getString("address"),
                            profile_image_url = landlordObj.getString("profile_image_url"),
                            created_at = landlordObj.getString("created_at"),
                            updated_at = landlordObj.getString("updated_at"),
                            deleted_at = landlordObj.getString("deleted_at"),
                            token = "",
                            role_name = landlordObj.getString("role_name"),
                            role_id = landlordObj.getString("role_id")
                        )
                        landlordLists.add(landlord)
                    }

                    onLandlordsLoaded(landlordLists)
                    return@makeRequest
                } catch (e: Exception) {
                    println(e)
                }
            },
            onError = { error ->
                println(error)
            },
            authToken = user.token
        )
    }

    // Function to get all users
    fun getUsers(user: User, onUsersLoaded: (users: ArrayList<User>) -> Unit) {
        networkManager.makeRequest(
            Request.Method.GET,
            "$backEndAPI/getUsers",
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
                        onUsersLoaded(ArrayList())
                        return@makeRequest
                    }

                    val usersArray = responseObject.getJSONArray("data")
                    val users = ArrayList<User>()

                    for (i in 0 until usersArray.length()) {
                        val userObj = usersArray.getJSONObject(i)
                        users.add(
                            User(
                                id = userObj.getInt("id"),
                                first_name = userObj.getString("first_name"),
                                last_name = userObj.getString("last_name"),
                                email = userObj.getString("email"),
                                hashed_password = "",
                                phone_number = userObj.getString("phone_number"),
                                address = userObj.getString("address"),
                                profile_image_url = userObj.getString("profile_image_url"),
                                created_at = userObj.getString("created_at"),
                                updated_at = userObj.getString("updated_at"),
                                deleted_at = userObj.getString("deleted_at"),
                                token = "",
                                role_name = userObj.getString("role_name"),
                                role_id = userObj.getString("role_id")
                            )
                        )
                    }
                    onUsersLoaded(users)
                } catch (e: Exception) {
                    println(e)
                }
            },
            onError = { error ->
                println(error)
            },
            authToken = user.token
        )
    }

    // Function to add a landlord
    fun addLandlord(
        firstName: String,
        lastName: String,
        email: String,
        phone: String,
        onUserLoaded: (User?) -> Unit
    ) {
        networkManager.makeRequest(
            Request.Method.POST,
            "$backEndAPI/createLandlord",
            mapOf(
                "first_name" to firstName,
                "last_name" to lastName,
                "email" to email,
                "phone_number" to phone
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
                        onUserLoaded(null)
                        return@makeRequest
                    }

                    val userObj = responseObject.getJSONObject("data")

                    val user = User(
                        id = userObj.getInt("id"),
                        first_name = userObj.getString("first_name"),
                        last_name = userObj.getString("last_name"),
                        email = userObj.getString("email"),
                        hashed_password = "",
                        phone_number = userObj.getString("phone_number"),
                        address = userObj.getString("address"),
                        profile_image_url = userObj.getString("profile_image_url"),
                        created_at = userObj.getString("created_at"),
                        updated_at = userObj.getString("updated_at"),
                        deleted_at = userObj.getString("deleted_at"),
                        token = "",
                        role_name = userObj.getString("role_name"),
                        role_id = userObj.getString("role_id")
                    )

                    onUserLoaded(user)

                } catch (e: JSONException) {
                    Log.d("Error", e.toString())
                }
            },
            onError = { error ->
                Log.d("Error", error)
                onUserLoaded(null)
            }
        )
    }

    // Function to get all properties
    fun getProperties(user: User, onPropertiesLoad: (properties: ArrayList<Property>) -> Unit) {
        networkManager.makeRequest(
            Request.Method.GET,
            "$backEndAPI/getProperties",
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
                        onPropertiesLoad(ArrayList())
                        return@makeRequest
                    }

                    val propertiesArray = responseObject.getJSONArray("data")
                    val properties = ArrayList<Property>()

                    for (i in 0 until propertiesArray.length()) {
                        val propertyObj = propertiesArray.getJSONObject(i)
                        properties.add(
                            Property(
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
                        )
                    }
                    onPropertiesLoad(properties)
                } catch (e: Exception) {
                    Log.d("Error", e.toString())
                }

            },
            onError = { error ->
                Log.d("Error", error)
            },
            authToken = user.token
        )
    }

    // Function to delete a landlord
    fun deleteProperty(property: Property, user: User, onPropertyDeleted: (Boolean) -> Unit) {
        networkManager.makeRequest(
            Request.Method.DELETE,
            "$backEndAPI/deleteProperty/${property.id}",
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
                        onPropertyDeleted(false)
                        return@makeRequest
                    }

                    onPropertyDeleted(true)
                } catch (e: Exception) {
                    Log.d("Error", e.toString())
                }
            },
            onError = { error ->
                Log.d("Error", error)
            },
            authToken = user.token
        )
    }
}
