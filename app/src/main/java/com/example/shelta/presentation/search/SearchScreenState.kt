package com.example.shelta.presentation.search

import com.example.shelta.domain.model.ArtModel

data class SearchScreenState(
    val isLoading: Boolean = false,
    val artModels: List<ArtModel> = emptyList(),
    val message: String = ""
)