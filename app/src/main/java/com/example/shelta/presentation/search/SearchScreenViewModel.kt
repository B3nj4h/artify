package com.example.shelta.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shelta.common.Resource
import com.example.shelta.domain.use_case.GetArtWorkByNameUseCase
import com.example.shelta.presentation.uievent.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val getArtWorkByNameUseCase: GetArtWorkByNameUseCase
): ViewModel() {
    var name by mutableStateOf("")

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = mutableStateOf(SearchScreenState())
    val state: State<SearchScreenState> = _state

    private fun getArtWork(name: String){
        getArtWorkByNameUseCase(name).onEach { result ->
            when(result){
                is Resource.Error -> {
                    _state.value = SearchScreenState(message = result.message?:"")
                    sendUiEvent(UiEvent.ShowToast(result.message?:"unknown error occurred"))
                }
                is Resource.Loading -> {
                    _state.value = SearchScreenState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = SearchScreenState(artModels = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }

    fun resetSearchState(){
        _state.value = SearchScreenState(artModels = emptyList())
        name = ""
    }

    fun onEvent(searchScreenEvents: SearchScreenEvents){
        when(searchScreenEvents){
            is SearchScreenEvents.OnSearchClicked -> {
                getArtWork(name)
            }
            is SearchScreenEvents.OnBackClicked -> {
                sendUiEvent(UiEvent.PopBackStack)
            }
            is SearchScreenEvents.OnSearchQueryChanged ->{
                name = searchScreenEvents.search
            }
        }
    }
    private fun sendUiEvent(uiEvent: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
}