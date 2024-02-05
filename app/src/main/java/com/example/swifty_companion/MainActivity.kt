package com.example.swifty_companion

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.swifty_companion.views.SearchScreen
import com.example.swifty_companion.views.UserScreen
import com.example.swifty_companion.viewModels.AppNavigationViewModel

class MainActivity : ComponentActivity() {

    private lateinit  var viewModel: AppNavigationViewModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= ViewModelProvider(this)[AppNavigationViewModel::class.java]
        setContent {
            AppNavigation(viewModel)
        }
    }
}

@Composable
fun AppNavigation(
    viewModel: AppNavigationViewModel
) {

    val navHost = rememberNavController()

    LaunchedEffect(true) {
        viewModel.fetchAccessToken();
    }

    NavHost(navController = navHost, startDestination = "searchScreen") {
        composable("searchScreen") {
            SearchScreen(
                viewModel,
                setSearchLogin = { viewModel.setSearchLogin(it) },
                usersSearchList = viewModel.usersSearchList.value,
                searchUsers = { viewModel.searchUsers() },
                _navhost = navHost,
                viewModel.error.value,
                setError = { v -> viewModel.error.value = v }
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
                navHostController = navHost,
                userId = it.arguments?.getInt("userId"),
                viewModel
            )
        }
    }
}


