package com.example.swifty_companion.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.example.swifty_companion.models.UserSearchModel

class SearchScreenViewModel(
) :  ViewModel() {

    var access_token:  MutableState<String?> = mutableStateOf<String?>(null)

}