package com.example.landlord.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.landlord.activities.AdminDashboardActivity
import com.example.landlord.activities.AdminEditUserActivity
import com.example.landlord.activities.LandlordDashboardActivity
import com.example.landlord.activities.LoginActivity
import com.example.landlord.activities.MainActivity
import com.example.landlord.models.Property
import com.example.landlord.models.User
import com.example.landlord.repositories.UserRepository
import java.text.SimpleDateFormat
import java.util.Locale

class Utils {
    companion object {
        fun formatTimestampForDisplay(timestamp: String): String? {
            // Parse the timestamp using the pattern of the input
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val date =
                inputFormat.parse(timestamp) ?: return timestamp // Return original if parsing fails

            // Reformat date to a more user-friendly format
            return SimpleDateFormat("dd MMM, yyyy", Locale.getDefault()).format(date)
        }

        fun String.capitalizeFirst(): String {
            if (this.isEmpty()) return this
            return this[0].uppercaseChar() + this.substring(1)
        }

        fun emailLandlord(activity: Activity, property: Property?) {

            val email = property?.landlord_email
            val subject = "Inquiry about ${property?.name}"
            val message =
                "Hello ${property?.landlord_first_name},\n\nI'm interested in your property named ${property?.name} " +
                        "located at ${property?.location}. Can you provide more details?\n\nRegards,\n[Your Name]"

            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$email")
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, message)
            }
            ContextCompat.startActivity(activity, emailIntent, null)
        }

        fun callLandlord(activity: Activity, property: Property?) {
            val phone = property?.landlord_phone_number
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
            ContextCompat.startActivity(activity, intent, null)
        }

        fun shareProperty(activity: Activity, property: Property?) {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Check out this property named ${property?.name} located at ${property?.location}:${property?.address}:" +
                            "\n\n${property?.description}"
                )
            }
            ContextCompat.startActivity(activity, shareIntent, null)
        }

        fun isLoggedIn(activity: Activity): Boolean {
            val sharedPref = activity.getSharedPreferences("user", 0)
            val token = sharedPref.getString("token", "")
            return token != ""
        }

        fun saveUser(activity: Activity, user: User) {
            val sharedPref = activity.getSharedPreferences("user", 0)
            val editor = sharedPref.edit()
            editor.putString("token", user.token)
            editor.putString("first_name", user.first_name)
            editor.putString("last_name", user.last_name)
            editor.putString("email", user.email)
            editor.putString("phone_number", user.phone_number)
            editor.putString("address", user.address)
            editor.putString("profile_image_url", user.profile_image_url)
            editor.putString("created_at", user.created_at)
            editor.putString("updated_at", user.updated_at)
            editor.putString("deleted_at", user.deleted_at)
            editor.putString("role_name", user.role_name)
            editor.putString("role_id", user.role_id.toString())
            editor.apply()
        }

        fun getUser(activity: Activity): User {
            val sharedPref = activity.getSharedPreferences("user", 0)
            val token = sharedPref.getString("token", "")
            val first_name = sharedPref.getString("first_name", "")
            val last_name = sharedPref.getString("last_name", "")
            val email = sharedPref.getString("email", "")
            val phone_number = sharedPref.getString("phone_number", "")
            val address = sharedPref.getString("address", "")
            val profile_image_url = sharedPref.getString("profile_image_url", "")
            val created_at = sharedPref.getString("created_at", "")
            val updated_at = sharedPref.getString("updated_at", "")
            val deleted_at = sharedPref.getString("deleted_at", "")
            val role_name = sharedPref.getString("role_name", "")
            val role_id = sharedPref.getString("role_id", "")

            return User(
                id = null,
                first_name = first_name,
                last_name = last_name,
                email = email,
                hashed_password = null,
                phone_number = phone_number,
                address = address,
                profile_image_url = profile_image_url,
                created_at = created_at,
                updated_at = updated_at,
                deleted_at = deleted_at,
                token = token,
                role_name = role_name,
                role_id = role_id
            )
        }

        fun logout(activity: Activity) {
            val sharedPref = activity.getSharedPreferences("user", 0)
            val editor = sharedPref.edit()
            editor.clear()
            editor.apply()
            Toast.makeText(activity, "Logged out successfully", Toast.LENGTH_LONG).show()
            activity.finish()
        }

        fun switchToUserAccount(activity: Activity) {
            if (isLoggedIn(activity)) {
                val sharedPref = activity.getSharedPreferences("user", 0)
                val editor = sharedPref.edit()
                editor.putBoolean("adminActive", false)
                editor.putBoolean("userActive", true)
                editor.putBoolean("landlordActive", false)
                editor.apply()

                val intent = Intent(activity, MainActivity::class.java)
                ContextCompat.startActivity(activity, intent, null)
            } else {
                val intent = Intent(activity, LoginActivity::class.java)
                ContextCompat.startActivity(activity, intent, null)
            }
        }

        fun switchToAdminAccount(activity: Activity) {
            if (isLoggedIn(activity) && getUser(activity).iSAdmin) {
                val sharedPref = activity.getSharedPreferences("user", 0)
                val editor = sharedPref.edit()
                editor.putBoolean("adminActive", true)
                editor.putBoolean("userActive", false)
                editor.putBoolean("landlordActive", false)
                editor.apply()

                val intent = Intent(activity, AdminDashboardActivity::class.java)
                ContextCompat.startActivity(activity, intent, null)
            } else {
                val intent = Intent(activity, LoginActivity::class.java)
                ContextCompat.startActivity(activity, intent, null)
            }
        }

        fun switchToLandlordAccount(activity: Activity) {
            if (isLoggedIn(activity) && getUser(activity).isLandlord) {
                val sharedPref = activity.getSharedPreferences("user", 0)
                val editor = sharedPref.edit()
                editor.putBoolean("adminActive", false)
                editor.putBoolean("userActive", false)
                editor.putBoolean("landlordActive", true)
                editor.apply()

                val intent = Intent(activity, LandlordDashboardActivity::class.java)
                ContextCompat.startActivity(activity, intent, null)
            } else {
                val intent = Intent(activity, LoginActivity::class.java)
                ContextCompat.startActivity(activity, intent, null)
            }
        }

        fun isAdminActive(activity: Activity): Boolean {
            val sharedPref = activity.getSharedPreferences("user", 0)
            return sharedPref.getBoolean("adminActive", false)
        }

        fun isLandlordActive(activity: Activity): Boolean {
            val sharedPref = activity.getSharedPreferences("user", 0)
            return sharedPref.getBoolean("landlordActive", false)
        }

        fun isUserActive(activity: Activity): Boolean {
            val sharedPref = activity.getSharedPreferences("user", 0)
            return sharedPref.getBoolean("userActive", false)
        }

        fun switchToDashboard(activity: Activity) {
            val intent = Intent(activity, AdminDashboardActivity::class.java)
            ContextCompat.startActivity(activity, intent, null)
        }

        fun editUser(activity: Activity, user: User) {
            val intent = Intent(activity, AdminEditUserActivity::class.java)
            intent.putExtra("userId", user.id)
            ContextCompat.startActivity(activity, intent, null)
        }

        fun deleteUser(activity: Activity, user: User) {
            UserRepository(activity).deleteUser(user) {
                Toast.makeText(activity, "User deleted successfully", Toast.LENGTH_LONG).show()
                val intent = Intent(activity, AdminDashboardActivity::class.java)
                ContextCompat.startActivity(activity, intent, null)
            }
        }
    }
}