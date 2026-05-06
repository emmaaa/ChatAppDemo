package com.example.chatapp.presentation.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chatapp.ui.theme.ChatDimensions
import com.example.chatapp.ui.theme.SectionHeaderDivider
import com.example.chatapp.ui.theme.SectionHeaderText

@Composable
internal fun SectionHeader(
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = ChatDimensions.paddingSectionHeaderVertical),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .weight(1f)
                .size(height = ChatDimensions.spacerDividerHeight, width = 0.dp)
                .background(SectionHeaderDivider)
        )
        Text(
            text = label,
            fontSize = ChatDimensions.textSizeSectionHeader,
            color = SectionHeaderText,
            modifier = Modifier.padding(horizontal = ChatDimensions.paddingSectionHeaderHorizontal),
            textAlign = TextAlign.Center
        )
        Spacer(
            modifier = Modifier
                .weight(1f)
                .size(height = ChatDimensions.spacerDividerHeight, width = 0.dp)
                .background(SectionHeaderDivider)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SectionHeaderPreview() {
    SectionHeader(label = "Today 14:30")
}
