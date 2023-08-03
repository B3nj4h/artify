package com.example.shelta.presentation.main.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.shelta.R
import com.example.shelta.presentation.auth.login.components.CustomButton
import com.example.shelta.presentation.auth.login.components.CustomTextField
import com.example.shelta.presentation.main.MainScreenEvents
import com.example.shelta.presentation.main.MainScreenViewModel
import com.example.shelta.presentation.send_feedback.SendFeedbackEvents
import com.example.shelta.presentation.send_feedback.SendFeedbackViewModel
import com.example.shelta.presentation.ui.theme.Red
import com.example.shelta.presentation.uievent.UiEvent
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onNavigate: (UiEvent.OnNavigate) -> Unit,
    sendViewModel: SendFeedbackViewModel = hiltViewModel(),
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collectLatest{ event ->
            when(event){
                is UiEvent.OnNavigate -> onNavigate(event)
                is UiEvent.ShowToast -> {
                    Toast.makeText(
                        context,
                        event.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> Unit
            }
        }
    }

    LaunchedEffect(key1 = true){
        sendViewModel.uiEvent.collectLatest{ event ->
            when(event){
                is UiEvent.OnNavigate -> onNavigate(event)
                is UiEvent.ShowToast -> {
                    Toast.makeText(
                        context,
                        event.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> Unit
            }
        }
    }

    val dropdownItems : List<String> = listOf(
        "Male",
        "Female",
        "Other",
        "Rather not to say"
    )

    Scaffold(
        topBar = {
            CustomTopAppBar(
                onMenuClicked = {viewModel.onEvent(MainScreenEvents.OnProfileClicked)}
            )
        }
    ){
        Box(modifier = Modifier
            .padding(
                bottom = it.calculateBottomPadding(),
                top = it.calculateTopPadding()
            )
            .fillMaxSize()
        ){
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                ElevatedCard(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize()
                    ) {
                        //                    Text(
                        //                        text = "Help protect",
                        //                        fontSize = 40.sp,
                        //                        fontWeight = FontWeight.Bold,
                        //                    )
                        //                    Text(
                        //                        text = "the world",
                        //                        fontSize = 40.sp,
                        //                        fontWeight = FontWeight.Bold,
                        //                    )
                        //                    Text(
                        //                        text = "from black hat",
                        //                        fontSize = 40.sp,
                        //                        fontWeight = FontWeight.Bold,
                        //                        color = Red
                        //                    )
                        //                    Text(
                        //                        text = "hackers",
                        //                        fontSize = 40.sp,
                        //                        fontFamily = FontFamily.Monospace,
                        //                        color = Red
                        //                    )
                        Text(
                            modifier = Modifier.padding(16.dp),
                            fontSize = 30.sp,
                            text = "Incident Report"
                        )
                        CustomTextField(
                            onClick = {},
                            placeholder = "Enter email",
                            value = sendViewModel.email,
                            onValueChange = { email ->
                                sendViewModel.onEvent(
                                    SendFeedbackEvents.OnEmailChanged(
                                        email
                                    )
                                )
                            },
                            leadingIcon = painterResource(id = R.drawable.profile)
                        ) { }
                        Spacer(modifier = Modifier.height(10.dp))
                        CustomTextField(
                            onClick = {},
                            placeholder = "Enter incident",
                            value = sendViewModel.incident,
                            onValueChange = { incident ->
                                sendViewModel.onEvent(
                                    SendFeedbackEvents.OnIncidentChanged(
                                        incident
                                    )
                                )
                            },
                            leadingIcon = painterResource(id = R.drawable.list)
                        ) { }
                        Spacer(modifier = Modifier.height(10.dp))
                        CustomTextField(
                            isPhoneNUmber = true,
                            onClick = {},
                            placeholder = "Enter phone number",
                            value = sendViewModel.phoneNumber,
                            onValueChange = { phoneNumber ->
                                sendViewModel.onEvent(
                                    SendFeedbackEvents.OnPhoneNumberChanged(phoneNumber)
                                )
                            },
                            leadingIcon = painterResource(id = R.drawable.phone)
                        ) { }
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = sendViewModel.gender,
                            )
                            IconButton(onClick = { sendViewModel.isExpandable = true }) {
                                Icon(
                                    imageVector = Icons.Rounded.ArrowDropDown,
                                    contentDescription = "Drop"
                                )
                                DropdownMenu(
                                    expanded = sendViewModel.isExpandable,
                                    onDismissRequest = { sendViewModel.isExpandable = false }
                                ) {
                                    dropdownItems.forEach { text ->
                                        DropdownMenuItem(
                                            text = { Text(text = text) },
                                            onClick = {
                                                sendViewModel.isExpandable = false
                                                sendViewModel.onEvent(
                                                    SendFeedbackEvents.OnGenderChanged(
                                                        text
                                                    )
                                                )
                                            })
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        CustomTextField(
                            onClick = {},
                            placeholder = "Enter first name",
                            value = sendViewModel.firstName,
                            onValueChange = { firstName ->
                                sendViewModel.onEvent(
                                    SendFeedbackEvents.OnFirstNameChanged(
                                        firstName
                                    )
                                )
                            },
                            leadingIcon = painterResource(id = R.drawable.profile)
                        ) { }
                        Spacer(modifier = Modifier.height(10.dp))
                        CustomTextField(
                            onClick = {},
                            placeholder = "Enter last name",
                            value = sendViewModel.lastName,
                            onValueChange = { lastName ->
                                sendViewModel.onEvent(
                                    SendFeedbackEvents.OnLastNameChanged(
                                        lastName
                                    )
                                )
                            },
                            leadingIcon = painterResource(id = R.drawable.profile)
                        ) { }
                        Spacer(modifier = Modifier.height(10.dp))
                        CustomButton(
                            text = "Submit",
                            isLoading = sendViewModel.state.value.isLoading
                        ) {
                            sendViewModel.onEvent(
                                SendFeedbackEvents.OnSubmitClicked
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        //                    Card{
                        //                        val lottieCompositionSpec by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(
                        //                            R.raw.hacker))
                        //                        LottieAnimation(
                        //                            composition = lottieCompositionSpec,
                        //                            iterations = Int.MAX_VALUE,
                        //                            alignment = Alignment.Center
                        //                        )
                        //                    }
                    }
                }
            }
        }
    }
}

