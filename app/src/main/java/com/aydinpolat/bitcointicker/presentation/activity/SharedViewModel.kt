package com.aydinpolat.bitcointicker.presentation.activity

import androidx.lifecycle.ViewModel
import com.aydinpolat.bitcointicker.domain.use_case.main.GetAppStartDestinationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val getAppStartDestinationUseCase: GetAppStartDestinationUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SharedState())
    val state = _state.asStateFlow()

    init {
        updateStateByUser()
    }

    private fun updateStateByUser() {
        val appStartDestination = getAppStartDestinationUseCase()
        _state.value = state.value.copy(appStartDestinationId = appStartDestination)
    }

}