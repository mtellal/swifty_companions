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
fun SearchScreen(navhost: NavHostController, auth: Auth) {
    var usersList by remember {
        mutableStateOf<List<UserSearchModel>?>(
            listOf(
                UserSearchModel(
                    id = 74705,
                    login = "efarinha",
                    first_name = "Eduardo",
                    last_name = "Farinha",
                    image = UserImage(
                        link = "https://cdn.intra.42.fr/users/8f5c7ca149f99ed9b6f12b2fe1c7cf52/efarinha.jpg",
                        versions = Versions(
                            large = "https://cdn.intra.42.fr/users/fd00fd067575c782cb9ddb0e8c59e2ce/large_efarinha.jpg",
                            medium = "https://cdn.intra.42.fr/users/497a354e670c30153176ed519fa74c8e/medium_efarinha.jpg",
                            small = "https://cdn.intra.42.fr/users/36548218e5ba33f8ed65a6db7e0405c6/small_efarinha.jpg",
                            micro = "https://cdn.intra.42.fr/users/e33d1e10403e3a28df8c68980c39d915/micro_efarinha.jpg"
                        )
                    )
                ), UserSearchModel(
                    id = 74705,
                    login = "efarinha",
                    first_name = "Eduardo",
                    last_name = "Farinha",
                    image = UserImage(
                        link = "https://cdn.intra.42.fr/users/8f5c7ca149f99ed9b6f12b2fe1c7cf52/efarinha.jpg",
                        versions = Versions(
                            large = "https://cdn.intra.42.fr/users/fd00fd067575c782cb9ddb0e8c59e2ce/large_efarinha.jpg",
                            medium = "https://cdn.intra.42.fr/users/497a354e670c30153176ed519fa74c8e/medium_efarinha.jpg",
                            small = "https://cdn.intra.42.fr/users/36548218e5ba33f8ed65a6db7e0405c6/small_efarinha.jpg",
                            micro = "https://cdn.intra.42.fr/users/e33d1e10403e3a28df8c68980c39d915/micro_efarinha.jpg"
                        )
                    )
                )
            )
        )
    }
    val coroutine = rememberCoroutineScope()
    var textField by remember { mutableStateOf("") }
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
        SearchBar(textField,
            onChangeValue = { v -> if (v.length < 50) textField = v },
            findPeer = {
                coroutine.launch {
                    withContext(Dispatchers.IO) {
                        usersList = auth.findPeerRequest(textField)
                        println("UserList => $usersList")
                    }
                }
            })

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(5.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
        ) {
            if (usersList != null) {
                if (usersList!!.isEmpty()) {
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
                items(usersList!!) {
                    UserSearchInfo(it, navhost)
                }
            }
        }
    }
}
