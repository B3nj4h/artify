package com.example.shelta.presentation.profile.components

import androidx.compose.ui.graphics.painter.Painter

data class ProfileItem(
    val name: String,
    val icon: Painter,
    val onClick: () -> Unit
)