package com.example.shelta.presentation.load.components

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.shelta.R
import com.example.shelta.common.AuthResult
import com.example.shelta.presentation.load.LoadScreenViewModel
import com.example.shelta.presentation.screens.Screens
import com.example.shelta.presentation.ui.theme.Blue

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoadScreen(
        navHostController: NavHostController,
        viewModel: LoadScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(viewModel, context){
        viewModel.authResults.collect{ result ->
            when(result){
                is AuthResult.Authorized -> {
                    navHostController.navigate(Screens.MainScreen.route){
                        popUpTo(Screens.LoadScreen.route){
                            inclusive = true
                        }
                    }
                }
                is AuthResult.Unauthorized -> {
                    navHostController.navigate(Screens.WelcomeScreen.route){
                        popUpTo(Screens.LoadScreen.route){
                            inclusive = true
                        }
                    }
                }
                is AuthResult.Error -> {
                    Toast.makeText(
                            context,
                            result.message,
                            Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    Scaffold() {
        Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                        .fillMaxSize()
                        .padding(top = it.calculateTopPadding()),
        ) {
            if (viewModel.authState.isLoading){
                Image(
                        modifier = Modifier.align(alignment = Alignment.Center),
                        painter = painterResource(id = R.drawable.splash),
                        contentDescription = "splash")
            } else if (viewModel.authState.message.isNotEmpty()){
                Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                            text = "Something went wrong",
                            textAlign = TextAlign.Center
                    )
                    Text(
                            modifier = Modifier.clickable { viewModel.authenticate() },
                            color = Blue,
                            textAlign = TextAlign.Center,
                            text = "Refresh"
                    )
                }
            }
        }
    }
}