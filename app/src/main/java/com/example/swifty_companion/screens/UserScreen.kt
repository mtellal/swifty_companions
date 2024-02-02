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
import com.example.swifty_companion.components.Header
import com.example.swifty_companion.components.Projects
import com.example.swifty_companion.components.Skills
import com.example.swifty_companion.models.CoalitionModel
import com.example.swifty_companion.models.UserDataModel
import com.example.swifty_companion.utils.Auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun UserScreen(
    navHostController: NavHostController,
    auth: Auth,
    userId: Int?,
) {
    val scrollState = rememberScrollState()
    var user by remember { mutableStateOf<UserDataModel?>(null) }
    var coalition by remember { mutableStateOf<Array<CoalitionModel>?>(null) }

    LaunchedEffect(true) {
        withContext(Dispatchers.IO) {
            user = auth.userDataRequest(userId)
            println("USER => $user")
            if (user != null && user!!.login != null) {
                println("before coalition")
                coalition = auth.userCoalition(user!!.login)
                if (coalition != null && coalition!!.size > 0) {
                    println("COALITION SIZE ${coalition?.size}")
                    for (i in coalition!!) {
                        println(i)
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    )
    {
        Header(navHostController, user, coalition)
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Projects(user)
            Spacer(modifier = Modifier.height(20.dp))
            Skills(user = user)
        }
    }
}