package com.example.chatapp.presentation.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatapp.R
import com.example.chatapp.ui.theme.ChatDimensions

@Composable
internal fun MessageInputBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSend: () -> Unit,
    modifier: Modifier = Modifier
) {
    val inputContainerColor = MaterialTheme.colorScheme.surfaceVariant
    val inputTextColor = MaterialTheme.colorScheme.onSurface
    val inputPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                horizontal = ChatDimensions.paddingInputBarHorizontal,
                vertical = ChatDimensions.paddingInputBarVertical
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = text,
            onValueChange = onTextChange,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.text_input_placeholder),
                    color = inputPlaceholderColor
                )
            },
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(ChatDimensions.textFieldCornerRadius)),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = inputContainerColor,
                unfocusedContainerColor = inputContainerColor,
                focusedTextColor = inputTextColor,
                unfocusedTextColor = inputTextColor,
                disabledTextColor = inputTextColor.copy(alpha = 0.6f),
                cursorColor = inputTextColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(onSend = { onSend() }),
            singleLine = false,
            maxLines = 4
        )

        Spacer(Modifier.width(ChatDimensions.spacerWidth))

        IconButton(
            onClick = onSend,
            modifier = Modifier
                .size(ChatDimensions.buttonSendSize)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = stringResource(R.string.btn_send),
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(ChatDimensions.iconSendSize)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MessageInputBarPreview() {
    Column {
        MessageInputBar(text = "", onTextChange = {}, onSend = {})
        MessageInputBar(text = "Hey, looks great!", onTextChange = {}, onSend = {})
    }
}
