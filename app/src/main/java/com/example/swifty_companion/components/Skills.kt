package com.example.swifty_companion.components

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swifty_companion.models.ProjectData
import com.example.swifty_companion.models.Skill
import com.example.swifty_companion.models.UserDataModel
import com.example.swifty_companion.utils.Colors

@Composable
fun Skill(
    name: String,
    level: Float
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(text = name, fontSize = 15.sp)
        Row (
            horizontalArrangement = Arrangement.Center
        )
        {
            Text(text = level.toString())
            Spacer(modifier = Modifier.width(20.dp))
            Text(text = ((level / 20) * 100).toInt().toString() + "%")
        }
    }
}

@Composable
fun Skills(user: UserDataModel?) {
    var skills: List<Skill>? = listOf();

    if (user != null && user.cursus_users != null && user.cursus_users!!.size > 1 && user.cursus_users!![1].skills.isNotEmpty()) {
            skills = user.cursus_users!![1].skills.slice(IntRange(0, user.cursus_users!![1].skills.size - 1))
    }

    Column {
        Text(text = "Skills", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(10.dp))
        Surface(
            color = Color.White,
            modifier = Modifier
                .shadow(elevation = 4.dp, RoundedCornerShape(10.dp))
        ) {
            if (skills != null && skills!!.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .height(minOf(skills!!.size * 50, 250).toInt().dp)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    skills!!.forEach {
                        Skill(it.name, it.level)
                    }
                }
            }
            else {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .height(50.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = "No skills found")
                }
            }
        }
    }
}
