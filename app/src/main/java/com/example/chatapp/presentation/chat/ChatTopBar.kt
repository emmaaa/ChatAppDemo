package com.example.chatapp.presentation.chat

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.chatapp.R
import com.example.chatapp.ui.theme.ChatPink

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChatTopBar(
    userName: String,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                UserAvatar(
                    name = userName,
                    size = dimensionResource(R.dimen.avatar_size_topbar),
                    // TODO: Move color
                    backgroundColor = Color(0xFFFF8A65),
                    imageResourceId = if (userName == "Sarah") R.drawable.avatar else null
                )
                Spacer(Modifier.width(dimensionResource(R.dimen.padding_spacer_width)))
                Text(
                    text = userName,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = dimensionResource(R.dimen.text_size_topbar_title).value.sp
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.btn_back),
                    tint = ChatPink
                )
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(Icons.Default.MoreVert, contentDescription = stringResource(R.string.btn_more_options))
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



