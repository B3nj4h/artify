package com.example.shelta.presentation.main

import com.example.shelta.common.Message

data class PostArtWorkState(
    val isLoading: Boolean = false,
    val message: Message? = null,
    val error: String = ""
)