package com.example.swifty_companion.viewModels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.core.content.getSystemService
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.swifty_companion.models.CoalitionModel
import com.example.swifty_companion.models.UserDataModel
import com.example.swifty_companion.models.UserSearchModel
import com.example.swifty_companion.utils.Auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.UnknownHostException
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class AppNavigationViewModel(application: Application) :
    AndroidViewModel(application = application) {
    var auth: MutableState<Auth> = mutableStateOf(Auth())
    var searchLogin: MutableState<String> = mutableStateOf("")
    var previousSearchLogin: MutableState<String> = mutableStateOf("")
    var usersSearchList: MutableState<List<UserSearchModel>?> = mutableStateOf(listOf())
    var error: MutableState<String?> = mutableStateOf(null)

    var currentUser: MutableState<UserDataModel?> = mutableStateOf(null)
    var coalition: MutableState<CoalitionModel?> = mutableStateOf<CoalitionModel?>(null)
    var coalitionColor: Color = Color.White;

    var isLoadingSearchUsers: MutableState<Boolean> = mutableStateOf(false)
    var isLoadingUserDatas: MutableState<Boolean> = mutableStateOf(false)

    fun isConnected(): Boolean {
        val connectivityManager =
            getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork;
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return (networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true)
    }

    private fun initCoalition(c: Array<CoalitionModel>?) {
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
                    isLoadingUserDatas.value = true;
                    if (auth.value.token == null) {
                        println("fetching access token ...")
                        auth.value.fetchAccessToken()
                        println("access token fetched !")
                    }
                    if (!isConnected()) {
                        error.value = "Internet Network not found"
                    } else {
                        currentUser.value = auth.value.userDataRequest(userId)
                        coalitionColor = Color.White
                        if (currentUser.value != null && currentUser.value!!.login != null) {
                            println("loading coalition ...")
                            initCoalition(auth.value.userCoalition(currentUser.value!!.login))
                            println("coalition loaded !")
                        }
                        println("User data loaded !")
                    }
                    isLoadingUserDatas.value = false;
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
                    isLoadingSearchUsers.value = true;
                    if (auth.value.token == null) {
                        println("fetching access token ...")
                        auth.value.fetchAccessToken()
                        println("access token fetched !")
                    }
                    try {
                        usersSearchList.value = auth.value.findPeerRequest(searchLogin.value)
                        if (usersSearchList.value == null || usersSearchList.value!!.isEmpty()) {
                            error.value = "Users not found";
                        } else
                            error.value = null
                    } catch (e: UnknownHostException) {
                        println("Error (host): searchUsers request => $e")
                        error.value = "Internet network not found"
                    } catch (e: Exception) {
                        println("Error: searchUsers request => $e")
                        error.value = "Users not found"
                    }
                    println("Users searched loaded !")
                    isLoadingSearchUsers.value = false;
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
//                    println("ACCESS TOKEN => ${auth.value.token}")
                    println("Access token fetched !")
                }
            }
        }
    }

}