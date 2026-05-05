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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatapp.R

@Composable
internal fun SectionHeader(
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.padding_section_header_vertical)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .weight(1f)
                .size(height = dimensionResource(R.dimen.spacer_divider_height), width = 0.dp)
                .background(Color(0xFFD0D0D0))
        )
        Text(
            text = label,
            fontSize = dimensionResource(R.dimen.text_size_section_header).value.sp,
            color = Color(0xFF9E9E9E),
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_section_header_horizontal)),
            textAlign = TextAlign.Center
        )
        Spacer(
            modifier = Modifier
                .weight(1f)
                .size(height = dimensionResource(R.dimen.spacer_divider_height), width = 0.dp)
                .background(Color(0xFFD0D0D0))
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SectionHeaderPreview() {
    SectionHeader(label = "Today 14:30")
}

