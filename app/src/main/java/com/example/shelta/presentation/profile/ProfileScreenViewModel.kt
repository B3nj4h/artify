package com.example.shelta.presentation.profile

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shelta.common.Resource
import com.example.shelta.domain.use_case.GetProfileUseCase
import com.example.shelta.presentation.screens.Screens
import com.example.shelta.presentation.uievent.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val sharedPreferences: SharedPreferences
): ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = mutableStateOf(ProfileScreenState())
    val state: State<ProfileScreenState> = _state
    init {
        getProfile()
    }
    fun getProfile(){
        getProfileUseCase().onEach{ result ->
            when(result){
                is Resource.Error -> {
                    _state.value = ProfileScreenState(message = result.message?:"")
                }
                is Resource.Loading -> {
                    _state.value = ProfileScreenState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = ProfileScreenState(profile = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(profileScreenEvents: ProfileScreenEvents){
        when(profileScreenEvents){
            is ProfileScreenEvents.OnChangeContactClicked -> {
                sendUiEvent(UiEvent.OnNavigate(Screens.ChangeContactScreen.route + "/${profileScreenEvents.id}"))
            }
            is ProfileScreenEvents.OnChangeEmailClicked -> {
                sendUiEvent(UiEvent.OnNavigate(Screens.ChangeEmailScreen.route + "/${profileScreenEvents.id}"))
            }
            is ProfileScreenEvents.OnChangeNameClicked -> {
                sendUiEvent(UiEvent.OnNavigate(Screens.ChangeNameScreen.route + "/${profileScreenEvents.id}"))
            }
            is ProfileScreenEvents.OnChangePasswordClicked -> {
                sendUiEvent(UiEvent.OnNavigate(Screens.ChangePasswordScreen.route + "/${profileScreenEvents.id}"))
            }
            is ProfileScreenEvents.OnLogoutClicked -> {
                viewModelScope.launch {
                    sharedPreferences.edit()
                        .putString("token", null)
                        .apply()
                }
            }
            is ProfileScreenEvents.OnBackClicked -> { sendUiEvent(UiEvent.PopBackStack) }
            is ProfileScreenEvents.OnSendFeedbackClicked ->  {
                sendUiEvent(UiEvent.OnNavigate(Screens.SendFeedbackScreen.route))
            }
        }
    }

    private fun sendUiEvent(uiEvent: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
}