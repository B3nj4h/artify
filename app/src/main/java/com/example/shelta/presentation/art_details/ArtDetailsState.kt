package com.example.shelta.presentation.art_details

import com.example.shelta.domain.model.ArtModel

data class ArtDetailsState(
    val isLoading: Boolean = false,
    val artModel: ArtModel? = null,
    val message: String = ""
)