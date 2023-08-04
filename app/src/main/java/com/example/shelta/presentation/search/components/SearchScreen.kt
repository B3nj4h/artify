package com.example.shelta.presentation.search.components

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.shelta.domain.model.ArtModel
import com.example.shelta.presentation.main.components.ArtWorkCard
import com.example.shelta.presentation.screens.Screens
import com.example.shelta.presentation.search.SearchScreenEvents
import com.example.shelta.presentation.search.SearchScreenViewModel
import com.example.shelta.presentation.uievent.UiEvent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navHostController: NavHostController,
    onPopBackStack: () -> Unit,
    viewModel: SearchScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state.value
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    LaunchedEffect(key1 = true, context){
        viewModel.uiEvent.collect {event ->
            when(event){
                is UiEvent.ShowToast -> {
                    Toast.makeText(
                        context,
                        event.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is UiEvent.PopBackStack -> onPopBackStack()
                else -> Unit
            }
        }
    }
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SearchBar(
                scrollBehavior = scrollBehavior,
                text = viewModel.name,
                onTextChange = { viewModel.onEvent(SearchScreenEvents.OnSearchQueryChanged(it))},
                onCloseClicked = { viewModel.onEvent(SearchScreenEvents.OnBackClicked) },
                onSearchClicked = { viewModel.onEvent(SearchScreenEvents.OnSearchClicked)},
                onResetSearchState = { viewModel.resetSearchState() }
            )
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            if(state.isLoading){
//                val lottieCompositionSpec by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(
//                    R.raw.gaming))
//                LottieAnimation(
//                    composition = lottieCompositionSpec,
//                    iterations = Int.MAX_VALUE,
//                    alignment = Alignment.Center
//                )
            } else if (state.message.isNotEmpty()){
                Button(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.Center)
                    ,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                    ),
                    contentPadding = PaddingValues(0.dp),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 5.dp,
                        pressedElevation = 5.dp,
                        focusedElevation = 5.dp,
                        hoveredElevation = 5.dp,
                    ),
                    shape = CircleShape
                    ,
                    onClick = {  }) {
                    Icon(
                        tint = MaterialTheme.colorScheme.tertiary,
                        imageVector = Icons.Default.Refresh, contentDescription = "refresh icon")
                }
            } else {
                Box {
                    LazyColumn() {
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                            ) {

                            }
                        }
                        items(state.artModels) { artModel ->
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
}