package com.example.shelta.presentation.change_name

import com.example.shelta.presentation.change_contact.ChangeContactScreenEvents

sealed class ChangeNameScreenEvents {
    data class OnNameChanged(val name: String): ChangeNameScreenEvents()
    object OnSubmitClicked: ChangeNameScreenEvents()
}