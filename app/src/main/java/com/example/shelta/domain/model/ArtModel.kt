package com.example.shelta.domain.model

data class ArtModel(
    val name: String,
    val price: String,
    val contact: String,
    val image_url: String,
    val rating: Int
)

fun ArtModel.toArtDto(): ArtModel{
    return ArtModel(
        name, price, contact, image_url, rating
    )
}