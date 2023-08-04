package com.example.shelta.presentation.main.components

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shelta.presentation.main.MainScreenEvents
import com.example.shelta.presentation.main.MainScreenViewModel
import com.example.shelta.presentation.send_feedback.SendFeedbackViewModel
import com.example.shelta.presentation.uievent.UiEvent
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigate: (UiEvent.OnNavigate) -> Unit,
    sendViewModel: SendFeedbackViewModel = hiltViewModel(),
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collectLatest{ event ->
            when(event){
                is UiEvent.OnNavigate -> onNavigate(event)
                is UiEvent.ShowToast -> {
                    Toast.makeText(
                        context,
                        event.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> Unit
            }
        }
    }

    LaunchedEffect(key1 = true){
        sendViewModel.uiEvent.collectLatest{ event ->
            when(event){
                is UiEvent.OnNavigate -> onNavigate(event)
                is UiEvent.ShowToast -> {
                    Toast.makeText(
                        context,
                        event.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                onMenuClicked = {viewModel.onEvent(MainScreenEvents.OnProfileClicked)}
            )
        }
    ){
        Box {
            LazyColumn(){

            }
        }
    }
}

