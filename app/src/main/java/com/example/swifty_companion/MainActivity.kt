package com.example.swifty_companion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.swifty_companion.screens.SearchScreen
import com.example.swifty_companion.screens.UserScreen
import com.example.swifty_companion.utils.Auth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val auth by remember { mutableStateOf<Auth>(Auth()) }
            val navhost = rememberNavController()
            NavHost(navController = navhost, startDestination = "searchScreen") {
                composable("searchScreen") {
                    SearchScreen(navhost, auth)
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
                        userId = it.arguments?.getInt("userId"))
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

