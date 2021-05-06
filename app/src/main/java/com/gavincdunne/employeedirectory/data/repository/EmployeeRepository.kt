package com.gavincdunne.employeedirectory.data.repository

import com.gavincdunne.employeedirectory.data.local.entity.Employee
import com.gavincdunne.employeedirectory.data.local.entity.EmployeesResponse
import kotlinx.coroutines.flow.Flow

interface EmployeeRepository {
    fun getEmployees() : Flow<EmployeesResponse>
    suspend fun insertEmployees(employeeList: List<Employee>)
//    fun readEmployees(list: List<Employee>) : List<Employee>
}