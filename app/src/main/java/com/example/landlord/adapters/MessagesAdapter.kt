package com.example.landlord.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.landlord.R
import com.example.landlord.models.Inquiry
import com.example.landlord.utils.Utils

class MessagesAdapter(private val messagesLists: List<Inquiry>) :
    RecyclerView.Adapter<MessagesAdapter.ItemViewHolder>() {
    class ItemViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        val usernameTextView: TextView = view.findViewById(R.id.usernameTextView)
        val messageTextView: TextView = view.findViewById(R.id.messageTextView)
        val messageTimeTextView: TextView = view.findViewById(R.id.messageTimeTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.messages_list_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = messagesLists[position]

        holder.usernameTextView.text = item.tenant_last_name
        holder.messageTextView.text = item.message
        holder.messageTimeTextView.text =
            Utils.formatTimestampForDisplay(item.created_at.toString())
    }

    override fun getItemCount() = messagesLists.size
}
