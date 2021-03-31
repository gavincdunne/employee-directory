package com.gavincdunne.employeedirectory.network

import com.gavincdunne.employeedirectory.data.entity.EmployeesResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface APIService {
    @GET("employees.json")
    suspend fun getEmployees(): EmployeesResponse
}