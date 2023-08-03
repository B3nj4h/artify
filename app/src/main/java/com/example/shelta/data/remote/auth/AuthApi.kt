package com.example.shelta.data.remote.auth

import com.example.shelta.common.Message
import com.example.shelta.domain.model.LogIn
import com.example.shelta.domain.model.SignUp
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @POST("signup")
    suspend fun signUp(
        @Body request: SignUp
    ): Message

    @POST("login")
    suspend fun logIn(
        @Body request: LogIn
    ): UserDto

    @GET("authenticate")
    suspend fun authenticate(
        @Header("Authorization") token: String
    )

}