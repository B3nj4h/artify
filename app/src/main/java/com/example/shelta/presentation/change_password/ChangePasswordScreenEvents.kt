package com.example.shelta.presentation.change_password

import com.example.shelta.presentation.change_name.ChangeNameScreenEvents

sealed class ChangePasswordScreenEvents {
    data class OnPreviousPasswordChanged(val previousPassword: String): ChangePasswordScreenEvents()
    data class OnNewPasswordChanged(val newPassword: String): ChangePasswordScreenEvents()
    object OnBackClicked: ChangePasswordScreenEvents()
    object OnSubmitClicked: ChangePasswordScreenEvents()
}