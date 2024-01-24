package com.example.swifty_companion.screens

import android.text.Layout
import android.widget.Space
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.swifty_companion.R
import com.example.swifty_companion.components.Header
import com.example.swifty_companion.components.ProfilePicture
import com.example.swifty_companion.components.Projects
import com.example.swifty_companion.components.Skills
import com.example.swifty_companion.models.CoalitionModel
import com.example.swifty_companion.models.UserDataModel
import com.example.swifty_companion.utils.Auth
import com.example.swifty_companion.utils.Colors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun UserScreen(
    navHostController: NavHostController,
    auth: Auth,
    userId: Int?,
) {
    val scrollState = rememberScrollState()
    var user: UserDataModel? by remember { mutableStateOf<UserDataModel?>(null) }
    var coalition: Array<CoalitionModel>? by remember { mutableStateOf<Array<CoalitionModel>?>(null) }

    LaunchedEffect(true) {
        withContext(Dispatchers.IO) {
                user = auth.userDataRequest(userId)
                coalition = auth.userCoalition(userId)
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