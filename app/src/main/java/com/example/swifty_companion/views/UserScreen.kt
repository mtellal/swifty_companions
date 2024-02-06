package com.example.swifty_companion.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.swifty_companion.components.Header
import com.example.swifty_companion.components.Projects
import com.example.swifty_companion.components.Skills
import com.example.swifty_companion.viewModels.AppNavigationViewModel

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

    if (viewModel.isLoadingUserDatas.value) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(40.dp)
            )
        }
    }
    else {
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

}