package com.example.shelta.presentation.main.components

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.shelta.presentation.main.MainScreenEvents
import com.example.shelta.presentation.main.MainScreenViewModel
import com.example.shelta.presentation.screens.Screens
import com.example.shelta.presentation.send_feedback.SendFeedbackViewModel
import com.example.shelta.presentation.uievent.UiEvent
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navHostController: NavHostController,
    onNavigate: (UiEvent.OnNavigate) -> Unit,
    sendViewModel: SendFeedbackViewModel = hiltViewModel(),
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val artModels = viewModel.state.value

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
        Box (
            modifier = Modifier.fillMaxSize()
        ){
            if (artModels.message.isNotBlank()){
                IconButton(
                    modifier = Modifier.align(Alignment.Center),
                    onClick = { viewModel.getArtWork() }) {
                    Icon(
                        tint = MaterialTheme.colorScheme.tertiary,
                        imageVector = Icons.Default.Refresh, contentDescription = "refresh icon"
                    )
                }
            } else if (artModels.isLoading){
//                val lottieCompositionSpec by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(
//                    com.example.kinetic.R.raw.gaming))
//                LottieAnimation(
//                    composition = lottieCompositionSpec,
//                    iterations = Int.MAX_VALUE,
//                    alignment = Alignment.Center
//                )
            } else {
                LazyColumn(){
                    item {
                        Column(
                            modifier = Modifier.height(50.dp)
                        ) {

                        }
                    }
                    item {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                            ,
                        ) {
                            Text(
                                text = "Republic of",
                                fontWeight = FontWeight.Bold,
                                fontSize = 35.sp,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                            Text(
                                text = "Gamers",
                                fontWeight = FontWeight.Bold,
                                fontSize = 35.sp,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    }
                    items(artModels.artModels){ artModel ->
                        ArtWorkCard(
                            name = artModel.name,
                            price = artModel.price,
                            imageUrl = artModel.image_url,
                            rating = artModel.rating
                        ) {
                            navHostController.navigate(
                                Screens.ArtWorkDetailsScreen.route + "/${artModel.id}"
                            )
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }
            }
        }
    }
}

