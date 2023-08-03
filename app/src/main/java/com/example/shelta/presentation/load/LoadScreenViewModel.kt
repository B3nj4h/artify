package com.example.shelta.presentation.load

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shelta.common.AuthResult
import com.example.shelta.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class LoadScreenViewModel @Inject constructor(
        private val authRepository: AuthRepository
): ViewModel() {
    var authState by mutableStateOf(AuthState())

    private val resultChannel = Channel<AuthResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    init {
        authenticate()
    }

    fun authenticate(){
        viewModelScope.launch {
            authState = authState.copy(isLoading = true)
            val result = authRepository.authenticate()
            authState = authState.copy(message = result.message ?: "")
            resultChannel.send(result)
            authState = authState.copy(isLoading = false)
        }
    }
}