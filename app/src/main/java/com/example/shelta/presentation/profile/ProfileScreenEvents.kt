package com.example.shelta.presentation.profile

sealed class ProfileScreenEvents {
    data class OnChangeNameClicked(val id: String): ProfileScreenEvents()
    data class OnChangeEmailClicked(val id: String): ProfileScreenEvents()
    data class OnChangeContactClicked(val id: String): ProfileScreenEvents()
    data class OnChangePasswordClicked(val id: String): ProfileScreenEvents()
    object OnSendFeedbackClicked: ProfileScreenEvents()
    object OnLogoutClicked: ProfileScreenEvents()
    object OnBackClicked: ProfileScreenEvents()
}