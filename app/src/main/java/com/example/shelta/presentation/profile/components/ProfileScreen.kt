package com.example.shelta.presentation.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.shelta.R
import com.example.shelta.presentation.profile.ProfileScreenEvents
import com.example.shelta.presentation.profile.ProfileScreenViewModel
import com.example.shelta.presentation.screens.Screens
import com.example.shelta.presentation.ui.theme.Blue
import com.example.shelta.presentation.uievent.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onPopBackStack: () -> Unit,
    navHostController: NavHostController,
    onNavigate: (UiEvent.OnNavigate) -> Unit,
    viewModel: ProfileScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{
            when(it){
                is UiEvent.OnNavigate -> onNavigate(it)
                is UiEvent.PopBackStack -> onPopBackStack()
                else -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { viewModel.onEvent(ProfileScreenEvents.OnBackClicked) }) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.arrow_back), contentDescription = "arrow back")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding()
                )
        ) {
            if (state.message.isNotEmpty()){
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = state.message,
                        color = Blue,
                        fontSize = 20.sp,
                    )
                    Text(
                        modifier = Modifier.clickable { viewModel.getProfile() },
                        text = "Refresh",
                        fontSize = 20.sp,
                        color = Blue,
                        textDecoration = TextDecoration.Underline
                    )
                }
            } else if (state.profile?.name?.isNotEmpty() == true) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "Hello ${state.profile.name}",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(20.dp)
                    )
                    ElevatedCardHolder(
                        onChangeNameClicked = { viewModel.onEvent(ProfileScreenEvents.OnChangeNameClicked("${state.profile.id ?:""}")) },
                        onChangeEmailCLicked = { viewModel.onEvent(ProfileScreenEvents.OnChangeEmailClicked("${state.profile.id ?:""}")) },
                        onChangeContactClicked = { viewModel.onEvent(ProfileScreenEvents.OnChangeContactClicked("${state.profile.id ?:""}")) },
                        onChangePasswordClicked = { viewModel.onEvent(ProfileScreenEvents.OnChangePasswordClicked( "${state.profile.id?:""}")) },
                        onLogoutClicked = {
                            viewModel.onEvent(ProfileScreenEvents.OnLogoutClicked)
                            navHostController.navigate(Screens.WelcomeScreen.route){
                                popUpTo(Screens.WelcomeScreen.route){
                                    inclusive = false
                                }
                            }
                        }
                    )
                }
            } else {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}