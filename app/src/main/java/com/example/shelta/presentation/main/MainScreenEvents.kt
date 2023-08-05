package com.example.shelta.presentation.main

import android.net.Uri

sealed class MainScreenEvents {
    object OnProfileClicked: MainScreenEvents()
    object OnSearchClicked: MainScreenEvents()
    data class OnUploadClicked(val onCLick: Boolean): MainScreenEvents()
    data class OnPostArtWorkClicked(val selectedImageUri: Uri?): MainScreenEvents()
}