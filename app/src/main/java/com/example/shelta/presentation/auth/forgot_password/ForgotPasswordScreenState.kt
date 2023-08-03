package com.example.shelta.presentation.auth.forgot_password

data class ForgotPasswordScreenState(
    val isLoading: Boolean = false,
    val message: String = "",
    val error: String = ""
)