package com.example.shelta.presentation.change_contact.components

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Phone
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
import com.example.shelta.presentation.auth.login.components.CustomButton
import com.example.shelta.presentation.auth.login.components.CustomTextField
import com.example.shelta.presentation.change_contact.ChangeContactScreenEvents
import com.example.shelta.presentation.change_contact.ChangeContactViewModel
import com.example.shelta.presentation.uievent.UiEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeContactScreen(
    onPopBackStack: () -> Unit,
    viewModel: ChangeContactViewModel = hiltViewModel()
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
                    IconButton(onClick = { viewModel.onEvent(ChangeContactScreenEvents.OnBackClicked) }) {
                        androidx.compose.material3.Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = R.drawable.arrow_back), contentDescription = "back") } },
                title = { Text(text = "Change contact")}
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
                    isPhoneNUmber = true,
                    onClick = {},
                    placeholder = "Enter new contact",
                    value = viewModel.contact,
                    onValueChange = { contact -> viewModel.onEvent(ChangeContactScreenEvents.OnContactChanged(contact))},
                    leadingIcon = painterResource(id = R.drawable.phone)) { }
                Spacer(modifier = Modifier.height(10.dp))
                CustomButton(text = "Submit", isLoading = state.isLoading) { viewModel.onEvent(ChangeContactScreenEvents.OnSubmitClicked) }
            }
        }
    }
}