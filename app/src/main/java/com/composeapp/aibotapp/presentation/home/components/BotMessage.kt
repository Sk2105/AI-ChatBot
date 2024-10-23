package com.composeapp.aibotapp.presentation.home.components

import android.content.Context
import android.content.Intent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.composeapp.aibotapp.R
import com.composeapp.aibotapp.presentation.common.MarkdownViewer


@Composable
fun BotMessage(content: String, modifier: Modifier) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .clip(
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = 1.dp,
                    bottomEnd = 16.dp
                )
            )
            .background(
                color = MaterialTheme.colorScheme.onSurface.copy(0.1f)
            )

    ) {

        Text(
            text = "Bot",
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.sp),
            color = MaterialTheme.colorScheme.onSurface.copy(0.6f),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        MarkdownViewer(
            content = content,
            modifier = modifier
                .animateContentSize()
        )
        Row {

            IconButton(onClick = {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, content)
                context.startActivity(intent)

            }, modifier = Modifier.padding(start = 8.dp)) {
                // share icon
                Icon(
                    imageVector = Icons.Rounded.Share,
                    contentDescription = "Share",
                    tint = MaterialTheme.colorScheme.onSurface.copy(0.6f)
                )

            }
            IconButton(onClick = {
                val clipboard =
                    context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                clipboard.text = AnnotatedString(content)

            }, modifier = Modifier.padding(start = 8.dp)) {
                // copy icon
                Icon(
                    painter = painterResource(id = R.drawable.content_copy_24),
                    contentDescription = "Copy",
                    tint = MaterialTheme.colorScheme.onSurface.copy(0.6f)
                )


            }
        }
    }

}

@Composable
@Preview
fun BotMessagePreview() {
    BotMessage("Hello, I am a bot. How can I help you?", Modifier)
}