package com.composeapp.aibotapp.presentation.home.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.composeapp.aibotapp.MainViewModel


@Composable
fun HomeBottomBar(viewModel: MainViewModel,onSendMessage: (String) -> Unit = {}) {
    var message by remember {
        mutableStateOf("")
    }

    val focusManager = LocalFocusManager.current
    val focus = remember {
        androidx.compose.ui.focus.FocusRequester()
    }
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth().wrapContentHeight()
            .padding(horizontal = 4.dp, vertical = 0.dp)
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {


        OutlinedTextField(
            value = message,
            onValueChange = {
                message = it
            },
            modifier = Modifier
                .focusRequester(focus)
                .weight(0.7f),
            label = {
                Text(
                    text = "Type a message...",
                    style = MaterialTheme.typography.bodyMedium.copy(fontFamily = FontFamily.Monospace)
                )
            },
            shape = RoundedCornerShape(20.dp), maxLines = 2,

            keyboardActions = KeyboardActions(onSend = {
                if (message.trim().isBlank()) {
                    Toast.makeText(context, "Please enter a message", Toast.LENGTH_SHORT).show()
                    return@KeyboardActions
                }
                onSendMessage(message.trim())
                message = ""
                focusManager.clearFocus()
            })
        )

        IconButton(
            enabled = !viewModel.messageStatus.value,
            onClick = {
                if (message.trim().isBlank()) {
                    Toast.makeText(context, "Please enter a message", Toast.LENGTH_SHORT).show()
                    return@IconButton
                }
                onSendMessage(message.trim())
                message = ""
                focusManager.clearFocus()
            },
            modifier = Modifier.padding(2.dp)
                .clip(RoundedCornerShape(100.dp))
              ,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Icon(
                imageVector = Icons.Rounded.Send,
                contentDescription = "Send",
                modifier = Modifier.padding(8.dp)
                    .align(Alignment.CenterVertically)
            )
        }

    }


}