package com.example.swifty_companion.views

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.swifty_companion.components.SearchBar
import com.example.swifty_companion.components.UserSearchInfo
import com.example.swifty_companion.models.UserSearchModel
import com.example.swifty_companion.viewModels.AppNavigationViewModel


@Composable
fun showAlert(
    title: String,
    message: String,
    setError: (s: String?) -> Unit
) {
    AlertDialog(
        onDismissRequest = { setError(null) },
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
                    setError(null)
                }) {
                    Text("OK")
                }
            }
        }
    )
}


@Composable
fun SearchScreen(
    viewModel: AppNavigationViewModel,
    setSearchLogin: (s: String) -> Unit,
    usersSearchList: List<UserSearchModel>?,
    searchUsers: () -> Unit,
    _navhost: NavHostController,
    error: String?,
    setError: (s: String?) -> Unit
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

        if (error != null) {
            showAlert(
                title = "Error",
                message = error,
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
