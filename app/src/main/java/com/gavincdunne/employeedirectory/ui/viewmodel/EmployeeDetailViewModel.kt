package com.gavincdunne.employeedirectory.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.gavincdunne.employeedirectory.data.entity.Employee
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmployeeDetailViewModel @Inject constructor(
    private val state: SavedStateHandle
): ViewModel() {

    val employee = state.get<Employee>("employee")
}