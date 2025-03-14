package com.composeapp.aibotapp.presentation.home.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.composeapp.aibotapp.MainViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(viewModel: MainViewModel, onOpenDrawer: () -> Unit) {

    val state = rememberTopAppBarState()
    TopAppBar(title = {
        Text(
            text = "AI Chatbot", style = MaterialTheme.typography.bodyLarge.copy(
                fontFamily = FontFamily.Monospace,
            )
        )
    },
        modifier = Modifier.statusBarsPadding(),
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
            state = state,
            canScroll = { false }
        ),

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        ), navigationIcon = {
            IconButton(
                onClick = {
                    onOpenDrawer()

                },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                AsyncImage(
                    model = viewModel.currentUserAuth.value?.photoUrl, contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .border(
                            2.dp,
                            Color(0xFF00aa66),
                            RoundedCornerShape(100.dp)
                        )
                )

            }

        }

    )

}