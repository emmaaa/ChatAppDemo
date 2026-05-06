package com.example.chatapp.presentation.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.chatapp.ui.theme.ChatDimensions

@Composable
internal fun MessageList(
    items: List<ChatListItem>,
    isOtherTyping: Boolean,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = ChatDimensions.paddingListHorizontal),
        verticalArrangement = Arrangement.Bottom
    ) {
        itemsIndexed(
            items = items,
            key = { _, item ->
                when (item) {
                    is ChatListItem.SectionHeader -> item.id
                    is ChatListItem.MessageItem -> "msg_${item.message.id}"
                }
            }
        ) { _, item ->
            when (item) {
                is ChatListItem.SectionHeader -> SectionHeader(item.label)
                is ChatListItem.MessageItem -> MessageBubble(
                    item = item,
                    isCurrentUser = item.message.senderId == ChatViewModel.CURRENT_USER_ID,
                    otherUserName = ChatViewModel.OTHER_USER_NAME
                )
            }
        }

        if (isOtherTyping) {
            item(key = "typing") {
                TypingBubble(userName = ChatViewModel.OTHER_USER_NAME)
            }
        }
    }
}
