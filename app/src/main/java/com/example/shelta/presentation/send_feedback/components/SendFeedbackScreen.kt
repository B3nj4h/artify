//package com.example.shelta.presentation.send_feedback.components
//
//import android.annotation.SuppressLint
//import android.widget.Toast
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.rounded.ArrowDropDown
//import androidx.compose.material3.DropdownMenu
//import androidx.compose.material3.DropdownMenuItem
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.NavHostController
//import com.example.shelta.R
//import com.example.shelta.presentation.auth.login.components.CustomButton
//import com.example.shelta.presentation.auth.login.components.CustomTextField
//import com.example.shelta.presentation.screens.Screens
//import com.example.shelta.presentation.send_feedback.SendFeedbackEvents
//import com.example.shelta.presentation.send_feedback.SendFeedbackViewModel
//import com.example.shelta.presentation.uievent.UiEvent
//
//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SendFeedbackScreen(
//    navHostController: NavHostController,
//    viewModel: SendFeedbackViewModel = hiltViewModel()
//) {
//    val state = viewModel.state.value
//    val context = LocalContext.current
//
//    LaunchedEffect(key1 = true){
//        viewModel.uiEvent.collect{
//            when(it){
//                is UiEvent.ShowToast -> {
//                    Toast.makeText(
//                        context,
//                        it.message,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//                else -> Unit
//            }
//        }
//    }
//
//    val dropdownItems : List<String> = listOf(
//        "Male",
//        "Female",
//        "Other",
//        "Rather not to say"
//    )
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                navigationIcon = {
//                    IconButton(
//                        onClick = {
//                            navHostController.navigate(Screens.ProfileScreen.route){
//                                popUpTo(Screens.ProfileScreen.route){
//                                    inclusive = true
//                                }
//                            }
//                        }) {
//                        Icon(
//                            modifier = Modifier.size(20.dp),
//                            painter = painterResource(id = R.drawable.arrow_back), contentDescription = "back") } },
//                title = { Text(text = "Change name") }
//            )
//        }
//    ) {
//        Box(
//            modifier = Modifier.padding(16.dp)
//        ){
//            Column() {
//                CustomTextField(
//                    isError = ,
//                    onClick = {},
//                    placeholder = "Enter email",
//                    value = viewModel.email,
//                    onValueChange = { email -> viewModel.onEvent(SendFeedbackEvents.OnEmailChanged(email))},
//                    leadingIcon = painterResource(id = R.drawable.profile)
//                ) { }
//                Spacer(modifier = Modifier.height(10.dp))
//                CustomTextField(
//                    onClick = {},
//                    placeholder = "Enter incident",
//                    value = viewModel.incident,
//                    onValueChange = { incident -> viewModel.onEvent(SendFeedbackEvents.OnIncidentChanged(incident))},
//                    leadingIcon = painterResource(id = R.drawable.list)
//                ) { }
//                Spacer(modifier = Modifier.height(10.dp))
//                CustomTextField(
//                    onClick = {},
//                    placeholder = "Enter phone number",
//                    value = viewModel.phoneNumber,
//                    onValueChange = { phoneNumber -> viewModel.onEvent(SendFeedbackEvents.OnPhoneNumberChanged(phoneNumber))},
//                    leadingIcon = painterResource(id = R.drawable.phone)
//                ) { }
//                Spacer(modifier = Modifier.height(10.dp))
//                Row(
//                    modifier = Modifier.fillMaxWidth()
//                        .padding(horizontal = 16.dp)
//                    ,
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Text(
//                        text = viewModel.gender,
//                    )
//                    IconButton(onClick = { viewModel.isExpandable = true }) {
//                        Icon(imageVector = Icons.Rounded.ArrowDropDown, contentDescription = "Drop")
//                        DropdownMenu(
//                            expanded = viewModel.isExpandable,
//                            onDismissRequest = { viewModel.isExpandable = false }
//                        ) {
//                            dropdownItems.forEach { text ->
//                                DropdownMenuItem(
//                                    text = { Text(text = text) },
//                                    onClick = {
//                                        viewModel.isExpandable = false
//                                        viewModel.onEvent(SendFeedbackEvents.OnGenderChanged(text))
//                                    })
//                            }
//                        }
//                    }
//                }
//                Spacer(modifier = Modifier.height(10.dp))
//                CustomTextField(
//                    onClick = {},
//                    placeholder = "Enter first name",
//                    value = viewModel.firstName,
//                    onValueChange = { firstName -> viewModel.onEvent(SendFeedbackEvents.OnFirstNameChanged(firstName))},
//                    leadingIcon = painterResource(id = R.drawable.profile)
//                ) { }
//                Spacer(modifier = Modifier.height(10.dp))
//                CustomTextField(
//                    onClick = {},
//                    placeholder = "Enter last name",
//                    value = viewModel.lastName,
//                    onValueChange = { lastName -> viewModel.onEvent(SendFeedbackEvents.OnLastNameChanged(lastName))},
//                    leadingIcon = painterResource(id = R.drawable.profile)
//                ) { }
//                Spacer(modifier = Modifier.height(10.dp))
//                CustomButton(text = "Submit", isLoading = state.isLoading) { viewModel.onEvent(
//                    SendFeedbackEvents.OnSubmitClicked) }
//            }
//        }
//    }
//}