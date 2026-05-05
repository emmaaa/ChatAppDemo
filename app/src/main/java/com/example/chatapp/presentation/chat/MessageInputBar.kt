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
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.chatapp.R

@Composable
internal fun MessageInputBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSend: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(
                horizontal = dimensionResource(R.dimen.padding_input_bar_horizontal),
                vertical = dimensionResource(R.dimen.padding_input_bar_vertical)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = text,
            onValueChange = onTextChange,
            placeholder = { Text(stringResource(R.string.text_input_placeholder), color = colorResource(R.color.placeholder_text)) },
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(dimensionResource(R.dimen.text_field_corner_radius))),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorResource(R.color.text_field_background),
                unfocusedContainerColor = colorResource(R.color.text_field_background),
                focusedTextColor = getTextColour(),
                unfocusedTextColor = getTextColour(),
                disabledTextColor = getTextColour().copy(alpha = 0.6f),
                cursorColor = getTextColour(),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
            keyboardActions = KeyboardActions(onSend = { onSend() }),
            singleLine = false,
            maxLines = 4
        )

        Spacer(Modifier.width(dimensionResource(R.dimen.spacer_width)))

        IconButton(
            onClick = onSend,
            modifier = Modifier
                .size(dimensionResource(R.dimen.button_send_size))
                .clip(CircleShape)
                .background(colorResource(R.color.chat_pink))
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = stringResource(R.string.btn_send),
                tint = colorResource(R.color.white),
                modifier = Modifier.size(dimensionResource(R.dimen.icon_send_size))
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

@Composable
private fun getTextColour(): Color {
    return if (isSystemInDarkTheme()) colorResource(R.color.text_color_dark_mode) else colorResource(R.color.text_color_light_mode)
}
