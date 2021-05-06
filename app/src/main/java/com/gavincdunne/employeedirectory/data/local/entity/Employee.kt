package com.gavincdunne.employeedirectory.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
@Entity
data class Employee(
    @PrimaryKey val uuid: String,
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
