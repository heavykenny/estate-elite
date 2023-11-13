package com.example.landlord.repositories

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.example.landlord.R
import com.example.landlord.logics.NetworkManager
import com.example.landlord.models.User
import org.json.JSONException
import org.json.JSONObject

class UserRepository(private val context: Context) {
    private val networkManager = NetworkManager(context)

    private val backEndAPI: String by lazy {
        "${context.resources.getString(R.string.base_api_url)}/user"
    }

    fun register(
        first_name: String,
        last_name: String,
        email: String,
        password: String,
        onUserLoaded: (User?) -> Unit
    ) {
        networkManager.makeRequest(
            Request.Method.POST,
            "$backEndAPI/createUser",
            mapOf(
                "first_name" to first_name,
                "last_name" to last_name,
                "email" to email,
                "password" to password
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
                        hashed_password = userObj.getString("hashed_password"),
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
                    e.printStackTrace()
                }
            },
            onError = { error ->
                Log.d("Error", error)
                onUserLoaded(null)
            }
        )
    }


    fun login(
        email: String,
        password: String,
        onUserLoaded: (User?) -> Unit
    ) {
        networkManager.makeRequest(
            Request.Method.POST,
            "$backEndAPI/loginUser",
            mapOf(
                "email" to email,
                "password" to password
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
                        hashed_password = userObj.getString("hashed_password"),
                        phone_number = userObj.getString("phone_number"),
                        address = userObj.getString("address"),
                        profile_image_url = userObj.getString("profile_image_url"),
                        created_at = userObj.getString("created_at"),
                        updated_at = userObj.getString("updated_at"),
                        deleted_at = userObj.getString("deleted_at"),
                        token = responseObject.getString("token"),
                        role_name = userObj.getString("role_name"),
                        role_id = userObj.getString("role_id")
                    )

                    onUserLoaded(user)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            onError = { error ->
                Log.d("Error", error)
                onUserLoaded(null)
            }
        )
    }

    fun updateUser(user: User, password: String, onUserLoaded: (User?) -> Unit) {
        mapOf(
            "first_name" to user.first_name,
            "last_name" to user.last_name,
            "email" to user.email,
            "phone_number" to user.phone_number,
            "address" to user.address,
            "role_id" to user.role_id,
            "password" to password,
        ).let {
            networkManager.makeRequest(
                Request.Method.PUT,
                "$backEndAPI/updateUser/${user.id}",
                it,
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
                        e.printStackTrace()
                    }
                },
                onError = { error ->
                    Log.d("Error", error)
                    onUserLoaded(null)
                }
            )
        }
    }

    fun getUserById(userId: Int, onUserLoaded: (User?) -> Unit) {
        networkManager.makeRequest(
            Request.Method.GET,
            "$backEndAPI/getUser/$userId",
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

    fun deleteUser(user: User, onUserDelete: (Boolean) -> Unit) {
        networkManager.makeRequest(
            Request.Method.DELETE,
            "$backEndAPI/deleteUser/${user.id}",
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
                        onUserDelete(false)
                        return@makeRequest
                    }

                    onUserDelete(true)
                } catch (e: JSONException) {
                    Log.d("Error", e.toString())
                }
            },
            onError = { error ->
                Log.d("Error", error)
                onUserDelete(false)
            }
        )
    }
}
