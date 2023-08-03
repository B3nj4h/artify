package com.example.shelta.presentation.change_contact

sealed class ChangeContactScreenEvents {
    data class OnContactChanged(val contact: String): ChangeContactScreenEvents()
    object OnBackClicked: ChangeContactScreenEvents()
    object OnSubmitClicked: ChangeContactScreenEvents()
}