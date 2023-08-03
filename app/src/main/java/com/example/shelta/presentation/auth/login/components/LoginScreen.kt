package com.example.shelta.presentation.auth.login.components

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.shelta.R
import com.example.shelta.common.AuthResult
import com.example.shelta.presentation.auth.login.LoginScreenEvents
import com.example.shelta.presentation.auth.login.LoginScreenViewModel
import com.example.shelta.presentation.auth.sign_up.SignUpScreenEvents
import com.example.shelta.presentation.screens.Screens
import com.example.shelta.presentation.uievent.UiEvent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onNavigate: (UiEvent.OnNavigate) -> Unit,
    onPopBackStack: () -> Unit,
    viewModel: LoginScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(viewModel, context){
        viewModel.authResults.collect{ result ->
            when(result){
                is AuthResult.Authorized -> {
                    viewModel.onEvent(LoginScreenEvents.OnNavigateToMainScreen)
                }
                is AuthResult.Unauthorized -> {
                    Toast.makeText(
                        context,
                        result.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
                is AuthResult.Error -> {
                    Toast.makeText(
                        context,
                        result.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    LaunchedEffect(true){
        viewModel.uiEvent.collect{
            when(it){
                is UiEvent.OnNavigate -> {
                    onNavigate(it)
                }
                is UiEvent.PopBackStack -> {
                    onPopBackStack()
                }
                is UiEvent.ShowToast -> {
                    Toast.makeText(
                        context,
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                } else -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            AuthTopAppBar(
                navigationIcon = painterResource(id = R.drawable.arrow_back),
                onClickNavigationIcon = { viewModel.onEvent(LoginScreenEvents.OnBackCLicked) },
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding())
            ,
        ){
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(10.dp)
                ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Login",
                    fontSize = 30.sp)
                Spacer(modifier = Modifier.height(10.dp))
                CustomTextField(
                    onClick = {},
                    placeholder = "email address",
                    value = viewModel.email,
                    onValueChange = { viewModel.onEvent(LoginScreenEvents.OnEmailChanged(it)) },
                    leadingIcon = painterResource(id = R.drawable.mail)) {}
                Spacer(modifier = Modifier.height(10.dp))
                PasswordTextField(
                    placeholder = "secret password",
                    value = viewModel.password,
                    onValueChange = { viewModel.onEvent(LoginScreenEvents.OnPasswordChanged(it)) },
                    leadingIcon = painterResource(id = R.drawable.lock),
                    trailingIcon = painterResource(id = R.drawable.visibilityon),
                    viewModel = viewModel)
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    modifier = Modifier.clickable { viewModel.onEvent(LoginScreenEvents.OnForgotPasswordClicked) },
                    text = "Forgot password ?",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.height(30.dp))
                CustomButton(text = "Log in") { viewModel.onEvent(LoginScreenEvents.OnLoginClicked) }
            }
        }
    }
}
