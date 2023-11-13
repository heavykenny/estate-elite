package com.example.landlord.repositories

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.example.landlord.R
import com.example.landlord.logics.NetworkManager
import com.example.landlord.models.Inquiry
import com.example.landlord.models.User
import org.json.JSONObject

class InquiryRepository(private val context: Context) {
    private val networkManager = NetworkManager(context)

    private val backEndAPI: String by lazy {
        "${context.resources.getString(R.string.base_api_url)}/inquiries"
    }

    fun getInquiries(user: User, onInquiriesReceived: (inquiries: List<Inquiry>) -> Unit) {
        val inquiriesList = ArrayList<Inquiry>()

        networkManager.makeRequest(
            Request.Method.GET,
            "$backEndAPI/getInquiries",
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
                        onInquiriesReceived(listOf())
                        return@makeRequest
                    }

                    val inquiries = responseObject.getJSONArray("data")
                    for (i in 0 until inquiries.length()) {
                        val inquiry = inquiries.getJSONObject(i)
                        inquiriesList.add(
                            Inquiry(
                                id = inquiry.getInt("id"),
                                tenant_id = inquiry.getInt("tenant_id"),
                                landlord_id = inquiry.getInt("landlord_id"),
                                property_id = inquiry.getInt("property_id"),
                                property_name = inquiry.getString("property_name"),
                                message = inquiry.getString("message"),
                                created_at = inquiry.getString("created_at"),
                                updated_at = inquiry.getString("updated_at"),
                                deleted_at = inquiry.getString("deleted_at"),
                                tenant_first_name = inquiry.getString("tenant_first_name"),
                                tenant_last_name = inquiry.getString("tenant_last_name"),
                                tenant_email = inquiry.getString("tenant_email"),
                                tenant_phone_number = inquiry.getString("tenant_phone_number"),
                                landlord_first_name = inquiry.getString("landlord_first_name"),
                                landlord_last_name = inquiry.getString("landlord_last_name"),
                                landlord_email = inquiry.getString("landlord_email"),
                            )
                        )
                    }
                    onInquiriesReceived(inquiriesList)

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

    fun sendMessage(
        user: User,
        propertyId: Int,
        landlordId: Int,
        message: String,
        onMessageSent: (success: Boolean) -> Unit
    ) {
        networkManager.makeRequest(
            Request.Method.POST,
            "$backEndAPI/createInquiry",
            mapOf(
                "property_id" to propertyId.toString(),
                "landlord_id" to landlordId.toString(),
                "message" to message
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
                        onMessageSent(false)
                        return@makeRequest
                    }

                    onMessageSent(true)

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

    fun getMessages(
        user: User,
        propertyId: Int,
        onMessagesReceived: (messages: List<Inquiry>) -> Unit
    ) {
        val messagesList = ArrayList<Inquiry>()

        networkManager.makeRequest(
            Request.Method.GET,
            "$backEndAPI/getMessages/$propertyId",
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
                        onMessagesReceived(listOf())
                        return@makeRequest
                    }

                    val messages = responseObject.getJSONArray("data")
                    for (i in 0 until messages.length()) {
                        val message = messages.getJSONObject(i)
                        messagesList.add(
                            Inquiry(
                                id = message.getInt("id"),
                                tenant_id = message.getInt("tenant_id"),
                                landlord_id = message.getInt("landlord_id"),
                                property_id = message.getInt("property_id"),
                                property_name = message.getString("property_name"),
                                message = message.getString("message"),
                                created_at = message.getString("created_at"),
                                updated_at = message.getString("updated_at"),
                                deleted_at = message.getString("deleted_at"),
                                tenant_first_name = message.getString("tenant_first_name"),
                                tenant_last_name = message.getString("tenant_last_name"),
                                tenant_email = message.getString("tenant_email"),
                                tenant_phone_number = message.getString("tenant_phone_number"),
                                landlord_first_name = message.getString("landlord_first_name"),
                                landlord_last_name = message.getString("landlord_last_name"),
                                landlord_email = message.getString("landlord_email"),
                            )
                        )
                    }
                    onMessagesReceived(messagesList)

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