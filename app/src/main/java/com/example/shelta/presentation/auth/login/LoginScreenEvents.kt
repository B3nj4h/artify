package com.example.shelta.presentation.auth.login

sealed class LoginScreenEvents {
    object OnBackCLicked: LoginScreenEvents()
    data class OnEmailChanged(val email: String): LoginScreenEvents()
    data class OnPasswordChanged(val password: String): LoginScreenEvents()
    object OnForgotPasswordClicked: LoginScreenEvents()
    object OnLoginClicked: LoginScreenEvents()
    object OnNavigateToMainScreen: LoginScreenEvents()
    object OnSignupClicked: LoginScreenEvents()
}