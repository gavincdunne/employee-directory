package com.gavincdunne.employeedirectory.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gavincdunne.employeedirectory.data.local.dao.EmployeeDao
import com.gavincdunne.employeedirectory.data.local.entity.Employee
import com.gavincdunne.employeedirectory.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject


@Database(entities = [Employee::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao

    class Callback @Inject constructor(
        private val database: AppDatabase,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val employeeDao = database.employeeDao()

            applicationScope.launch {
                employeeDao.insertEmployeesList(listOf())
            }
        }
    }
}