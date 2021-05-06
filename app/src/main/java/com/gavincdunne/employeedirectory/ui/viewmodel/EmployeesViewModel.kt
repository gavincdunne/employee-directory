package com.gavincdunne.employeedirectory.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.gavincdunne.employeedirectory.data.entity.Employee
import com.gavincdunne.employeedirectory.data.repository.EmployeeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeesViewModel @Inject constructor(
    private val repository: EmployeeRepository
) : ViewModel() {

    private val _events = Channel<Events>()
    val events = _events.receiveAsFlow()

    fun readEmployees() = liveData {
        repository.getEmployees()
            .onStart { showLoading() }
            .catch { showError(it.localizedMessage) }
            .onCompletion {  }
            .collect {
                if (it.employees.isEmpty()) {
                    showEmpty()
                } else {
                    emit(it.employees.sortedBy { e -> e.fullName })
                    showSuccess()
                }
            }
    }

    /**
     * Emits Events.Loading state to fragment UI
     */
    private fun showLoading() = viewModelScope.launch {
        _events.send(Events.Loading)
    }

    /**
     * Emits Events.Success state to fragment UI
     */
    private fun showSuccess() = viewModelScope.launch {
        _events.send(Events.Success)
    }

    /**
     * Emits Events.Empty state to fragment UI
     */
    private fun showEmpty() = viewModelScope.launch {
        _events.send(Events.Empty)
    }

    /**
     * Emits Events.Error state to fragment UI
     * @param error a string message error returned from repository.getEmployees()
     */
    private fun showError(error: String?) = viewModelScope.launch {
        error?.let { _events.send(Events.Error(error)) }
            ?: run { _events.send(Events.Error("There was an unknown error.")) }

    }

    fun navigateToEmployeeDetail(employee: Employee) = viewModelScope.launch {
        _events.send(Events.NavigateToDetails(employee))
    }

    sealed class Events {
        object Loading : Events()
        object Success : Events()
        data class Error(val error: String) : Events()
        data class NavigateToDetails(val employee: Employee) : Events()
        object Empty : Events()
    }
}