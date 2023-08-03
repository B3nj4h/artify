package com.example.shelta.presentation.change_password

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shelta.common.Resource
import com.example.shelta.domain.use_case.UpdatePasswordUseCase
import com.example.shelta.presentation.uievent.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val updatePasswordUseCase: UpdatePasswordUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    var id by mutableStateOf("")
    var previousPassword by mutableStateOf("")
    var newPassword by mutableStateOf("")

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = mutableStateOf(ChangePasswordScreenState())
    val state: State<ChangePasswordScreenState> = _state

    init { id = savedStateHandle.get<String>("id")?:""}

    private fun changePassword(id: String, oldPassword: String, newPassword: String){
        updatePasswordUseCase(id, oldPassword, newPassword).onEach { result ->
            when(result){
                is Resource.Error -> {
                    _state.value = ChangePasswordScreenState(message = result.message?:"")
                    sendUiEvent(UiEvent.ShowToast(result.message?:"unknown error occurred"))
                }
                is Resource.Loading -> {
                    _state.value = ChangePasswordScreenState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = ChangePasswordScreenState(response = result.data)
                    sendUiEvent(UiEvent.ShowToast(result.data?.message?:"unknown error occurred"))
                }
            }
        }.launchIn(viewModelScope)
    }
    fun onEvent(changePasswordScreenEvents: ChangePasswordScreenEvents){
        when(changePasswordScreenEvents){
            is ChangePasswordScreenEvents.OnPreviousPasswordChanged -> { previousPassword = changePasswordScreenEvents.previousPassword }
            is ChangePasswordScreenEvents.OnNewPasswordChanged -> { newPassword = changePasswordScreenEvents.newPassword }
            is ChangePasswordScreenEvents.OnBackClicked -> { sendUiEvent(UiEvent.PopBackStack) }
            is ChangePasswordScreenEvents.OnSubmitClicked -> {
                if (!newPassword.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#\$%^&*()\\-_+=\\[\\]{}|:<>?~/\\\\]).{8,}\$".toRegex())){
                    sendUiEvent(UiEvent.ShowToast(message = "weak password try entering a strong one"))
                } else if (!previousPassword.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#\$%^&*()\\-_+=\\[\\]{}|:<>?~/\\\\]).{8,}\$".toRegex())){
                    sendUiEvent(UiEvent.ShowToast(message = "weak password try entering a strong one"))
                }else {
                    changePassword(id,previousPassword, newPassword)
                }
            }
        }
    }

    private fun sendUiEvent(uiEvent: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
}