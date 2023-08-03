package com.example.shelta.presentation.auth.sign_up.components

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shelta.R
import com.example.shelta.common.AuthResult
import com.example.shelta.presentation.auth.login.LoginScreenViewModel
import com.example.shelta.presentation.auth.login.components.AuthTopAppBar
import com.example.shelta.presentation.auth.login.components.CustomButton
import com.example.shelta.presentation.auth.login.components.CustomTextField
import com.example.shelta.presentation.auth.login.components.PasswordTextField
import com.example.shelta.presentation.auth.sign_up.SignUpScreenEvents
import com.example.shelta.presentation.auth.sign_up.SignUpScreenViewModel
import com.example.shelta.presentation.uievent.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignUpScreen(
    onNavigate: (UiEvent.OnNavigate) -> Unit,
    onPopBackStack: () -> Unit,
    viewModel: SignUpScreenViewModel = hiltViewModel(),
    loginViewModel: LoginScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{
            when(it){
                is UiEvent.OnNavigate -> onNavigate(it)
                is UiEvent.ShowToast -> Toast.makeText(
                        context,
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                is UiEvent.PopBackStack -> onPopBackStack()
                else -> Unit
            }
        }
    }

    LaunchedEffect(viewModel, context){
        viewModel.authResults.collect{ result ->
            when(result){
                is AuthResult.Authorized -> {
                    viewModel.onEvent(SignUpScreenEvents.OnNavigateToMainScreen)
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

    Scaffold(
        topBar = {
            AuthTopAppBar(
                navigationIcon = painterResource(id = R.drawable.arrow_back),
                onClickNavigationIcon = { viewModel.onEvent(SignUpScreenEvents.OnBackCLicked) },)
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
                    text = "Sign up",
                    fontSize = 30.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                CustomTextField(
                    onClick = {},
                    placeholder = "user name",
                    value = viewModel.username,
                    onValueChange = { viewModel.onEvent(SignUpScreenEvents.OnUserNameChanged(it)) },
                    leadingIcon = painterResource(id = R.drawable.profile)) {  }
                Spacer(modifier = Modifier.height(10.dp))
                CustomTextField(
                    onClick = {},
                    placeholder = "email address",
                    value = viewModel.email,
                    onValueChange = { viewModel.onEvent(SignUpScreenEvents.OnEmailChanged(it)) },
                    leadingIcon = painterResource(id = R.drawable.mail)) {  }
                Spacer(modifier = Modifier.height(10.dp))
                CustomTextField(
                    onClick = {},
                    placeholder = "phone number",
                    value = viewModel.contact,
                    onValueChange = { viewModel.onEvent(SignUpScreenEvents.OnContactChanged(it)) },
                    leadingIcon = painterResource(id = R.drawable.phone)) { }
                Spacer(modifier = Modifier.height(10.dp))
                PasswordTextField(
                    placeholder = "secret password",
                    value = viewModel.password,
                    onValueChange = { viewModel.onEvent(SignUpScreenEvents.OnPasswordChanged(it)) },
                    leadingIcon = painterResource(id = R.drawable.lock),
                    viewModel = loginViewModel,
                    trailingIcon = painterResource(id = R.drawable.visibilityon)
                )
                Spacer(modifier = Modifier.height(30.dp))
                CustomButton(text = "Sign up") {
                    viewModel.onEvent(SignUpScreenEvents.OnSignUpCLicked) }
            }
        }
    }
}