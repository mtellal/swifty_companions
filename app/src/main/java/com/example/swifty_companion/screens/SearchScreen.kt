package com.example.swifty_companion.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.swifty_companion.components.SearchBar
import com.example.swifty_companion.components.UserSearchInfo
import com.example.swifty_companion.models.UserImage
import com.example.swifty_companion.models.UserSearchModel
import com.example.swifty_companion.models.Versions
import com.example.swifty_companion.utils.Auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SearchScreen(
    searchLogin: String,
    setSearchLogin: (s: String) -> Unit,
    usersSearchLists: List<UserSearchModel>?,
    searchUsers: () -> Unit,
    navhost: NavHostController, 
    auth: Auth) {

    val coroutine = rememberCoroutineScope()
    var accessToken = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(true) {
        withContext(Dispatchers.IO) {
            accessToken.value = auth.httpRequest()
            println("token => ${accessToken.value}")
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        SearchBar(searchLogin,
            onChangeValue = { v -> if (v.length < 50) setSearchLogin(v) },
            findPeer = searchUsers
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(5.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
        ) {
            if (usersSearchLists != null) {
                if (usersSearchLists!!.isEmpty()) {
                    item() {
                        Row (
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Text(
                                text = "No users found",
                                color = Color(android.graphics.Color.parseColor("#b5b5b5"))
                            )
                        }
                    }
                }
                items(usersSearchLists!!) {
                    UserSearchInfo(it, navhost)
                }
            }
        }
    }
}
