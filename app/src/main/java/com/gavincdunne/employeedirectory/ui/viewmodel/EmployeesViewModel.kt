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
        repository.readEmployees()
            .onStart { showLoading() }
            .catch { showError(it.localizedMessage) }
            .onCompletion {  }
            .collect {
                if (it.isEmpty()) {
                    showEmpty()
                } else {
                    emit(it)
                    showSuccess()
                }
            }
    }

    /**
     * Emits Events.Loading state to fragment UI
     */
    private fun showLoading() = viewModelScope.launch {
        _events.value = Events.Loading
    }

    /**
     * Emits Events.Success state to fragment UI
     */
    private fun showSuccess() = viewModelScope.launch {
        _events.value = Events.Success
    }

    /**
     * Emits Events.Empty state to fragment UI
     */
    private fun showEmpty() = viewModelScope.launch {
        _events.value = Events.Empty
    }

    /**
     * Emits Events.Error state to fragment UI
     * @param error a string message error returned from repository.getEmployees()
     */
    private fun showError(error: String?) = viewModelScope.launch {
        error?.let { _events.value = Events.Error(error) }
            ?: run { _events.value = Events.Error("There was an unknown error.") }

    }

    sealed class Events {
        object Loading : Events()
        object Success : Events()
        data class Error(val error: String) : Events()
        object Empty : Events()
    }
}