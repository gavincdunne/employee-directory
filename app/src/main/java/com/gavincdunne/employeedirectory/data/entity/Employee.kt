package com.gavincdunne.employeedirectory.data.entity

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Employee(
    val uuid: String,
    @Json(name = "full_name") val fullName: String,
    @Json(name = "phone_number") val phone: String,
    @Json(name = "email_address") val email: String,
    val biography: String?,
    @Json(name = "photo_url_small") val photoSmall: String,
    @Json(name = "photo_url_large") val photoLarge: String?,
    val team: String,
    @Json(name = "employee_type") val type: EmployeeType,
) : Parcelable

enum class EmployeeType {
    FULL_TIME,
    PART_TIME,
    CONTRACTOR
}
