package com.example.chatapp.presentation.chat

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatapp.R
import com.example.chatapp.domain.model.UserProfile
import com.example.chatapp.ui.theme.ChatDimensions

@Composable
internal fun TypingBubble(
    userProfile: UserProfile,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = ChatDimensions.messageSpacingNormal),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        UserAvatar(
            profile = userProfile,
            size = ChatDimensions.avatarSizeMessage,
            modifier = Modifier.padding(end = ChatDimensions.paddingAvatarEnd)
        )

        Surface(
            shape = RoundedCornerShape(
                topStart = ChatDimensions.bubbleCornerRadiusLarge,
                topEnd = ChatDimensions.bubbleCornerRadiusLarge,
                bottomStart = ChatDimensions.bubbleCornerRadiusTail,
                bottomEnd = ChatDimensions.bubbleCornerRadiusLarge
            ),
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.widthIn(max = ChatDimensions.typingBubbleMaxWidth)
        ) {
            Text(
                text = stringResource(R.string.typing_indicator),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = ChatDimensions.textSizeTyping,
                modifier = Modifier.padding(
                    horizontal = ChatDimensions.paddingTextBubbleHorizontal,
                    vertical = ChatDimensions.paddingTextBubbleVertical
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TypingBubblePreview() {
    TypingBubble(userProfile = UserProfile(name = "Sarah", avatarRes = R.drawable.avatar))
}
