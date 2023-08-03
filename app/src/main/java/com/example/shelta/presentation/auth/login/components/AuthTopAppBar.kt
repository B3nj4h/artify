package com.example.shelta.presentation.auth.login.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shelta.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthTopAppBar(
    navigationIcon: Painter,
    onClickNavigationIcon: () -> Unit,
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { onClickNavigationIcon() }) {
                Icon(
                    modifier = Modifier.padding(5.dp)
                        .size(20.dp)
                    ,
                    painter = navigationIcon,
                    contentDescription = "navigation icon")
            }
        },
        title = {},
    )
}

@Preview
@Composable
fun AuthTopAppBar() {
    AuthTopAppBar(
        navigationIcon = painterResource(id = R.drawable.arrow_back),
        onClickNavigationIcon = { /*TODO*/ })
}