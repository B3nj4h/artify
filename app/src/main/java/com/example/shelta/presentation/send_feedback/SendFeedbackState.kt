package com.example.shelta.presentation.send_feedback

import com.example.shelta.common.Message

data class SendFeedbackState(
    val isLoading: Boolean = false,
    val message: String = "",
    val response: Message? = null
)