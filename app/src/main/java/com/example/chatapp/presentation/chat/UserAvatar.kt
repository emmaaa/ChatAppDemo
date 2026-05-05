package com.example.chatapp.presentation.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.ui.res.colorResource
import com.example.chatapp.R

@Composable
internal fun UserAvatar(
    name: String,
    size: Dp,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    imageResourceId: Int? = null
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(if (imageResourceId != null) Color.Transparent else backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        if (imageResourceId != null) {
            Image(
                painter = painterResource(id = imageResourceId),
                contentDescription = name,
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            Text(
                text = name.firstOrNull()?.uppercaseChar()?.toString() ?: "?",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = (size.value * 0.42f).sp
            )
        }
    }
}

@Preview
@Composable
private fun UserAvatarPreview() {
    UserAvatar(
        name = "Sarah",
        size = 36.dp,
        backgroundColor = colorResource(R.color.avatar_background)
    )
}

