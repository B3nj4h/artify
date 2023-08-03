package com.example.shelta.presentation.main

sealed class MainScreenEvents {
    object OnSearchClicked: MainScreenEvents()
    object OnProfileClicked: MainScreenEvents()
}