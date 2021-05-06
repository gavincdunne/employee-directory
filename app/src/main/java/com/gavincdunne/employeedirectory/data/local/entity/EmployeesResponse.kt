package com.gavincdunne.employeedirectory.data.local.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmployeesResponse(val employees: List<Employee>)