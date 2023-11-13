package com.example.landlord.models

class User(
    var id: Int?,
    var first_name: String?,
    var last_name: String?,
    var email: String?,
    var hashed_password: String?,
    var phone_number: String?,
    var address: String?,
    var profile_image_url: String?,
    var created_at: String?,
    var updated_at: String?,
    var deleted_at: String?,
    var token: String?,
    var role_name: String?,
    var role_id: String?
) {

    val iSAdmin: Boolean
        get() {
            return role_name == "admin"
        }

    val isLandlord: Boolean
        get() {
            return role_name == "landlord"
        }

    val isUser: Boolean
        get() {
            return role_name == "user" || role_name == ""
        }
}
