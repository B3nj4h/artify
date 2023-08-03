package com.example.shelta.data.remote.auth

import com.example.shelta.common.TokenResponse

data class UserDto(
    val token: String,
    val user: User
)

fun UserDto.toToken(): TokenResponse {
    return TokenResponse(
        token = token
    )
}