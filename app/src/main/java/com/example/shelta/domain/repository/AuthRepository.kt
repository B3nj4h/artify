package com.example.shelta.domain.repository

import com.example.shelta.common.AuthResult
import com.example.shelta.domain.model.LogIn
import com.example.shelta.domain.model.SignUp

interface AuthRepository {
    suspend fun signUp(signUp: SignUp): AuthResult<Unit>
    suspend fun logIn(logIn: LogIn): AuthResult<Unit>
    suspend fun authenticate(): AuthResult<Unit>
}