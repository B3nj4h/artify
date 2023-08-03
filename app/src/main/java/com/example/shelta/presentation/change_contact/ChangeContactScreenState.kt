package com.example.shelta.presentation.change_contact

import com.example.shelta.common.Message

data class ChangeContactScreenState(
    val isLoading: Boolean = false,
    val message: String = "",
    val response: Message? = null
)