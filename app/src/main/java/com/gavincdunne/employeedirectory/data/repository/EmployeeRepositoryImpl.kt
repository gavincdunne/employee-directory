package com.gavincdunne.employeedirectory.data.repository

import com.gavincdunne.employeedirectory.data.local.dao.EmployeeDao
import com.gavincdunne.employeedirectory.data.local.entity.Employee
import com.gavincdunne.employeedirectory.data.local.entity.EmployeesResponse
import com.gavincdunne.employeedirectory.data.remote.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmployeeRepositoryImpl @Inject constructor(
    private val apiService: APIService,
    private val employeeDao: EmployeeDao
) : EmployeeRepository {
    override fun getEmployees(): Flow<EmployeesResponse> {
        return flow {
            val employeesResponse = apiService.getEmployees()
            emit(employeesResponse)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun insertEmployees(employeeList: List<Employee>) {
        employeeDao.insertEmployeesList(employeeList)
    }

    private fun readEmployees(list: List<Employee>): List<Employee> {
        if (list.isNullOrEmpty()) {
            GlobalScope.launch { insertEmployees(list) }
        }

        return employeeDao.readEmployeeList()
    }
}