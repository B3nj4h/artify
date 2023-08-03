package com.example.shelta.presentation.send_feedback

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shelta.common.Resource
import com.example.shelta.domain.model.Feedback
import com.example.shelta.domain.use_case.SendFeedbackUseCase
import com.example.shelta.presentation.uievent.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendFeedbackViewModel @Inject constructor(
    private val sendFeedbackUseCase: SendFeedbackUseCase
): ViewModel() {
    var email by mutableStateOf("")
    var incident by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var gender by mutableStateOf("Gender")
    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var isExpandable by mutableStateOf(false)
    var isError by mutableStateOf(false)

    var emailPlaceholder by mutableStateOf("email")
    var password by mutableStateOf("password")

    private fun emailVerification(email: String) {
        val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
        isError = !email.matches(emailRegex)
    }

    private val _state = mutableStateOf(SendFeedbackState())
    val state: State<SendFeedbackState> = _state


    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private fun sendFeedback(feedback: Feedback){
        sendFeedbackUseCase(feedback).onEach { result ->
            when(result){
                is Resource.Error -> {
                    _state.value = SendFeedbackState(message = result.message?:"")
                    sendUiEvent(UiEvent.ShowToast(result.message?:"unknown error occurred"))
                }
                is Resource.Loading -> {
                    _state.value = SendFeedbackState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = SendFeedbackState(response = result.data)
                    sendUiEvent(UiEvent.ShowToast("Data send successfully"))
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(sendFeedbackEvents: SendFeedbackEvents){
        when(sendFeedbackEvents){
            is SendFeedbackEvents.OnEmailChanged -> {
                email = sendFeedbackEvents.email
                emailVerification(email)
            }
            is SendFeedbackEvents.OnFirstNameChanged -> {
                firstName = sendFeedbackEvents.firstName
            }
            is SendFeedbackEvents.OnGenderChanged -> {
                gender = sendFeedbackEvents.gender
            }
            is SendFeedbackEvents.OnIncidentChanged -> {
                incident = sendFeedbackEvents.incident
            }
            is SendFeedbackEvents.OnPhoneNumberChanged -> {
                phoneNumber = sendFeedbackEvents.phoneNumber
            }
            is SendFeedbackEvents.OnLastNameChanged -> {
                lastName = sendFeedbackEvents.lastname
            }
            is SendFeedbackEvents.OnSubmitClicked -> {
                if (!email.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))){
                    sendUiEvent(UiEvent.ShowToast(message = "invalid email"))
                } else {
                    sendFeedback(
                        Feedback(
                            email, incident, phoneNumber, gender, firstName, lastName
                        )
                    )
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