package com.example.shelta.presentation.change_name

import com.example.shelta.common.Message

data class ChangeNameScreenState(
    val isLoading: Boolean = false,
    val message: String = "",
    val response: Message? = null
)