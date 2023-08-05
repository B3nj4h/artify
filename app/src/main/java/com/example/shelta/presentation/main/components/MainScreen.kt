package com.example.shelta.presentation.main.components

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material.icons.rounded.Upload
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.shelta.presentation.auth.login.components.CustomButton
import com.example.shelta.presentation.auth.login.components.CustomTextField
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
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri}
    )
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
                onMenuClicked = {viewModel.onEvent(MainScreenEvents.OnProfileClicked)},
                onSearchClicked = { viewModel.onEvent(MainScreenEvents.OnSearchClicked)}
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.isUploadClicked = !viewModel.isUploadClicked }) {
                Icon(imageVector = Icons.Rounded.Upload, contentDescription = "upload")
            }
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
                if (viewModel.isUploadClicked){
                    Dialog(
                        onDismissRequest = { viewModel.onEvent(MainScreenEvents.OnUploadClicked(false)) }
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(500.dp)
                                .padding(20.dp)
                            ,
                        ) {
                            if (selectedImageUri != null){
                                CustomButton(text = "Upload image") {
                                    photoPickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                }
                            } else {
                                CustomButton(text = "Pick image") {
                                    photoPickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                }
                            }
                            AsyncImage(
                                modifier = Modifier
                                    .padding(16.dp)
                                ,
                                model = selectedImageUri,
                                contentScale = ContentScale.Crop,
                                contentDescription = null
                            )
                        }
                    }
                }
            } else if (artModels.isLoading){
//                val lottieCompositionSpec by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(
//                    com.example.kinetic.R.raw.gaming))
//                LottieAnimation(
//                    composition = lottieCompositionSpec,
//                    iterations = Int.MAX_VALUE,
//                    alignment = Alignment.Center
//                )
            } else if (viewModel.isUploadClicked){

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

