package com.example.shelta.presentation.auth.login.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.shelta.R
import com.example.shelta.presentation.auth.login.LoginScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    placeholder: String = "",
    value: String,
    viewModel: LoginScreenViewModel,
    onValueChange: (String) -> Unit,
    leadingIcon: Painter,
    trailingIcon: Painter? = null
) {
    TextField(
        placeholder = { Text(text = placeholder) },
        singleLine = true,
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Gray,
            unfocusedIndicatorColor = Color.Gray,
            containerColor = Color.Transparent
        ),
        shape = RectangleShape,
        value = value,
        onValueChange = { onValueChange(it) },
        leadingIcon = {
            Icon(
                modifier = Modifier.size(30.dp),
                painter = leadingIcon,
                contentDescription = "leading icon") },
        trailingIcon = {
            if (trailingIcon != null) {
                IconButton(
                    onClick = { viewModel.passwordVisibility = !viewModel.passwordVisibility }) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = if (viewModel.passwordVisibility) trailingIcon else painterResource(
                            id = R.drawable.visibilityoff
                        ),
                        contentDescription = "trailing icon"
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (viewModel.passwordVisibility) VisualTransformation.None
        else PasswordVisualTransformation(),
    )
}