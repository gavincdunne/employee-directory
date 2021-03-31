package com.gavincdunne.employeedirectory.data.repository

import com.gavincdunne.employeedirectory.data.entity.Employee
import com.gavincdunne.employeedirectory.data.entity.EmployeesResponse
import kotlinx.coroutines.flow.flow
import java.util.concurrent.Flow

class FakeEmployeeRepository {
    private val employeeList = mutableListOf<Employee>()

    private val observableEmployeeList = flow<EmployeesResponse>() {  }

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }
}