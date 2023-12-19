package com.example.swifty_companion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.swifty_companion.models.UserImage
import com.example.swifty_companion.models.UserSearchModel
import com.example.swifty_companion.models.Versions
import com.example.swifty_companion.screens.SearchScreen
import com.example.swifty_companion.screens.UserScreen
import com.example.swifty_companion.utils.Auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val auth by remember { mutableStateOf<Auth>(Auth()) }
            val coroutine = rememberCoroutineScope()

            var searchLogin by remember { mutableStateOf("") }
            var usersSearchLists by remember {
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

            val navhost = rememberNavController()
            NavHost(navController = navhost, startDestination = "searchScreen") {
                composable("searchScreen") {
                    SearchScreen(
                        searchLogin,
                        setSearchLogin = { searchLogin = it},
                        usersSearchLists = usersSearchLists,
                        searchUsers = {
                            coroutine.launch {
                                kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                                    usersSearchLists = auth.findPeerRequest(searchLogin)
                                    kotlin.io.println("UserList => $usersSearchLists")
                                }
                            }
                        },
                        navhost, auth
                    )
                    /*UserScreen(
                        navHostController = navhost,
                        login = "mtellal"
                    )*/
                }
                composable(
                    "userScreen/{userId}",
                    arguments = listOf(
                        navArgument(name = "userId") {
                            type = NavType.IntType
                            nullable = false
                        },
                    )
                ) {
                    UserScreen(
                        navHostController = navhost,
                        auth,
                        userId = it.arguments?.getInt("userId")
                    )
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

