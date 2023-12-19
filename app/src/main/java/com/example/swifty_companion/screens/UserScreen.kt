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
import com.example.swifty_companion.models.UserDataModel
import com.example.swifty_companion.utils.Auth
import com.example.swifty_companion.utils.Colors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun Project(
    name: String,
    score: Int?
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(text = name, fontSize = 15.sp)
        Row {
            if (score != null && score > 0) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "check mark",
                    tint = Colors.green_primary
                )
                Text(text = score.toString(), color = Colors.green_primary)
            }
            else {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "check mark",
                    tint = Color.Red
                )
                Text(text = score.toString(), color = Color.Red)
            }
        }
    }
}


@Composable
fun Projects(user: UserDataModel?) {
    Column {
        Text(text = "Projects", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(10.dp))
        Surface(
            modifier = Modifier
                .shadow(elevation = 4.dp, RoundedCornerShape(10.dp))
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                if (user != null && user.projects_users.isNotEmpty()) {
                    user.projects_users.forEach {
                        if (it.final_mark != null &&
                            it.cursus_ids.isNotEmpty() &&
                            it.cursus_ids[0] == 21
                        ) {
                            Project(it.project.name, it.final_mark)
                        }
                    }
                } else {
                    Text(text = "No projects found")
                }
            }
        }
    }
}

@Composable
fun UserScreen(
    navHostController: NavHostController,
    auth: Auth,
    userId: Int?,
) {
    val scrollState = rememberScrollState()
    var user: UserDataModel? by remember { mutableStateOf<UserDataModel?>(null) }

    LaunchedEffect(true) {
        withContext(Dispatchers.IO) {
            user = auth.userDataRequest(userId)
            println("user set $user")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    )
    {
        Header(navHostController, user)
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Projects(user)
        }
    }
}