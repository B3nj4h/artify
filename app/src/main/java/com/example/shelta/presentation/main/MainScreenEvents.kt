package com.example.shelta.presentation.main

sealed class MainScreenEvents {
    object OnProfileClicked: MainScreenEvents()
    object OnSearchClicked: MainScreenEvents()
}