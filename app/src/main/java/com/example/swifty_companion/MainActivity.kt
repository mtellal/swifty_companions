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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
            AppNavigation()
        }
    }
}


@Composable
fun AppNavigation() {
    val auth by remember { mutableStateOf<Auth>(Auth()) }
    val coroutine = rememberCoroutineScope()

    var searchLogin by remember { mutableStateOf("") }
    var usersSearchLists by remember {
        mutableStateOf<List<UserSearchModel>?>(listOf())
    }

    val navhost = rememberNavController()
    NavHost(navController = navhost, startDestination = "searchScreen") {
        composable("searchScreen") {
            SearchScreen(
                searchLogin,
                setSearchLogin = { searchLogin = it },
                usersSearchLists = usersSearchLists,
                searchUsers = {
                    coroutine.launch {
                        withContext(Dispatchers.IO) {
                            usersSearchLists = auth.findPeerRequest(searchLogin)
                        }
                    }
                },
                navhost, auth
            )
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


