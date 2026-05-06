package com.example.chatapp.presentation.chat

import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatapp.R
import com.example.chatapp.ui.theme.AvatarBackground
import com.example.chatapp.ui.theme.ChatDimensions
import com.example.chatapp.ui.theme.ChatPink

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChatTopBar(
    userName: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    TopAppBar(
        modifier = modifier,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                UserAvatar(
                    name = userName,
                    size = ChatDimensions.avatarSizeTopbar,
                    backgroundColor = AvatarBackground,
                    imageResourceId = if (userName == "Sarah") R.drawable.avatar else null
                )
                Spacer(Modifier.width(ChatDimensions.paddingSpacerWidth))
                Text(
                    text = userName,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = ChatDimensions.textSizeTopbarTitle
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = {
                Toast.makeText(
                    context,
                    "This would go back to your message list",
                    Toast.LENGTH_SHORT
                ).show()
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.btn_back),
                    tint = ChatPink
                )
            }
        },
        actions = {
            IconButton(onClick = {
                Toast.makeText(
                    context,
                    "Options like \"block\", \"mute\" and \"search\" would live here",
                    Toast.LENGTH_SHORT
                ).show()
            }) {
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = stringResource(R.string.btn_more_options)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun ChatTopBarPreview() {
    ChatTopBar(userName = "Sarah")
}
