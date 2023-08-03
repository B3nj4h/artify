package com.example.shelta.presentation.auth.sign_up

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shelta.common.AuthResult
import com.example.shelta.data.remote.rest.repository.AuthRepositoryImpl
import com.example.shelta.domain.model.SignUp
import com.example.shelta.domain.repository.AuthRepository
import com.example.shelta.presentation.screens.Screens
import com.example.shelta.presentation.uievent.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpScreenViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel(){

    var passwordVisibility by mutableStateOf(false)
    var authState by mutableStateOf(AuthState())

    private val resultChannel = Channel<AuthResult<Unit>>()
    val authResults = resultChannel.receiveAsFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var username by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var contact by mutableStateOf("")
    private fun signUp(){
        viewModelScope.launch {
            authState = authState.copy(isLoading = true)
            val result = authRepository.signUp(
                SignUp(
                    email = email,
                    name = username,
                    password = password,
                    contact = contact,
                )
            )
            resultChannel.send(result)
            authState = authState.copy(isLoading = false)
        }
    }
    fun onEvent(signUpScreenEvents: SignUpScreenEvents){
        when(signUpScreenEvents){
            is SignUpScreenEvents.OnEmailChanged -> {
                email = signUpScreenEvents.email
            }
            is SignUpScreenEvents.OnPasswordChanged -> {
                password = signUpScreenEvents.password
            }
            is SignUpScreenEvents.OnContactChanged -> {
                contact = signUpScreenEvents.contact
            }
            is SignUpScreenEvents.OnUserNameChanged -> {
                username = signUpScreenEvents.username
            }
            is SignUpScreenEvents.OnNavigateToMainScreen -> {
                sendUiEvent(UiEvent.OnNavigate(Screens.MainScreen.route))
            }
            is SignUpScreenEvents.OnSignUpCLicked -> {
                if (!email.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))){
                    sendUiEvent(UiEvent.ShowToast(message = "invalid email"))
                } else if (!password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#\$%^&*()\\-_+=\\[\\]{}|:<>?~/\\\\]).{8,}\$".toRegex())){
                    sendUiEvent(UiEvent.ShowToast(message = "weak password try entering a strong one"))
                } else {
                    signUp()
                }
            }
            is SignUpScreenEvents.OnBackCLicked -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
        }
    }

    private fun sendUiEvent(uiEvent: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
}