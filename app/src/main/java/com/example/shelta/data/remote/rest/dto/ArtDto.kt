package com.example.shelta.data.remote.rest.dto

import com.example.shelta.domain.model.ArtModel

data class ArtDto(
    val id: Int,
    val created_at: String?,
    val updated_at: String?,
    val image_path: String,
    val name: String,
    val price: String,
    val contact: String,
    val image_url: String,
    val rating: String,
    val description: String
)

fun ArtDto.toArtModel(): ArtModel {
    return ArtModel(
        id, description, name, price, contact, image_url, rating
    )
}