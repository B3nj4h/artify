package com.example.shelta.presentation.welcome

sealed class WelcomeScreenEvents {
    object OnRegisterClicked: WelcomeScreenEvents()
    object OnLoginClicked: WelcomeScreenEvents()
    object TermsAndConditionsClicked: WelcomeScreenEvents()
}