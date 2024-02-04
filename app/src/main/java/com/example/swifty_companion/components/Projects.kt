package com.example.swifty_companion.components

import android.widget.ScrollView
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swifty_companion.R
import com.example.swifty_companion.models.ProjectData
import com.example.swifty_companion.models.UserDataModel
import com.example.swifty_companion.utils.Colors

@Composable
fun Project(
    project: ProjectData
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(text = project.project.name, fontSize = 15.sp)
        Row {
            if (project.status == "in_progress") {
                Icon(
                    painter = painterResource(id = R.drawable.time),
                    contentDescription = "check mark",
                    tint = Colors.orange,
                    modifier = Modifier
                        .height(25.dp)
                        .width(25.dp)
                )
            }
            else if (project.status == "finished" && project.validated != null && project.validated!!) {
                Text(text = project.final_mark.toString(), color = Colors.green_primary)
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "check mark",
                    tint = Colors.green_primary
                )
            } else {
                if (project.final_mark != null)
                Text(text = project.final_mark.toString() , color = Color.Red)
                else
                    Text(text = "0", color = Color.Red)
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "check mark",
                    tint = Color.Red
                )
            }
        }
    }
}


@Composable
fun Projects(user: UserDataModel?) {
    var projects: List<ProjectData>? by remember {
        mutableStateOf<List<ProjectData>?>(null)
    }

    if (user != null && user.projects_users != null && user.projects_users!!.isNotEmpty()) {
            projects = user.projects_users!!.slice(IntRange(0, user.projects_users!!.size - 1))
    }

    Column {
        Text(text = "Projects", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(10.dp))
        Surface(
            color = Color.White,
            modifier = Modifier
                .shadow(elevation = 4.dp, RoundedCornerShape(10.dp))
        ) {
            if (projects != null && projects!!.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .height(minOf(projects!!.size * 50, 250).toInt().dp)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    projects!!.forEach {
                        Project(it)
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .height(50.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = "No projects found")
                }
            }
        }
    }
}
