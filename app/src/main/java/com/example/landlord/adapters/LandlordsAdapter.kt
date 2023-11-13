package com.example.landlord.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.landlord.R
import com.example.landlord.models.User

class LandlordsAdapter(
    private val items: List<User>,
    private val listener: OnLandlordClickListener
) : RecyclerView.Adapter<LandlordsAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View, listener: OnLandlordClickListener) :
        RecyclerView.ViewHolder(view) {
        val userOrLandlordNameTextView: TextView =
            view.findViewById(R.id.userOrLandlordNameTextView)
        private val editUserOrLandlordButton: ImageButton =
            view.findViewById(R.id.editUserOrLandlordButton)

        private val deleteUserOrLandlordButton: ImageButton =
            view.findViewById(R.id.deleteUserOrLandlordButton)

        init {
            view.setOnClickListener {
                listener.onLandlordClicked(bindingAdapterPosition)
            }

            editUserOrLandlordButton.setOnClickListener {
                listener.onEditUserClicked(bindingAdapterPosition)
            }

            deleteUserOrLandlordButton.setOnClickListener {
                listener.onDeleteUserClicked(bindingAdapterPosition)
            }
        }
    }

    interface OnLandlordClickListener {
        fun onLandlordClicked(position: Int)
        fun onEditUserClicked(position: Int)
        fun onDeleteUserClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user_or_landlord, parent, false)
        return ItemViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val landlord = items[position]
        holder.userOrLandlordNameTextView.text = landlord.first_name + " " + landlord.last_name
    }

    override fun getItemCount() = items.size
}
