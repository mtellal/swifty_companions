package com.example.swifty_companion.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavViewModelStoreProvider
import com.example.swifty_companion.components.Header
import com.example.swifty_companion.components.Projects
import com.example.swifty_companion.components.Skills
import com.example.swifty_companion.models.CoalitionModel
import com.example.swifty_companion.models.UserDataModel
import com.example.swifty_companion.utils.Auth
import com.example.swifty_companion.viewModels.AppNavigationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun UserScreen(
    navHostController: NavHostController,
    userId: Int?,
    viewModel: AppNavigationViewModel
) {
    val scrollState = rememberScrollState()

    LaunchedEffect(true) {
        viewModel.loadUserData(userId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    )
    {
        Header(
            navHostController,
            viewModel.currentUser.value,
            viewModel.coalition.value,
            viewModel.coalitionColor
        )
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Projects(viewModel.currentUser.value)
            Spacer(modifier = Modifier.height(20.dp))
            Skills(user = viewModel.currentUser.value)
        }
    }
}