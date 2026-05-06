package com.example.chatapp.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Centralized dimensions theme
object ChatDimensions {
    // Bubble styling
    val bubbleCornerRadiusLarge: Dp = 18.dp
    val bubbleCornerRadiusTail: Dp = 4.dp
    val bubbleMaxWidth: Dp = 280.dp
    val typingBubbleMaxWidth: Dp = 180.dp

    // Spacing between messages
    val messageSpacingNormal: Dp = 8.dp
    val messageSpacingSmall: Dp = 2.dp

    // Padding
    val paddingListHorizontal: Dp = 12.dp
    val paddingInputBarHorizontal: Dp = 12.dp
    val paddingInputBarVertical: Dp = 8.dp
    val paddingTextBubbleHorizontal: Dp = 14.dp
    val paddingTextBubbleVertical: Dp = 10.dp
    val paddingAvatarEnd: Dp = 6.dp
    val paddingSpacerWidth: Dp = 10.dp
    val paddingSectionHeaderVertical: Dp = 12.dp
    val paddingSectionHeaderHorizontal: Dp = 10.dp

    // Component sizes
    val avatarSizeTopbar: Dp = 36.dp
    val avatarSizeMessage: Dp = 28.dp
    val buttonSendSize: Dp = 48.dp
    val iconSendSize: Dp = 22.dp
    val textFieldCornerRadius: Dp = 24.dp

    // Text sizes
    val textSizeSectionHeader: TextUnit = 11.sp
    val textSizeMessage: TextUnit = 15.sp
    val textSizeTyping: TextUnit = 14.sp
    val textSizeTopbarTitle: TextUnit = 18.sp

    // Line heights
    val lineHeightMessage: TextUnit = 20.sp

    // Spacer heights
    val spacerDividerHeight: Dp = 1.dp
    val spacerWidth: Dp = 8.dp
}
