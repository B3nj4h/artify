package com.example.shelta.presentation.auth.sign_up

sealed class SignUpScreenEvents {
    data class OnUserNameChanged(val username: String): SignUpScreenEvents()
    data class OnEmailChanged(val email: String): SignUpScreenEvents()
    data class OnPasswordChanged(val password: String): SignUpScreenEvents()
    data class OnContactChanged(val contact: String): SignUpScreenEvents()
    object OnSignUpCLicked: SignUpScreenEvents()
    object OnNavigateToMainScreen: SignUpScreenEvents()
    object OnBackCLicked: SignUpScreenEvents()
}