package com.example.shelta.presentation.change_name

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shelta.common.Resource
import com.example.shelta.domain.use_case.UpdateNameUseCase
import com.example.shelta.presentation.uievent.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeNameViewModel @Inject constructor(
    private val updateNameUseCase: UpdateNameUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    var id by mutableStateOf("")
    var name by mutableStateOf("")

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = mutableStateOf(ChangeNameScreenState())
    val state: State<ChangeNameScreenState> = _state

    private fun changeName(id: String, name: String){
        updateNameUseCase(id, name).onEach { result ->
            when(result){
                is Resource.Error -> {
                    _state.value = ChangeNameScreenState(message = result.message?:"")
                    sendUiEvent(UiEvent.ShowToast(result.message?:"unknown error occurred"))
                }
                is Resource.Loading -> {
                    _state.value = ChangeNameScreenState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = ChangeNameScreenState(response = result.data)
                    sendUiEvent(UiEvent.ShowToast(result.data?.message?:"unknown error occurred"))
                }
            }
        }.launchIn(viewModelScope)
    }
    fun onEvent(changeNameScreenEvents: ChangeNameScreenEvents){
        when(changeNameScreenEvents){
            is ChangeNameScreenEvents.OnNameChanged -> { name = changeNameScreenEvents.name }
            is ChangeNameScreenEvents.OnSubmitClicked -> { changeName(id, name) }
        }
    }

    private fun sendUiEvent(uiEvent: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
}