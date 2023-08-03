package com.example.shelta.common

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sharedPreferences: SharedPreferences
): Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .header("Authorization","Bearer ${sharedPreferences.getString("token",null)}")
            .build()
        return chain.proceed(request)
    }
}