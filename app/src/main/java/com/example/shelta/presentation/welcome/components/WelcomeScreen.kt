package com.example.shelta.presentation.welcome.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shelta.R
import com.example.shelta.presentation.auth.login.components.CustomButton
import com.example.shelta.presentation.uievent.UiEvent
import com.example.shelta.presentation.welcome.WelcomeScreenEvents
import com.example.shelta.presentation.welcome.WelcomeScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WelcomeScreen(
    onNavigate: (UiEvent.OnNavigate) -> Unit,
    viewModel: WelcomeScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{
            when(it){
                is UiEvent.OnNavigate -> onNavigate(it)
                else -> Unit
            }
        }
    }

    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    textAlign = TextAlign.Start,
                    text = "Explore collect \n \nSell more artwork",
                    fontSize = 40.sp,
                    fontFamily = FontFamily.Monospace
                )
                Card(
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .fillMaxHeight(0.6f)
                        .fillMaxWidth(),
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = R.drawable.welcome),
                        contentDescription = "logo")
                }
                CustomButton(text = "Get started") {
                    viewModel.onEvent(WelcomeScreenEvents.OnRegisterClicked)
                }
                Row() {
                    Text(
                        text = "Already have an account?"
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        modifier = Modifier.clickable {
                            viewModel.onEvent(WelcomeScreenEvents.OnLoginClicked)
                        },
                        text = "Log in",
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
    }
}