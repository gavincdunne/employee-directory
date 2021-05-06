package com.gavincdunne.employeedirectory.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.gavincdunne.employeedirectory.data.local.dao.EmployeeDao
import com.gavincdunne.employeedirectory.data.local.entity.Employee
import com.gavincdunne.employeedirectory.data.local.entity.EmployeeType
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {
    private lateinit var database: AppDatabase
    private lateinit var employeeDao: EmployeeDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        employeeDao = database.employeeDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndReadEmployeeList() {
        val employeeList = mutableListOf<Employee>()
        employeeList.add(Employee(
            "123",
            "Developer",
            "",
            "",
        "",
            "",
            "",
            "",
            EmployeeType.CONTRACTOR
        ))

        runBlocking { employeeDao.insertEmployeesList(employeeList) }
        assertEquals(employeeDao.readEmployeeList(), employeeList)

    }
}