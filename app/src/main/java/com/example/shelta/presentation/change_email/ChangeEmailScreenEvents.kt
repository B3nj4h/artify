package com.example.shelta.presentation.change_email

sealed class ChangeEmailScreenEvents {
    data class OnEmailChanged(val email: String): ChangeEmailScreenEvents()
    object OnBackClicked: ChangeEmailScreenEvents()
    object OnSubmitClicked: ChangeEmailScreenEvents()
}