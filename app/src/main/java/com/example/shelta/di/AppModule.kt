package com.example.shelta.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.shelta.common.AuthInterceptor
import com.example.shelta.constants.Constants
import com.example.shelta.data.remote.auth.AuthApi
import com.example.shelta.data.remote.rest.repository.AuthRepositoryImpl
import com.example.shelta.data.remote.rest.api.Api
import com.example.shelta.data.remote.rest.repository.RepositoryImpl
import com.example.shelta.domain.repository.AuthRepository
import com.example.shelta.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesOKHttpClient(sharedPreferences: SharedPreferences): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(AuthInterceptor(sharedPreferences))
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideApi(okHttpClient: OkHttpClient): Api {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(Api::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(api: Api): Repository {
        return RepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideAuthApi(): AuthApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun provideSharedPref(application: Application): SharedPreferences {
        return application.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(authApi: AuthApi, preferences: SharedPreferences): AuthRepository {
        return AuthRepositoryImpl(authApi, preferences)
    }
}