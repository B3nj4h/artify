package com.example.shelta.domain.model

data class Feedback(
    val phone_number: String,
    val email: String,
    val incident: String,
    val gender: String,
    val first_name: String,
    val last_name: String
)