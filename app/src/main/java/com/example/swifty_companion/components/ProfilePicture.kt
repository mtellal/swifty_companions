package com.example.swifty_companion.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.swifty_companion.R

@Composable
fun ProfilePicture(
    url: String?,
    size: Dp? = null
) {
    var width = 50.dp
    var height = 50.dp
    if (size != null) {
        height = size
        width = size
    }

    Surface(
        modifier = Modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(50.dp))
    ) {
        if (url != null) {
            AsyncImage(
                model = url,
                contentDescription = null,
                modifier = Modifier.height(50.dp).fillMaxWidth()
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.defaultuser),
                contentDescription = "default profile picture",
                modifier = Modifier.height(50.dp)
            )
        }
    }
}
