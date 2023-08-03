package com.example.shelta.presentation.auth.login.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shelta.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    isPhoneNUmber: Boolean? = false,
//    isError: Boolean? = false,
//    label: String,
    onClick: () -> Unit,
    placeholder: String = "",
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: Painter,
    trailingIcon: Painter? = null,
    onTrailingIconClicked: () -> Unit,
) {
    if (isPhoneNUmber == true){
        TextField(
//            isError = isError ?: false,
//            label = { Text(text = label)},
            singleLine = true,
            placeholder = { Text(text = placeholder)},
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .clickable { onClick() }
            ,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Gray,
                unfocusedIndicatorColor = Color.Gray,
                containerColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            shape = RectangleShape,
            value = value,
            onValueChange = { onValueChange(it) },
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = leadingIcon,
                    contentDescription = "leading icon") },
            trailingIcon = {
                if (trailingIcon != null){
                    IconButton(onClick = { onTrailingIconClicked() }) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = trailingIcon, contentDescription = "trailing icon")
                    }
                }
            }
        )
    } else {
        TextField(
//            isError = isError ?: false,
            placeholder = { Text(text = placeholder)},
            singleLine = true,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .clickable { onClick() }
            ,
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
                if (trailingIcon != null){
                    IconButton(onClick = { onTrailingIconClicked() }) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            painter = trailingIcon, contentDescription = "trailing icon")
                    }
                }
            }
        )
    }
}