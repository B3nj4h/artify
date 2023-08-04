package com.example.shelta.domain.model

import androidx.compose.ui.graphics.ImageBitmap

data class PostArtModel(
    val image: ImageBitmap,
    val name: String,
    val price: String,
    val contact: String,
    val rating: String,
    val description: String,
)