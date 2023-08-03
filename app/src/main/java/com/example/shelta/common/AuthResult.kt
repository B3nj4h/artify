package com.example.shelta.common

sealed class AuthResult<T>(val data: T? = null, val message: String? = null){
    class Authorized<T>(data: T? = null): AuthResult<T>(data = data)
    class Unauthorized<T>: AuthResult<T>()
    class Error<T>(message: String?): AuthResult<T>(message = message)
}