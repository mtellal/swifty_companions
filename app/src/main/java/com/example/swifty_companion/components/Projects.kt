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
import com.example.swifty_companion.models.UserDataModel
import com.example.swifty_companion.utils.Colors

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
            } else {
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
    var expand by remember { mutableStateOf(false) }
    var projects: List<ProjectData>? by remember {
        mutableStateOf<List<ProjectData>?>(null)
    }
    if (user != null && user.projects_users.isNotEmpty()) {
        if (!expand) {
            projects = user.projects_users.slice(IntRange(0, 5))
        } else {
            projects = user.projects_users.toList()
        }
    }
    Column {
        Text(text = "Projects", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(10.dp))
        Surface(
            modifier = Modifier
                .shadow(elevation = 4.dp, RoundedCornerShape(10.dp))
                .clickable { expand = !expand }
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                if (projects != null && projects!!.isNotEmpty()) {
                    projects!!.forEach {
                        if (it.final_mark != null &&
                            it.cursus_ids.isNotEmpty() &&
                            it.cursus_ids[0] == 21
                        ) {
                            Project(it.project.name, it.final_mark)
                        }
                    }
                } else {
                    println("projects data -> $projects")
                    Text(text = "No projects found")
                }
            }
        }
    }
}
