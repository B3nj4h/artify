package com.example.shelta.presentation.auth.login.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomButton(
    text: String,
    isLoading: Boolean = false,
    onCLick: () -> Unit
) {
    ElevatedButton(
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
        ,
        onClick = { onCLick() }
    ) {
        if (isLoading){
            Icon(imageVector = Icons.Rounded.Refresh, contentDescription = "loading icon")
        } else {
            Text(
                modifier = Modifier.padding(2.dp),
                text = text,
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}