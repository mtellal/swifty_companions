package com.example.swifty_companion.screens

import android.app.AlertDialog
import android.app.LocaleConfig
import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.swifty_companion.components.SearchBar
import com.example.swifty_companion.components.UserSearchInfo
import com.example.swifty_companion.models.UserImage
import com.example.swifty_companion.models.UserSearchModel
import com.example.swifty_companion.models.Versions
import com.example.swifty_companion.utils.Auth
import com.example.swifty_companion.viewModels.AppNavigationViewModel
import com.example.swifty_companion.viewModels.SearchScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun showAlert(
    title: String,
    message: String,
    error: Boolean,
    setError: (b: Boolean) -> Unit
) {
    if (error) {
        AlertDialog(
            onDismissRequest = { setError(!error) },
            title = { Text(text = title) },
            text = { Text(text = message) },
            confirmButton = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        setError(!error)
                    }) {
                        Text("OK")
                    }
                }
            }
        )
    }
}


@Composable
fun SearchScreen(
    viewModel: AppNavigationViewModel,
    setSearchLogin: (s: String) -> Unit,
    usersSearchList: List<UserSearchModel>?,
    searchUsers: () -> Unit,
    _navhost: NavHostController,
    error: Boolean,
    setError: (b: Boolean) -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        SearchBar(
            viewModel.searchLogin.value,
            onChangeValue = { v -> if (v.length < 25) setSearchLogin(v.filter { it.isLetter() }) },
            searchUsers = searchUsers,
        )

        if (error) {
            showAlert(
                title = "Error",
                message = "User not found",
                error,
                setError
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(5.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp)
            ) {
                if ((usersSearchList?.isEmpty() == false)) {
                    items(usersSearchList!!) {
                        UserSearchInfo(it, _navhost)
                    }
                }
            }
        }
    }
}
