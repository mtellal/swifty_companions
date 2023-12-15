package com.example.swifty_companion

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

@Composable
fun UserSearchInfo(user: UserInfo, navhost: NavHostController) {
    println("small url image => ${user.image.versions.small}")
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .shadow(elevation = 3.dp, RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(10.dp)
            .clickable {
                navhost.navigate("user/" + user.login)
                println("clicked ")
            }) {
        Box(
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .clip(RoundedCornerShape(50.dp))
        ) {
            if (user.image.versions.small != null) {
                AsyncImage(
                    model = user.image.versions.small,
                    contentDescription = null,
                    modifier = Modifier.height(50.dp)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.defaultuser),
                    contentDescription = "default profile picture",
                    modifier = Modifier.height(50.dp)
                )
            }
        }
        Text(
            text = user.login,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 10.dp)
        )
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp, end = 10.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = user.first_name)
                Text(text = user.last_name)
            }
        }
    }
}