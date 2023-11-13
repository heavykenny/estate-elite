package com.example.landlord.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.landlord.R
import com.example.landlord.models.Inquiry
import com.example.landlord.utils.Utils

class InquiriesAdapter(
    private val items: List<Inquiry>,
    private val listener: OnIconClickListener
) : RecyclerView.Adapter<InquiriesAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View, listener: OnIconClickListener) :
        RecyclerView.ViewHolder(view) {
        // Bind the variables to the views in the layout
        val propertyNameTextView: TextView = view.findViewById(R.id.propertyNameTextView)
        val landlordNameTextView: TextView = view.findViewById(R.id.landlordNameTextView)
        val dateOfInquiryTextView: TextView = view.findViewById(R.id.dateOfInquiryTextView)

        init {
            // Set the click listener for the item view
            view.setOnClickListener {
                listener.onPropertyClicked(bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // Create a new view using the layout file we created
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.inquiry_list_item, parent, false)
        return ItemViewHolder(view, listener)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        // Set the text for the views in the layout
        holder.propertyNameTextView.text = item.property_name
        holder.landlordNameTextView.text =
            "Landlord: ${item.landlord_first_name} ${item.landlord_last_name}"
        holder.dateOfInquiryTextView.text =
            "Date of Inquiry: ${Utils.formatTimestampForDisplay(item.created_at.toString())}"
    }

    override fun getItemCount() = items.size

    interface OnIconClickListener {
        fun onPropertyClicked(position: Int)
    }

}
