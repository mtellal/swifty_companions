package com.example.swifty_companion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.swifty_companion.screens.SearchScreen
import com.example.swifty_companion.screens.UserScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navhost = rememberNavController()
            NavHost(navController = navhost, startDestination = "search") {
                composable("search") {
                    //SearchScreen(navhost)
                    UserScreen(
                        navHostController = navhost,
                        login = "mtellal"
                    )
                }
                composable(
                    "user/{login}",
                    arguments = listOf(
                        navArgument(name = "login") {
                            type = NavType.StringType
                            nullable = false
                        },
                    )
                ) {
                    UserScreen(
                        navHostController = navhost,
                        login = it.arguments?.getString("login"))
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

