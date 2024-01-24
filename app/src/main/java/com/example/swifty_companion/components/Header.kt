package com.example.swifty_companion.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.swifty_companion.R
import com.example.swifty_companion.models.CoalitionModel
import com.example.swifty_companion.models.UserDataModel
import com.example.swifty_companion.utils.Colors

@Composable
fun UserLevelBar(
    user: UserDataModel?
) {
    var level : String? = null
    var percent: String? = null
    var percentFloat: Float = 0f
    if (user != null && user.cursus_users.size > 1) {
        level = user.cursus_users[1].level.toInt().toString()
        percent = ((user.cursus_users[1].level - user.cursus_users[1].level.toInt()) * 100).toInt().toString()
        percentFloat = percent.toFloat() / 100
    }

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .clip(RoundedCornerShape(15.dp))
                .padding(start = 60.dp),
            color = Color.Black.copy(alpha = 0.6f),
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                Surface(
                    color = Colors.green_primary,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(percentFloat)
                ) {
                }
                Text(
                    color = Color.White,
                    text = if (level != null) "level $level - $percent%" else "",
                    fontSize = 15.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        ProfilePicture(
            url = user?.image?.versions?.small,
            size = 70.dp
        )
    }
}

@Composable
fun Header(
    navhost: NavHostController,
    user: UserDataModel?,
    coalition: Array<CoalitionModel>?
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        if (coalition != null && coalition.size >= 1 && coalition[1].image_url.isNotEmpty()) {
            AsyncImage(
                model = coalition[1].image_url,
                contentDescription = "Coalition image",
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth()
            )
        }
        else {
            Image(
                painter = painterResource(id = R.drawable.alliance_background),
                contentDescription = "Colation image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(250.dp)
            )
        }

        IconButton(
            onClick = {
                navhost.navigate("searchScreen")
            }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back arrow",
                tint = Color.White
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(30.dp)
        )
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = user?.first_name + " " + user?.last_name,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 20.dp)
                )
            }
            Surface(
                color = Color.Black.copy(alpha = 0.6f),
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                )
                {
                    Column(
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Text(text = "Wallet", color = Colors.green_primary)
                        Text(text = user?.wallet.toString(), color = Color.White)
                    }
                    Column(
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Text(text = "Evaluation Points", color = Colors.green_primary)
                        Text(text = user?.correction_point.toString(), color = Color.White)
                    }
                    Column(
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Text(text = "Cursus", color = Colors.green_primary)
                        Text(
                            text = if (user != null && user.cursus_users.size > 1) user.cursus_users[1].cursus.name else "",
                            color = Color.White
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Text(text = "Grade", color = Colors.green_primary)
                        Text(
                            text = if (user != null && user.cursus_users.size > 1 && user.cursus_users[1].grade != null) user.cursus_users[1].grade else "",
                            color = Color.White
                        )
                    }
                }
            }
            UserLevelBar(user = user)
        }
    }
}
