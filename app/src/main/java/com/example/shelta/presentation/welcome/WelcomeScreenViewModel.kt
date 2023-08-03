package com.example.shelta.presentation.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shelta.presentation.screens.Screens
import com.example.shelta.presentation.uievent.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class WelcomeScreenViewModel: ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    fun onEvent(welcomeScreenEvents: WelcomeScreenEvents){
        when(welcomeScreenEvents){
            is WelcomeScreenEvents.OnLoginClicked -> {
                sendUiEvent(UiEvent.OnNavigate(Screens.LoginScreen.route))
            }
            is WelcomeScreenEvents.OnRegisterClicked -> {
                sendUiEvent(UiEvent.OnNavigate(Screens.SignUpScreen.route))
            }
            is WelcomeScreenEvents.TermsAndConditionsClicked -> {

            }
        }
    }
    private fun sendUiEvent(uiEvent: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
}