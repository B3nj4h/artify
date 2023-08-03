package com.example.shelta.presentation.change_password

import com.example.shelta.common.Message

data class ChangePasswordScreenState(
    val isLoading: Boolean = false,
    val message: String = "",
    val response: Message? = null
)