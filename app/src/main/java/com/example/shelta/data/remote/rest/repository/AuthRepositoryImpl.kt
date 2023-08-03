package com.example.shelta.data.remote.rest.repository

import android.content.SharedPreferences
import com.example.shelta.common.AuthResult
import com.example.shelta.data.remote.auth.AuthApi
import com.example.shelta.domain.model.LogIn
import com.example.shelta.domain.model.SignUp
import com.example.shelta.domain.repository.AuthRepository
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val sharedPreferences: SharedPreferences
): AuthRepository {
    override suspend fun signUp(signUp: SignUp): AuthResult<Unit> {
        return try {
            authApi.signUp(
                request = SignUp(
                    email = signUp.email,
                    name = signUp.name,
                    password = signUp.password,
                    contact = signUp.contact,
                )
            )
            logIn(
                LogIn(
                    email = signUp.email,
                    password = signUp.password
                )
            )
        } catch (e: HttpException){
            if (e.code() == 401){
                AuthResult.Unauthorized()
            }else if (e.code() == 500){
                AuthResult.Error("email or contact already taken")
            } else {
                AuthResult.Error("unknown error occurred")
            }
        } catch (e: IOException){
            AuthResult.Error(message = "Can't reach server, check your internet connection")
        }
    }

    override suspend fun logIn(logIn: LogIn): AuthResult<Unit> {
        return try {
            val response = authApi.logIn(
                request = LogIn(
                    email = logIn.email,
                    password = logIn.password,
                )
            )
            sharedPreferences.edit()
                .putString("token", response.token)
                .apply()
            sharedPreferences.edit()
                .putString("name", response.user.name)
                .apply()
            AuthResult.Authorized()
        } catch (e: HttpException){
            if (e.code() == 401){
                AuthResult.Unauthorized()
            }else if (e.code() == 404){
                AuthResult.Error("Wrong credentials")
            } else {
                AuthResult.Error("unknown error occurred")
            }
        } catch (e: IOException){
            AuthResult.Error("Can't reach server, check your internet connection")
        }
    }

    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            val token = sharedPreferences.getString("token", null) ?: return AuthResult.Unauthorized()
            authApi.authenticate("Bearer $token")
            AuthResult.Authorized()
        } catch (e: HttpException){
            if (e.code() == 401){
                AuthResult.Unauthorized()
            } else {
                AuthResult.Error("Unknown error occurred")
            }
        } catch (e: IOException){
            AuthResult.Error("Can't reach server, check your internet connection")
        }
    }
}