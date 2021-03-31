package com.gavincdunne.employeedirectory.data.repository

import com.gavincdunne.employeedirectory.data.entity.EmployeesResponse
import com.gavincdunne.employeedirectory.network.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmployeeRepositoryImpl @Inject constructor(private val apiService: APIService): EmployeeRepository {
    override fun getEmployees(): Flow<EmployeesResponse> {
        return flow {
            val employeesResponse = apiService.getEmployees()
            emit(employeesResponse)
        }.flowOn(Dispatchers.IO)
    }
}