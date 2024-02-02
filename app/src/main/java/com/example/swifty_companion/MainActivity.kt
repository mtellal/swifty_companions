package com.example.swifty_companion

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.example.swifty_companion.viewModels.AppNavigationViewModel
import com.example.swifty_companion.viewModels.SearchScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    val viewModel by viewModels<AppNavigationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation(viewModel)
        }
    }
}

@Composable
fun AppNavigation(
    viewModel: AppNavigationViewModel
) {

    val navhost = rememberNavController()

    LaunchedEffect(true) {
        withContext(Dispatchers.IO) {
            println("Fetching access tokne ...")
            viewModel.auth.value.fetchAccessToken()
            println("Access token fetched")
        }
    }

    NavHost(navController = navhost, startDestination = "searchScreen") {
        composable("searchScreen") {
            SearchScreen(
                viewModel,
                setSearchLogin = { viewModel.setSearchLogin(it) },
                usersSearchList = viewModel.usersSearchList.value,
                searchUsers = { viewModel.searchUsers() },
                navhost,
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
                navHostController = navhost,
                viewModel.auth.value,
                userId = it.arguments?.getInt("userId")
            )
        }
    }
}


