package com.example.shelta.presentation.change_contact

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shelta.common.Resource
import com.example.shelta.domain.use_case.UpdateContactUseCase
import com.example.shelta.presentation.uievent.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeContactViewModel @Inject constructor(
    private val updateContactUseCase: UpdateContactUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    var id by mutableStateOf("")
    var contact by mutableStateOf("")

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = mutableStateOf(ChangeContactScreenState())
    val state: State<ChangeContactScreenState> = _state

    init { id = savedStateHandle.get<String>("id")?:"" }
    private fun changeContact(id:String, contact: String){
        updateContactUseCase(id, contact).onEach { result ->
            when(result){
                is Resource.Error -> {
                    _state.value = ChangeContactScreenState(message = result.message?:"")
                    sendUiEvent(UiEvent.ShowToast(result.message?:"unknown error occurred"))
                }
                is Resource.Loading -> {
                    _state.value = ChangeContactScreenState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = ChangeContactScreenState(response = result.data)
                    sendUiEvent(UiEvent.ShowToast(result.data?.message?:"unknown error occurred"))
                }
            }
        }.launchIn(viewModelScope)
    }
    fun onEvent(changeContactScreenEvents: ChangeContactScreenEvents){
        when(changeContactScreenEvents){
            is ChangeContactScreenEvents.OnContactChanged -> { contact = changeContactScreenEvents.contact }
            is ChangeContactScreenEvents.OnBackClicked -> { sendUiEvent(UiEvent.PopBackStack) }
            is ChangeContactScreenEvents.OnSubmitClicked -> { changeContact(id, contact) }
        }
    }

    private fun sendUiEvent(uiEvent: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
}