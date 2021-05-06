package com.gavincdunne.employeedirectory.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gavincdunne.employeedirectory.data.local.entity.Employee

@Dao
interface EmployeeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployeesList(employees: List<Employee>)

    @Query("SELECT * FROM employee")
    fun readEmployeeList(): List<Employee>
}