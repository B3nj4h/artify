package com.example.shelta.presentation.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shelta.common.AuthResult
import com.example.shelta.domain.model.LogIn
import com.example.shelta.domain.model.SignUp
import com.example.shelta.domain.repository.AuthRepository
import com.example.shelta.presentation.auth.sign_up.AuthState
import com.example.shelta.presentation.screens.Screens
import com.example.shelta.presentation.uievent.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel(){
    var passwordVisibility by mutableStateOf(false)
    var isError by mutableStateOf(false)
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    private fun emailVerification(email: String) {
        val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
        isError = !email.matches(emailRegex)
    }
    var authState by mutableStateOf(AuthState())

    private val resultChannel = Channel<AuthResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private fun logIn(){
        viewModelScope.launch {
            authState = authState.copy(isLoading = true)
            val result = authRepository.logIn(LogIn(email, password))
            resultChannel.send(result)
            authState = authState.copy(isLoading = false)
        }
    }
    fun onEvent(loginScreenEvents: LoginScreenEvents){
        when(loginScreenEvents){
            is LoginScreenEvents.OnNavigateToMainScreen -> {
                sendUiEvent(UiEvent.OnNavigate(Screens.MainScreen.route))
            }
            is LoginScreenEvents.OnSignupClicked -> {
                sendUiEvent(UiEvent.OnNavigate(Screens.SignUpScreen.route))
            }
            is LoginScreenEvents.OnLoginClicked -> {
                viewModelScope.launch {
                    if (email.isBlank() || password.isBlank()){
                        sendUiEvent(UiEvent.ShowToast(message = "Fill in all details"))
                    } else if (!email.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))){
                        sendUiEvent(UiEvent.ShowToast(message = "invalid email"))
                    } else if (!password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#\$%^&*()\\-_+=\\[\\]{}|:<>?~/\\\\]).{8,}\$".toRegex())){
                        sendUiEvent(UiEvent.ShowToast(message = "weak password try entering a strong one"))
                    } else {
                        logIn()
                    }
                }
            }
            is LoginScreenEvents.OnBackCLicked -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
            is LoginScreenEvents.OnEmailChanged -> {
                email = loginScreenEvents.email
                emailVerification(email)
            }
            is LoginScreenEvents.OnPasswordChanged -> {
                password = loginScreenEvents.password
            }
            is LoginScreenEvents.OnForgotPasswordClicked -> {
                sendUiEvent(UiEvent.OnNavigate(Screens.ForgotPasswordScreen.route))
            }
        }
    }

    private fun sendUiEvent(uiEvent: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
}