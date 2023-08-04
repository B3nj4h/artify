package com.example.shelta.domain.model

data class ArtModel(
    val id: Int,
    val description: String,
    val name: String,
    val price: String,
    val contact: String,
    val image_url: String,
    val rating: Int
)