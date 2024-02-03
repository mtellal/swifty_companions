package com.example.swifty_companion.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import com.example.swifty_companion.models.CoalitionModel
import com.example.swifty_companion.models.UserDataModel
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

    var currentUser: MutableState<UserDataModel?> = mutableStateOf(null)
    var coalition: MutableState<CoalitionModel?> = mutableStateOf<CoalitionModel?>(null)
    var coalitionColor: Color = Color.White;


    fun initCoalition(c: Array<CoalitionModel>?) {
        if (c != null && c.size > 0) {
            if (c.size > 2)
                coalition.value = c[1]
            else coalition.value = c[0]
            try {
                coalitionColor = Color(android.graphics.Color.parseColor(coalition.value!!.color))
            } catch (e: Exception) {
                println("Error: (AppNavigationViewModel) initCoalition failed - invalid color => $e")
            }
        }
    }


    fun loadUserData(userId: Int?) {
        if (currentUser.value == null || currentUser.value!!.id != userId) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    println("Loading user data ...")
                    currentUser.value = auth.value.userDataRequest(userId)
                    if (currentUser.value != null && currentUser.value!!.login != null) {
                        println("loading coalition ...")
                        initCoalition(auth.value.userCoalition(currentUser.value!!.login))
                        println("coalition loaded !")
                    }
                    println("User data loaded !")
                }
            }
        }
    }

    fun setSearchLogin(value: String) {
        previousSearchLogin.value = searchLogin.value;
        searchLogin.value = value;
    }

    fun searchUsers() {
        if (previousSearchLogin.value != searchLogin.value) {
            previousSearchLogin.value = searchLogin.value;
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    println("Searching users ...")
                    usersSearchList!!.value = auth.value.findPeerRequest(searchLogin.value)
                    if (usersSearchList == null || usersSearchList.value!!.isEmpty()) {
                        error.value = true;
                    } else
                        error.value = false
                    println("Users searched loaded !")
                }
            }
        }
    }

    fun fetchAccessToken() {
        if (auth.value.token == null) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    println("Fetching access token")
                    auth.value.fetchAccessToken()
                    println("Access token fetched !")
                }
            }
        }
    }


}