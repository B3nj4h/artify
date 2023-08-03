package com.example.shelta.presentation.change_email

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shelta.common.Resource
import com.example.shelta.domain.use_case.UpdateEmailUseCase
import com.example.shelta.presentation.uievent.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeEmailViewModel @Inject constructor(
    private val updateEmailUseCase: UpdateEmailUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var id by mutableStateOf("")
    var email by mutableStateOf("")

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = mutableStateOf(ChangeEmailScreenState())
    val state: State<ChangeEmailScreenState> = _state
    init { id = savedStateHandle.get<String>("id")?:"" }
    private fun changeEmail(id: String, email: String){
        updateEmailUseCase(id, email).onEach { result ->
            when(result){
                is Resource.Error -> {
                    _state.value = ChangeEmailScreenState(message = result.message?:"")
                    sendUiEvent(UiEvent.ShowToast(result.message?:"unknown error occurred"))
                }
                is Resource.Loading -> {
                    _state.value = ChangeEmailScreenState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = ChangeEmailScreenState(response = result.data)
                    sendUiEvent(UiEvent.ShowToast(result.data?.message?:"unknown error occurred"))
                }
            }
        }.launchIn(viewModelScope)
    }
    fun onEvent(changeEmailScreenEvents: ChangeEmailScreenEvents){
        when(changeEmailScreenEvents){
            is ChangeEmailScreenEvents.OnEmailChanged -> { email = changeEmailScreenEvents.email }
            is ChangeEmailScreenEvents.OnBackClicked -> { sendUiEvent(UiEvent.PopBackStack) }
            is ChangeEmailScreenEvents.OnSubmitClicked -> {
                if (!email.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))){
                    sendUiEvent(UiEvent.ShowToast(message = "invalid email"))
                } else {
                    changeEmail(id, email)
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