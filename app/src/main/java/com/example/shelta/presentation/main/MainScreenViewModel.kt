package com.example.shelta.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shelta.common.Resource
import com.example.shelta.domain.model.Feedback
import com.example.shelta.domain.use_case.GetArtWorkUseCase
import com.example.shelta.presentation.screens.Screens
import com.example.shelta.presentation.send_feedback.SendFeedbackState
import com.example.shelta.presentation.uievent.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getArtWorkUseCase: GetArtWorkUseCase
): ViewModel() {
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = mutableStateOf(MainScreenState())
    val state: State<MainScreenState> = _state

    init {
        getArtWork()
    }

    fun getArtWork(){
        getArtWorkUseCase().onEach { result ->
            when(result){
                is Resource.Error -> {
                    _state.value = MainScreenState(message = result.message?:"")
                    sendUiEvent(UiEvent.ShowToast(result.message?:"unknown error occurred"))
                }
                is Resource.Loading -> {
                    _state.value = MainScreenState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = MainScreenState(artModels = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }

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