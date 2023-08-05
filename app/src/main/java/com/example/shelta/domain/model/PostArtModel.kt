package com.example.shelta.domain.model

import okhttp3.MultipartBody

data class PostArtModel(
    val image_url: MultipartBody.Part,
    val name: String,
    val price: String,
    val contact: String,
    val rating: String,
    val description: String,
)