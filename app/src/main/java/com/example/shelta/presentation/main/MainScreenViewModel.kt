package com.example.shelta.presentation.main

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shelta.common.Resource
import com.example.shelta.domain.model.PostArtModel
import com.example.shelta.domain.use_case.GetArtWorkUseCase
import com.example.shelta.domain.use_case.PostArtWorkUseCase
import com.example.shelta.presentation.screens.Screens
import com.example.shelta.presentation.uievent.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getArtWorkUseCase: GetArtWorkUseCase,
    private val postArtWorkUseCase: PostArtWorkUseCase
): ViewModel() {
    var selectedImageUri by mutableStateOf(mutableStateOf<Uri?>(null))
    private val imageFile = selectedImageUri.value?.path?.let { File(it) }
    private val requestFile = imageFile?.let { RequestBody.create("image/*".toMediaTypeOrNull(), it) }
    val imagePart = requestFile?.let { MultipartBody.Part.createFormData("image", imageFile?.name, it) }

    var isUploadClicked by mutableStateOf(true)

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = mutableStateOf(MainScreenState())
    val state: State<MainScreenState> = _state

    private val _postArtWorkState = mutableStateOf(PostArtWorkState())
    val postArtWorkState: State<PostArtWorkState> = _postArtWorkState

    init {
        getArtWork()
    }

    fun postArtWork(postArtModel: PostArtModel){
        postArtWorkUseCase(postArtModel = postArtModel).onEach { result ->
            when(result){
                is Resource.Error -> {
                    _postArtWorkState.value = PostArtWorkState(error = result.message?:"")
                    sendUiEvent(UiEvent.ShowToast(result.message?:"unknown error occurred"))
                }
                is Resource.Loading -> {
                    _postArtWorkState.value = PostArtWorkState(isLoading = true)
                }
                is Resource.Success -> {
                    _postArtWorkState.value = PostArtWorkState(message = result.data)
                }
            }
        }.launchIn(viewModelScope)
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
                sendUiEvent(UiEvent.OnNavigate(Screens.SearchScreen.route))
            }
            is MainScreenEvents.OnProfileClicked -> {
                sendUiEvent(UiEvent.OnNavigate(Screens.ProfileScreen.route))
            }
            is MainScreenEvents.OnUploadClicked -> {
                isUploadClicked = mainScreenEvents.onCLick
            }
            is MainScreenEvents.OnPostArtWorkClicked -> {
                imagePart?.let {
                    PostArtModel(
                        image = it,
                        name = "",
                        price = "",
                        contact = "",
                        rating = "",
                        description = ""
                    )
                }?.let {
                    postArtWork(
                        it
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