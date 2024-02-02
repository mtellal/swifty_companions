package com.example.swifty_companion.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import com.example.swifty_companion.models.UserSearchModel
import com.example.swifty_companion.utils.Auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class AppNavigationViewModel : ViewModel() {

    var auth: MutableState<Auth> = mutableStateOf(Auth())
    var searchLogin: MutableState<String> = mutableStateOf("")
    var previousSearchLogin: MutableState<String> = mutableStateOf("")
    var usersSearchList: MutableState<List<UserSearchModel>?> = mutableStateOf(listOf())
    var error: MutableState<Boolean> = mutableStateOf(false)

    var currentUser: MutableState<UserSearchModel>? = null

    fun setSearchLogin(value: String) {
        previousSearchLogin.value = searchLogin.value;
        searchLogin.value = value;
    }

    fun searchUsers() {
        if (previousSearchLogin.value != searchLogin.value) {
            previousSearchLogin.value = searchLogin.value;
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    usersSearchList!!.value = auth.value.findPeerRequest(searchLogin.value)
                    if (usersSearchList == null || usersSearchList.value!!.isEmpty()) {
                        error.value = true;
                    } else
                        error.value = false
                }
            }
        }
    }


}