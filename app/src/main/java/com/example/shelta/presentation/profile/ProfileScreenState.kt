package com.example.shelta.presentation.profile

import com.example.shelta.domain.model.Profile

data class ProfileScreenState(
    val isLoading: Boolean = false,
    val profile: Profile? = null,
    val message: String = ""
)