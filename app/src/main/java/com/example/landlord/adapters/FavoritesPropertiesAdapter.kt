package com.example.landlord.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.landlord.R
import com.example.landlord.models.Property
import com.example.landlord.utils.Utils.Companion.capitalizeFirst

class FavoritesPropertiesAdapter(
    private val items: List<Property>,
    private val listener: OnIconClickListener
) : RecyclerView.Adapter<FavoritesPropertiesAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View, private val listener: OnIconClickListener) :
        RecyclerView.ViewHolder(view) {
        // Bind the variables to the views
        val propertyName: TextView = view.findViewById(R.id.propertyNameTextView)
        val propertyPrice: TextView = view.findViewById(R.id.propertyPriceTextView)
        val rentSaleLeaseTextView: TextView = view.findViewById(R.id.rentSaleLeaseTextView)
        val landlordNameTextView: TextView = view.findViewById(R.id.landlordNameTextView)
        val propertyLocation: TextView = view.findViewById(R.id.propertyLocationTextView)

        init {
            // Set the click listener for the item view
            view.setOnClickListener {
                listener.onPropertyClicked(bindingAdapterPosition)
            }
        }
    }

    interface OnIconClickListener {
        fun onPropertyClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // Create a new view using the layout file we created
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.favorite_property_list_item, parent, false)
        return ItemViewHolder(view, listener)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        // Set the text for the views
        holder.propertyName.text = item.name
        holder.propertyPrice.text = "£" + item.price.toString()
        holder.rentSaleLeaseTextView.text = item.property_type.toString().capitalizeFirst()
        holder.landlordNameTextView.text =
            "Landlord: " + item.landlord_first_name + " " + item.landlord_last_name
        holder.propertyLocation.text = "Location: " + item.location
    }

    override fun getItemCount() = items.size
}
