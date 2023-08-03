package com.example.shelta.presentation.auth.forgot_password

sealed class ForgotPasswordScreenEvents{
    object OnBackCLicked: ForgotPasswordScreenEvents()
    object OnSendLinkClicked: ForgotPasswordScreenEvents()
    data class OnChangeEmail(val email: String): ForgotPasswordScreenEvents()
}
