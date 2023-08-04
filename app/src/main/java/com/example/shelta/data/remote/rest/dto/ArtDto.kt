package com.example.shelta.data.remote.rest.dto

import com.example.shelta.domain.model.ArtModel

data class ArtDto(
    val name: String,
    val price: String,
    val contact: String,
    val image_url: String,
    val rating: Int
)

fun ArtDto.toArtModel(): ArtModel {
    return ArtModel(
        name, price, contact, image_url, rating
    )
}