package com.gavincdunne.employeedirectory.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.gavincdunne.employeedirectory.data.repository.EmployeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeesViewModel @Inject constructor(
    private val repository: EmployeeRepository
) : ViewModel() {

    private val _events = MutableStateFlow<Events>(Events.Empty)
    val events: StateFlow<Events> = _events

    val employees = liveData {
        repository.getEmployees()
            .onStart { showLoading() }
            .catch { showError(it.localizedMessage) }
            .onCompletion {  }
            .collect {
                if (it.employees.isEmpty()) {
                    showEmpty()
                } else {
                    emit(it)
                    showSuccess()
                }
            }
    }

    private fun showLoading() = viewModelScope.launch {
        _events.value = Events.Loading
    }

    private fun showSuccess() = viewModelScope.launch {
        _events.value = Events.Success
    }

    private fun showEmpty() = viewModelScope.launch {
        _events.value = Events.Empty
    }

    private fun showError(error: String) = viewModelScope.launch {
        _events.value = Events.Error(error)
    }

    sealed class Events {
        object Loading : Events()
        object Success : Events()
        data class Error(val error: String) : Events()
        object Empty : Events()
    }
}