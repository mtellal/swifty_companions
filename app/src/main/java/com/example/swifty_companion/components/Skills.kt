package com.example.swifty_companion.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
        Text(text = level.toString())
    }
}

@Composable
fun Skills(user: UserDataModel?) {
    var expand by remember { mutableStateOf(false) }
    var skills: List<Skill>? by remember {
        mutableStateOf<List<Skill>?>(null)
    }
    if (user != null && user.cursus_users.size > 1 && user.cursus_users[1].skills.isNotEmpty()) {
        if (!expand) {
            skills = user.cursus_users[1].skills.slice(IntRange(0, 5))
        } else {
            skills = user.cursus_users[1].skills.toList()
        }
    }
    Column {
        Text(text = "Skills", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(10.dp))
        Surface(
            modifier = Modifier
                .shadow(elevation = 4.dp, RoundedCornerShape(10.dp))
                .clickable { expand = !expand }
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                if (skills != null && skills!!.isNotEmpty()) {
                    skills!!.forEach {
                        Skill(it.name, it.level)
                    }
                } else {
                    println("projects data -> $skills")
                    Text(text = "No projects found")
                }
            }
        }
    }
}
