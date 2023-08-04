package com.example.shelta.presentation.main

sealed class MainScreenEvents {
    object OnUploadClicked: MainScreenEvents()
    object OnProfileClicked: MainScreenEvents()
    object OnSearchClicked: MainScreenEvents()
}