package com.example.shelta.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shelta.presentation.screens.Screens
import com.example.shelta.presentation.uievent.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainScreenViewModel: ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onEvent(mainScreenEvents: MainScreenEvents){
        when(mainScreenEvents){
            is MainScreenEvents.OnSearchClicked -> {

            }
            is MainScreenEvents.OnProfileClicked -> {
                sendUiEvent(UiEvent.OnNavigate(Screens.ProfileScreen.route))
            }
        }
    }
    private fun sendUiEvent(uiEvent: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
}