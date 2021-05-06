package com.gavincdunne.employeedirectory.data.remote

import com.gavincdunne.employeedirectory.data.local.entity.EmployeesResponse
import retrofit2.http.GET

interface APIService {
    @GET("employees.json")
    suspend fun getEmployees(): EmployeesResponse
}