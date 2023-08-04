package com.example.shelta.presentation.main

import com.example.shelta.domain.model.ArtModel

data class MainScreenState(
    val isLoading: Boolean = false,
    val artModels: List<ArtModel> = emptyList(),
    val message: String = ""
)