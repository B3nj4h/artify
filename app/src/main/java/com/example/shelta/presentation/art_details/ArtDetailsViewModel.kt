package com.example.shelta.presentation.art_details

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shelta.common.Resource
import com.example.shelta.domain.use_case.GetArtWorkDetailsUseCase
import com.example.shelta.presentation.uievent.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getArtWorkDetailsUseCase: GetArtWorkDetailsUseCase
): ViewModel() {
    var id by mutableStateOf("")

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = mutableStateOf(ArtDetailsState())
    val state: State<ArtDetailsState> = _state

    init {
        id = savedStateHandle.get<String>("id")?:""
        getArtDetails(id)
    }

    private fun getArtDetails(id: String){
        getArtWorkDetailsUseCase(id).onEach { result ->
            when(result){
                is Resource.Error -> {
                    _state.value = ArtDetailsState(message = result.message?:"")
                    sendUiEvent(UiEvent.ShowToast(result.message?:"unknown error occurred"))
                }
                is Resource.Loading -> {
                    _state.value = ArtDetailsState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = ArtDetailsState(artModel = result.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(artDetailsEvents: ArtDetailsEvents){
        when(artDetailsEvents){
            ArtDetailsEvents.OnPopBackStack -> {
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