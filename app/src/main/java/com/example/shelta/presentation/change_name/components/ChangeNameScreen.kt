package com.example.shelta.presentation.change_name.components

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
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
import androidx.navigation.NavHostController
import com.example.shelta.R
import com.example.shelta.presentation.auth.login.components.CustomButton
import com.example.shelta.presentation.auth.login.components.CustomTextField
import com.example.shelta.presentation.change_name.ChangeNameScreenEvents
import com.example.shelta.presentation.change_name.ChangeNameViewModel
import com.example.shelta.presentation.screens.Screens
import com.example.shelta.presentation.uievent.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeNameScreen(
    navHostController: NavHostController,
    viewModel: ChangeNameViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val context = LocalContext.current

    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{
            when(it){
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
                    IconButton(
                        onClick = {
                            navHostController.navigate(Screens.ProfileScreen.route){
                                popUpTo(Screens.ProfileScreen.route){
                                    inclusive = true
                                }
                            }
                        }) {
                        androidx.compose.material3.Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.arrow_back), contentDescription = "back") } },
                title = { Text(text = "Change name") }
            )
        }
    ) {
        Box(
            modifier = Modifier.padding(
                top = it.calculateTopPadding(),
                bottom = it.calculateBottomPadding())
        ){
            Column() {
                CustomTextField(
                    onClick = {},
                    placeholder = "Enter new name",
                    value = viewModel.name,
                    onValueChange = { email -> viewModel.onEvent(ChangeNameScreenEvents.OnNameChanged(email))},
                    leadingIcon = painterResource(id = R.drawable.profile)) { }
                Spacer(modifier = Modifier.height(10.dp))
                CustomButton(text = "Submit", isLoading = state.isLoading) { viewModel.onEvent(ChangeNameScreenEvents.OnSubmitClicked) }
            }
        }
    }
}