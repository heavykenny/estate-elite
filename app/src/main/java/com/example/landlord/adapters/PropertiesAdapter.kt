package com.example.landlord.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.landlord.R
import com.example.landlord.models.Property
import com.example.landlord.utils.Utils
import com.example.landlord.utils.Utils.Companion.capitalizeFirst

class PropertiesAdapter(
    private val items: ArrayList<Property>,
    private val listener: OnIconClickListener
) : RecyclerView.Adapter<PropertiesAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View, private val listener: OnIconClickListener) :
        RecyclerView.ViewHolder(view) {
        // Bind the variables to the views in the layout
        val propertyName: TextView = view.findViewById(R.id.propertyNameTextView)
        val propertyPrice: TextView = view.findViewById(R.id.propertyPriceTextView)
        val bedroomTextView: TextView = view.findViewById(R.id.bedroomTextView)
        val bathroomTextView: TextView = view.findViewById(R.id.bathroomTextView)
        val rentSaleLeaseTextView: TextView = view.findViewById(R.id.rentSaleLeaseTextView)
        val dateListedTextView: TextView = view.findViewById(R.id.dateListedTextView)
        val landlordNameTextView: TextView = view.findViewById(R.id.landlordNameTextView)
        val propertyLocation: TextView = view.findViewById(R.id.propertyLocationTextView)
        private val callIconImageView: ImageView = view.findViewById(R.id.callIconImageView)
        private val emailIconImageView: ImageView = view.findViewById(R.id.emailIconImageView)
        private val saveIconImageView: ImageView = view.findViewById(R.id.saveIconImageView)

        init {
            callIconImageView.setOnClickListener {
                listener.onCallIconClicked(bindingAdapterPosition)
            }

            emailIconImageView.setOnClickListener {
                listener.onEmailIconClicked(bindingAdapterPosition)
            }

            saveIconImageView.setOnClickListener {
                listener.onFavoriteClicked(bindingAdapterPosition)
            }

            view.setOnClickListener {
                listener.onPropertyClicked(bindingAdapterPosition)
            }
        }
    }

    interface OnIconClickListener {
        fun onCallIconClicked(position: Int)
        fun onEmailIconClicked(position: Int)
        fun onPropertyClicked(position: Int)
        fun onFavoriteClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.property_list_item, parent, false)
        return ItemViewHolder(view, listener)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        // Set the text for the views in the layout
        holder.propertyName.text = item.name
        holder.propertyPrice.text = "£" + item.price.toString()
        holder.bedroomTextView.text = item.bedrooms.toString() + " beds"
        holder.bathroomTextView.text = item.bathrooms.toString() + " baths"
        holder.rentSaleLeaseTextView.text = item.property_type.toString().capitalizeFirst()
        holder.dateListedTextView.text = Utils.formatTimestampForDisplay(item.created_at.toString())
        holder.landlordNameTextView.text =
            "Landlord: " + item.landlord_first_name + " " + item.landlord_last_name
        holder.propertyLocation.text = "Location: " + item.location
    }

    override fun getItemCount() = items.size
}
