package com.example.shelta.presentation.main.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shelta.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    onResetSearchState: () -> Unit,
    onSearchClicked:() -> Unit,
    onBackClicked: () -> Unit,
    onFilterClicked: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp)
        ,
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(20.dp),
        value = value,
        onValueChange = { onValueChange(it) },
        leadingIcon = {
            IconButton(onClick = { onBackClicked() }) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "search")
            }
        },
        trailingIcon = {
            Row {
                if (value.isNotEmpty()){
                    IconButton(onClick = {
                        onValueChange("")
                        onResetSearchState()
                    }) {
                        Icon(
                            modifier = Modifier
                                .padding(5.dp)
                                .size(15.dp),
                            painter = painterResource(id = R.drawable.cancel),
                            contentDescription = "close"
                        )
                    }
                }
                IconButton(onClick = { onFilterClicked() }) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.filter),
                        contentDescription = "filter"
                    )
                }
            }
        },
        placeholder = { Text(text = "search here ....") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onResetSearchState()
                onSearchClicked()
                keyboardController?.hide()
            }
        )
    )
}

@Preview
@Composable
fun SearchBarPrev() {
    SearchBar("benjamin",{},{},{}, {},{})
}