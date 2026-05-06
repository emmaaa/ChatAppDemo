package com.example.chatapp.presentation.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.chatapp.R
import com.example.chatapp.domain.model.UserProfile

@Composable
internal fun UserAvatar(
    profile: UserProfile,
    size: Dp,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.tertiary),
        contentAlignment = Alignment.Center
    ) {
        if (profile.avatarRes != null) {
            Image(
                painter = painterResource(id = profile.avatarRes),
                contentDescription = profile.name,
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            Text(
                text = profile.name.first().uppercaseChar().toString(),
                color = MaterialTheme.colorScheme.onTertiary,
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Preview
@Composable
private fun UserAvatarPreview() {
    UserAvatar(
        profile = UserProfile(name = "Sarah", avatarRes = R.drawable.avatar),
        size = 36.dp
    )
}
