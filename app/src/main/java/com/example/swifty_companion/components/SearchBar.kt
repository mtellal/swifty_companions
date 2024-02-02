package com.example.swifty_companion.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    textField: String,
    onChangeValue: (s: String) -> Unit,
    searchUsers: () -> Unit,
) {
    val orientationLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    val localFocus = LocalFocusManager.current;

    fun submit(text: String): Unit {
        if (textField.isNotEmpty()) {
            searchUsers()
            localFocus.clearFocus()
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxHeight(if (orientationLandscape) 0.2f else 0.1f)
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            label = {
                Text(text = "Find a peer")
            },
            modifier = Modifier
                .fillMaxWidth(0.7f),
            value = textField,
            singleLine = true,
            onValueChange = onChangeValue,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Email Icon"
                )
            },
            keyboardActions = KeyboardActions(
                onDone = { submit(textField) }
            )
        )
        Spacer(modifier = Modifier.width(10.dp))
        OutlinedButton(
            modifier = Modifier
                .padding(top = 5.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(Color.White),
            onClick = { submit(textField) }
        ) {
            Text(text = "Search", color = Color.Black)
        }
    }

}