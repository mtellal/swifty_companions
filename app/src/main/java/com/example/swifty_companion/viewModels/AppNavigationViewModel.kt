package com.example.swifty_companion.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.rememberNavController
import com.example.swifty_companion.models.UserSearchModel
import com.example.swifty_companion.utils.Auth
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class AppNavigationViewModel: ViewModel() {

    var auth: MutableState<Auth> = mutableStateOf(Auth())
    var searchLogin: MutableState<String> = mutableStateOf("")
    var usersSearchList: MutableState<List<UserSearchModel>?> = mutableStateOf(listOf())
    var error: MutableState<Boolean> = mutableStateOf(false)

}