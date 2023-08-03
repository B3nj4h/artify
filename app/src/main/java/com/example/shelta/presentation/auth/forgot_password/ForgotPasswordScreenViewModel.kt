package com.example.shelta.presentation.auth.forgot_password

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shelta.common.Resource
import com.example.shelta.domain.model.ResetModel
import com.example.shelta.domain.use_case.ResetPasswordUseCase
import com.example.shelta.presentation.uievent.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordScreenViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase
): ViewModel() {
    var email by mutableStateOf("")

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = mutableStateOf(ForgotPasswordScreenState())
    val state: State<ForgotPasswordScreenState> = _state

    private fun sendEmail(email: String){
        resetPasswordUseCase(ResetModel(email)).onEach { result ->
            when(result){
                is Resource.Error -> {
                    _state.value = ForgotPasswordScreenState(message = result.message?:"")
                    sendUiEvent(UiEvent.ShowToast(result.message?:"unknown error occurred"))
                }
                is Resource.Loading -> {
                    _state.value = ForgotPasswordScreenState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = ForgotPasswordScreenState(message = result.message?:"unknown error occurred")
                    sendUiEvent(UiEvent.ShowToast("We have send you an email to reset your password"))
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(forgotPasswordScreenEvents: ForgotPasswordScreenEvents){
        when(forgotPasswordScreenEvents){
            is ForgotPasswordScreenEvents.OnBackCLicked -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
            is ForgotPasswordScreenEvents.OnSendLinkClicked -> {
                if (!email.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))){
                    sendUiEvent(UiEvent.ShowToast(message = "invalid email"))
                } else {
                    sendEmail(email)
                }
            }
            is ForgotPasswordScreenEvents.OnChangeEmail -> {
                email = forgotPasswordScreenEvents.email
            }
        }
    }

    private fun sendUiEvent(uiEvent: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
}