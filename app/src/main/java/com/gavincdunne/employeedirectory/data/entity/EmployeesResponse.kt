package com.gavincdunne.employeedirectory.data.entity

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmployeesResponse(val employees: List<Employee>)