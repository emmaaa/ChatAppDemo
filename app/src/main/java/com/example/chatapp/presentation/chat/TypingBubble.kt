package com.example.chatapp.presentation.chat

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.chatapp.R
import com.example.chatapp.ui.theme.BubbleReceived

@Composable
internal fun TypingBubble(
    userName: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(R.dimen.message_spacing_normal)),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        UserAvatar(
            name = userName,
            size = dimensionResource(R.dimen.avatar_size_message),
            backgroundColor = Color(0xFFFF8A65),
            modifier = Modifier.padding(end = dimensionResource(R.dimen.padding_avatar_end)),
            imageResourceId = if (userName == "Sarah") R.drawable.avatar else null
        )

        Surface(
            shape = RoundedCornerShape(
                topStart = dimensionResource(R.dimen.bubble_corner_radius_large),
                topEnd = dimensionResource(R.dimen.bubble_corner_radius_large),
                bottomStart = dimensionResource(R.dimen.bubble_corner_radius_tail),
                bottomEnd = dimensionResource(R.dimen.bubble_corner_radius_large)
            ),
            color = BubbleReceived,
            modifier = Modifier.widthIn(max = dimensionResource(R.dimen.typing_bubble_max_width))
        ) {
            Text(
                text = stringResource(R.string.typing_indicator),
                color = Color(0xFF6E6E6E),
                fontSize = dimensionResource(R.dimen.text_size_typing).value.sp,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(R.dimen.padding_text_bubble_horizontal),
                    vertical = dimensionResource(R.dimen.padding_text_bubble_vertical)
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TypingBubblePreview() {
    TypingBubble(userName = "Sarah")
}

