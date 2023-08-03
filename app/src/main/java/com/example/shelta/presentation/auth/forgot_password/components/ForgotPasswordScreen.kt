package com.example.shelta.presentation.auth.forgot_password.components

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shelta.R
import com.example.shelta.presentation.auth.forgot_password.ForgotPasswordScreenEvents
import com.example.shelta.presentation.auth.forgot_password.ForgotPasswordScreenViewModel
import com.example.shelta.presentation.auth.login.components.AuthTopAppBar
import com.example.shelta.presentation.auth.login.components.CustomButton
import com.example.shelta.presentation.auth.login.components.CustomTextField
import com.example.shelta.presentation.uievent.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ForgotPasswordScreen(
    onPopBackStack: () -> Unit,
    viewModel: ForgotPasswordScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val state = viewModel.state.value
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{
            when(it){
                is UiEvent.PopBackStack -> {
                    onPopBackStack()
                }
                is UiEvent.ShowToast -> {
                    Toast.makeText(
                        context,
                        it.message,
                        Toast.LENGTH_LONG
                    ).show()
                } else -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            AuthTopAppBar(
                navigationIcon = painterResource(id = R.drawable.arrow_back),
                onClickNavigationIcon = { viewModel.onEvent(ForgotPasswordScreenEvents.OnBackCLicked) })
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding()),
        ){
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Enter Email to Reset your password",
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                CustomTextField(
                    onClick = {},
                    placeholder = "email address",
                    value = viewModel.email,
                    onValueChange = { email -> viewModel.onEvent(ForgotPasswordScreenEvents.OnChangeEmail(email)) },
                    leadingIcon = painterResource(id = R.drawable.mail)) { }
                Spacer(modifier = Modifier.height(30.dp))
                CustomButton(text = "Send link", isLoading = state.isLoading) {
                    viewModel.onEvent(ForgotPasswordScreenEvents.OnSendLinkClicked)
                }
            }
        }
    }
}