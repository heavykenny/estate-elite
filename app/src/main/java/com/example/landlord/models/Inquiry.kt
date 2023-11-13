package com.example.landlord.models

class Inquiry(
    var id: Int?,
    var tenant_id: Int?,
    var landlord_id: Int?,
    var property_id: Int?,
    var property_name: String?,
    var message: String?,
    var created_at: String?,
    var updated_at: String?,
    var deleted_at: String?,
    var tenant_first_name: String?,
    var tenant_last_name: String?,
    var tenant_email: String?,
    var tenant_phone_number: String?,
    var landlord_first_name: String?,
    var landlord_last_name: String?,
    var landlord_email: String?
)
