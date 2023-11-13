package com.example.landlord.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.landlord.R
import com.example.landlord.models.Property

class PropertiesEditAdapter(
    private val items: ArrayList<Property>,
    private val listener: OnPropertyClickListener
) : RecyclerView.Adapter<PropertiesEditAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View, private val listener: OnPropertyClickListener) :
        RecyclerView.ViewHolder(view) {

        // Bind the variables to the views in the layout
        val propertyName: TextView = view.findViewById(R.id.propertyNameTextView)
        private val viewPropertyButton: ImageButton = view.findViewById(R.id.viewPropertyButton)
        private val managePropertyButton: ImageButton = view.findViewById(R.id.managePropertyButton)
        private val deletePropertyButton: ImageButton = view.findViewById(R.id.deletePropertyButton)

        init {
            viewPropertyButton.setOnClickListener {
                listener.onViewPropertyClicked(bindingAdapterPosition)
            }

            managePropertyButton.setOnClickListener {
                listener.onEditPropertyClicked(bindingAdapterPosition)
            }

            deletePropertyButton.setOnClickListener {
                listener.onDeletePropertyClicked(bindingAdapterPosition)
            }

            view.setOnClickListener {
                listener.onPropertyClicked(bindingAdapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_property_edit, parent, false)
        return ItemViewHolder(view, listener)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val property = items[position]
        holder.propertyName.text = property.name
    }

    interface OnPropertyClickListener {
        fun onViewPropertyClicked(position: Int)
        fun onDeletePropertyClicked(position: Int)
        fun onPropertyClicked(position: Int)
        fun onEditPropertyClicked(position: Int)
    }
}
