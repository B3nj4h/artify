package com.example.shelta.presentation.profile.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.ListAlt
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shelta.R

@Composable
fun ElevatedCardHolder(
    onChangeNameClicked: () -> Unit,
    onChangeEmailCLicked: () -> Unit,
    onChangeContactClicked: () -> Unit,
    onChangePasswordClicked: () -> Unit,
    onLogoutClicked: () -> Unit
) {
    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp
        ),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.padding(20.dp)
    ) {
        LazyColumn(){
            item {
                SingleRow(name = "Change Name", icon = painterResource(id = R.drawable.profile)) { onChangeNameClicked()}
                Divider()
                SingleRow(name = "Change Email", icon = painterResource(id = R.drawable.mail)) { onChangeEmailCLicked()}
                Divider()
                SingleRow(name = "Change contact", icon = painterResource(id = R.drawable.phone)) { onChangeContactClicked()}
                Divider()
                SingleRow(name = "Change password", icon = painterResource(id = R.drawable.lock)) { onChangePasswordClicked()}
                Divider()
                SingleRow(name = "Log out", icon = painterResource(id = R.drawable.logout)) { onLogoutClicked()}
            }
        }
    }
}