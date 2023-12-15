package com.example.swifty_companion

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavArgument
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.example.swifty_companion.ui.theme.Swifty_companionTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navhost = rememberNavController()
            NavHost(navController = navhost, startDestination = "search") {
                composable("search") {
                    Home(navhost)
                }
                composable(
                    "user/{login}",
                    arguments = listOf(
                        navArgument(name = "login") {
                            type = NavType.StringType
                            nullable = false
                        }
                    )
                    ) {
                        Text(text = "Hello ${it.arguments?.getString("login")}")
                }
            }
        }
    }
}

@Composable
fun Home(navhost: NavHostController) {
    val auth by remember { mutableStateOf<Auth>(Auth()) }
    var usersList by remember {
        mutableStateOf<List<UserInfo>?>(
            listOf(
                UserInfo(
                    login = "efarinha",
                    first_name = "Eduardo",
                    last_name = "Farinha",
                    email = "efarinha@student.42luxembourg.lu",
                    url = "https://api.intra.42.fr/v2/users/efarinha",
                    displayName = "null",
                    image = UserImage(
                        link = "https://cdn.intra.42.fr/users/8f5c7ca149f99ed9b6f12b2fe1c7cf52/efarinha.jpg",
                        versions = Versions(
                            large = "https://cdn.intra.42.fr/users/fd00fd067575c782cb9ddb0e8c59e2ce/large_efarinha.jpg",
                            medium = "https://cdn.intra.42.fr/users/497a354e670c30153176ed519fa74c8e/medium_efarinha.jpg",
                            small = "https://cdn.intra.42.fr/users/36548218e5ba33f8ed65a6db7e0405c6/small_efarinha.jpg",
                            micro = "https://cdn.intra.42.fr/users/e33d1e10403e3a28df8c68980c39d915/micro_efarinha.jpg"
                        )
                    )
                ), UserInfo(
                    login = "efarinha",
                    first_name = "Eduardo",
                    last_name = "Farinha",
                    email = "efarinha@student.42luxembourg.lu",
                    url = "https://api.intra.42.fr/v2/users/efarinha",
                    displayName = "null",
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
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        SearchBar(textField, onChangeValue = { v -> textField = v }, findPeer = {
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
                items(usersList!!) {
                    UserSearchInfo(it, navhost)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    //Home()
}

