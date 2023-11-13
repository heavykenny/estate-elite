package com.example.landlord.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.landlord.R
import com.example.landlord.adapters.MessagesAdapter
import com.example.landlord.repositories.InquiryRepository
import com.example.landlord.repositories.PropertyRepository
import com.example.landlord.utils.NavigationUtil
import com.example.landlord.utils.Utils

class MessagingActivity : AppCompatActivity() {

    // Get the property id and landlord id from the intent
    private val propertyId: Int by lazy {
        intent.getIntExtra("propertyId", 0)
    }

    private val landlordId: Int by lazy {
        intent.getIntExtra("landlordId", 0)
    }

    private lateinit var messagesRecyclerView: RecyclerView

    // Declare the variables for the views
    private var landlordNameTextView: TextView? = null
    private var propertyNameTextView: TextView? = null
    private var landlordContactLinearLayout: LinearLayout? = null
    private var inputLayout: LinearLayout? = null
    private var messageInput: EditText? = null
    private var sendButton: Button? = null


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messaging)

        // Set the toolbar title
        val toolbarTitle = findViewById<TextView>(R.id.toolbarTitle)
        toolbarTitle.text = "Messages"

        // Set the navigation
        val linLayoutMessages = findViewById<LinearLayout>(R.id.linLayoutMessages)
        NavigationUtil().navigate(this, linLayoutMessages)

        // Bind the variables to the views
        landlordNameTextView = findViewById(R.id.landlordNameTextView)
        propertyNameTextView = findViewById(R.id.propertyNameTextView)
        inputLayout = findViewById(R.id.inputLayout)
        messagesRecyclerView = findViewById(R.id.messageList)
        messageInput = findViewById(R.id.messageInput)
        sendButton = findViewById(R.id.sendButton)
        landlordContactLinearLayout = findViewById(R.id.landlordContactLinearLayout)

        // Get the property details
        PropertyRepository(this).getProperty(propertyId) { property ->
            landlordContactLinearLayout?.visibility = LinearLayout.VISIBLE
            propertyNameTextView?.text = property?.name
            landlordNameTextView?.text =
                property?.landlord_first_name + " " + property?.landlord_last_name
        }
        // Get the user details
        val user = Utils.getUser(this)

        sendButton?.setOnClickListener {
            if (messageInput?.text.toString().isNotEmpty()) {
                // Send the message
                InquiryRepository(this).sendMessage(
                    user,
                    propertyId,
                    landlordId,
                    messageInput?.text.toString()
                ) { success ->
                    if (success) {
                        Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show()
                        messageInput?.text?.clear()
                        finish()
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Message failed to send", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // Get the messages
        val messageListScrollView = findViewById<ScrollView>(R.id.messageListScrollView)
        // Get the messages
        InquiryRepository(this).getMessages(user, propertyId) { messages ->
            if (messages.isNotEmpty()) {
                landlordContactLinearLayout?.visibility = View.GONE
                messagesRecyclerView.visibility = RecyclerView.VISIBLE
                messagesRecyclerView.layoutManager = LinearLayoutManager(this)
                messagesRecyclerView.adapter = MessagesAdapter(messages)
                messageListScrollView.visibility = ScrollView.VISIBLE
            }
        }
    }
}