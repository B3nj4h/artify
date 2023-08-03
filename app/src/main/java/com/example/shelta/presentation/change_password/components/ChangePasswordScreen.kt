package com.example.shelta.presentation.change_password.components

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Password
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shelta.R
import com.example.shelta.presentation.auth.login.LoginScreenViewModel
import com.example.shelta.presentation.auth.login.components.CustomButton
import com.example.shelta.presentation.auth.login.components.CustomTextField
import com.example.shelta.presentation.auth.login.components.PasswordTextField
import com.example.shelta.presentation.change_contact.ChangeContactScreenEvents
import com.example.shelta.presentation.change_password.ChangePasswordScreenEvents
import com.example.shelta.presentation.change_password.ChangePasswordViewModel
import com.example.shelta.presentation.uievent.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordScreen(
    onPopBackStack: () -> Unit,
    viewModel: ChangePasswordViewModel = hiltViewModel(),
    loginViewModel: LoginScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val context = LocalContext.current

    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{
            when(it){
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowToast -> {
                    Toast.makeText(
                        context,
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { viewModel.onEvent(ChangePasswordScreenEvents.OnBackClicked) }) {
                        androidx.compose.material3.Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.arrow_back), contentDescription = "back") } },
                title = { Text(text = "Change password") }
            )
        }
    ) {
        Box(
            modifier = Modifier.padding(
                top = it.calculateTopPadding(),
                bottom = it.calculateBottomPadding())
        ){
            Column() {
                PasswordTextField(
                    placeholder = "Enter Previous password",
                    value = viewModel.previousPassword,
                    onValueChange = { prePass -> viewModel.onEvent(ChangePasswordScreenEvents.OnPreviousPasswordChanged(prePass))},
                    leadingIcon = painterResource(id = R.drawable.lock),
                    trailingIcon = painterResource(id = R.drawable.visibilityon),
                    viewModel = loginViewModel
                )
                Spacer(modifier = Modifier.height(10.dp))
                PasswordTextField(
                    placeholder = "Enter new password",
                    value = viewModel.newPassword,
                    onValueChange = { newPass -> viewModel.onEvent(ChangePasswordScreenEvents.OnNewPasswordChanged(newPass))},
                    leadingIcon = painterResource(id = R.drawable.lock),
                    trailingIcon = painterResource(id = R.drawable.visibilityon),
                    viewModel = loginViewModel
                )
                Spacer(modifier = Modifier.height(10.dp))
                CustomButton(text = "Submit", isLoading = state.isLoading) { viewModel.onEvent(ChangePasswordScreenEvents.OnSubmitClicked) }
            }
        }
    }
}