package com.example.shelta.presentation.change_email

import com.example.shelta.common.Message

data class ChangeEmailScreenState(
    val isLoading: Boolean = false,
    val message: String = "",
    val response: Message? = null
)