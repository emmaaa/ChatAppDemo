package com.example.chatapp.presentation.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.chatapp.R
import com.example.chatapp.domain.model.Message
import com.example.chatapp.domain.model.UserProfile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    ChatScreenContent(
        uiState = uiState,
        listState = listState,
        onTextChange = viewModel::onInputChanged,
        onSend = viewModel::sendMessage,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatScreenContent(
    uiState: ChatUiState,
    listState: LazyListState,
    onTextChange: (String) -> Unit,
    onSend: () -> Unit,
    modifier: Modifier = Modifier
) {

    LaunchedEffect(uiState.items.size, uiState.isOtherTyping) {
        if (uiState.items.isNotEmpty() || uiState.isOtherTyping) {
            val targetIndex =
                if (uiState.isOtherTyping) uiState.items.size else uiState.items.size - 1
            listState.animateScrollToItem(targetIndex.coerceAtLeast(0))
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        ChatTopBar(userProfile = uiState.otherUserProfile)

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .imePadding()
        ) {
            MessageList(
                items = uiState.items,
                isOtherTyping = uiState.isOtherTyping,
                otherUserProfile = uiState.otherUserProfile,
                listState = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )

            MessageInputBar(
                text = uiState.inputText,
                onTextChange = onTextChange,
                onSend = onSend
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatScreenPreview() {
    val previewState = ChatUiState(
        items = listOf(
            ChatListItem.SectionHeader(id = "header_1", label = "Today 10:30"),
            ChatListItem.MessageItem(
                message = Message(
                    id = 1,
                    senderId = "sarah",
                    text = "Hey there!",
                    timestamp = 1_000L
                ),
                isSmallSpacingBelow = false
            ),
            ChatListItem.MessageItem(
                message = Message(
                    id = 2,
                    senderId = "me",
                    text = "Hi, how's it going?",
                    timestamp = 2_000L
                ),
                isSmallSpacingBelow = false
            )
        ),
        inputText = "",
        isOtherTyping = false,
        otherUserProfile = UserProfile(name = "Sarah", avatarRes = R.drawable.avatar)
    )

    ChatScreenContent(
        uiState = previewState,
        listState = rememberLazyListState(),
        onTextChange = {},
        onSend = {}
    )
}
