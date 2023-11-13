package com.example.landlord.logics

import android.content.Context
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class NetworkManager(private val context: Context) {

    // Instantiate the RequestQueue.
    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    // Make a request to the server
    fun makeRequest(
        method: Int,
        backEndAPI: String,
        params: Map<String, String?>,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit,
        authToken: String? = null
    ) {
        // Convert the params to JSON
        val requestBody = JSONObject(params).toString()

        // Request a string response from the provided URL.
        val stringRequest = object : StringRequest(
            method,
            backEndAPI,
            Response.Listener { response ->
                onSuccess(response)
            },
            Response.ErrorListener { error ->
                onError(error.toString())
            }) {

            // Set the content type of the request
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }

            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray {
                return requestBody.toByteArray(Charsets.UTF_8)
            }

            // Set the headers of the request
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                // Add the authorization token to the headers
                val headers = HashMap<String, String>()
                authToken?.let {
                    headers["Bearer"] = "$it"
                }
                return headers
            }

        }
        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest)
    }
}
