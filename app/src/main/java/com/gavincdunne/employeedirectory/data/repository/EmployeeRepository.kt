package com.gavincdunne.employeedirectory.data.repository

import com.gavincdunne.employeedirectory.data.entity.EmployeesResponse
import kotlinx.coroutines.flow.Flow

interface EmployeeRepository {
    fun getEmployees() : Flow<EmployeesResponse>
}