package com.composeapp.aibotapp.presentation.home.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun UserMessage(msg: String, modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
            .padding(start = 40.dp, end = 8.dp)
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        Column(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 1.dp
                    )
                )
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer
                )
                .padding(16.dp)
        ) {
            Text(
                text = "You",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp),
                color = MaterialTheme.colorScheme.onSurface.copy(0.6f),
                modifier = Modifier
                    .align(Alignment.End)
            )
            Text(
                text = msg,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .align(Alignment.End)

            )

        }


    }
}