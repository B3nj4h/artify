package com.example.shelta.domain.model

import okhttp3.MultipartBody
import okhttp3.RequestBody

data class PostArtModel(
    val image_url: MultipartBody.Part,
    val name: RequestBody,
    val price: RequestBody,
    val contact: RequestBody,
    val rating: RequestBody,
    val description: RequestBody,
)