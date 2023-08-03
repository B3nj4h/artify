package com.example.shelta.data.remote.rest.dto

import com.example.shelta.domain.model.Profile

data class ProfileUserDto(
    val contact: String,
    val created_at: String,
    val email: String,
    val email_verified_at: Any,
    val id: Int,
    val name: String,
    val updated_at: String
)

fun ProfileUserDto.toProfile(): Profile{
    return Profile(
        id = id,
        contact = contact,
        email = email,
        name = name,
    )
}