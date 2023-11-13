package com.example.landlord.utils

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.landlord.R
import com.example.landlord.activities.FavoritesActivity
import com.example.landlord.activities.InquiriesActivity
import com.example.landlord.activities.MainActivity
import com.example.landlord.activities.SettingsActivity

class NavigationUtil {
    private var linLayoutHome: LinearLayout? = null
    private var linLayoutFavorites: LinearLayout? = null
    private var linLayoutMessages: LinearLayout? = null
    private var linLayoutProfile: LinearLayout? = null

    fun navigate(activity: Activity, activeLayout: LinearLayout?) {
        val backButton = activity.findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            activity.onBackPressed()
        }

        linLayoutHome = activity.findViewById(R.id.linLayoutHome)
        linLayoutFavorites = activity.findViewById(R.id.linLayoutFavorites)
        linLayoutMessages = activity.findViewById(R.id.linLayoutMessages)
        linLayoutProfile = activity.findViewById(R.id.linLayoutProfile)

        activeLayout?.setBackgroundColor(
            ContextCompat.getColor(
                activity,
                R.color.french_blue
            )
        )

        linLayoutHome!!.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(activity.applicationContext, 0, 0)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(activity, intent, options.toBundle())
        }

        linLayoutFavorites!!.setOnClickListener {
            val intent = Intent(activity, FavoritesActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(activity.applicationContext, 0, 0)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(activity, intent, options.toBundle())
        }

        linLayoutMessages!!.setOnClickListener {
            val intent = Intent(activity, InquiriesActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(activity.applicationContext, 0, 0)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(activity, intent, options.toBundle())
        }

        linLayoutProfile!!.setOnClickListener {
            val intent = Intent(activity, SettingsActivity::class.java)
            val options = ActivityOptions.makeCustomAnimation(activity.applicationContext, 0, 0)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(activity, intent, options.toBundle())
        }
    }
}