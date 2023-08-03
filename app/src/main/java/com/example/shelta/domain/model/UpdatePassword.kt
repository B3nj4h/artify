package com.example.shelta.domain.model

data class UpdatePassword(
    val oldPassword: String,
    val newPassword: String
)