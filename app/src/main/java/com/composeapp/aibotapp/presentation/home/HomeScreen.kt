package com.composeapp.aibotapp.presentation.home

import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.composeapp.aibotapp.MainViewModel
import com.composeapp.aibotapp.R
import com.composeapp.aibotapp.data.db.entities.Role
import com.composeapp.aibotapp.presentation.home.components.BotMessage
import com.composeapp.aibotapp.presentation.home.components.HomeAppBar
import com.composeapp.aibotapp.presentation.home.components.HomeBottomBar
import com.composeapp.aibotapp.presentation.home.components.UserMessage
import com.composeapp.aibotapp.presentation.navigation.routes.AppRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: MainViewModel
) {
    val currentUser by remember {
        mutableStateOf(viewModel.currentUserAuth.value)
    }
    val focusManager = LocalFocusManager.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val verticalScroll = rememberScrollState()
    val scope = rememberCoroutineScope()
    val messages = viewModel.messages.collectAsState()
    var dialogState by remember {
        mutableStateOf(false)
    }

    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        ModalDrawerSheet(
            modifier = Modifier.wrapContentWidth()
        ) {

            Column(
                modifier = Modifier
                    .padding(10.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // user name

                AsyncImage(
                    model = currentUser?.photoUrl,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .size(80.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .background(
                            MaterialTheme.colorScheme.primaryContainer
                        )
                        .border(
                            width = 2.dp,
                            color = Color(0xff00aa66),
                            shape = RoundedCornerShape(100.dp)
                        ),

                    )
                Text(
                    text = currentUser?.displayName ?: "User",
                    modifier = Modifier,
                    style = MaterialTheme.typography.bodyLarge
                )

                // user email
                Text(
                    text = currentUser?.email ?: "User@gmail.com",
                    modifier = Modifier,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(
                            0.7f
                        )
                    )
                )

            }


            HorizontalDivider()
            Text(
                text = "More",
                modifier = Modifier.padding(10.dp),
                style = MaterialTheme.typography.bodySmall
            )

            NavigationDrawerItem(label = {
                Text(
                    text = "About",
                    style = MaterialTheme.typography.bodyMedium
                )
            }, selected = false, onClick = {
                scope.launch {
                    drawerState.close()
                    dialogState = true

                }
            }, icon = {
                Icon(imageVector = Icons.Rounded.Info, contentDescription = "Help")
            })


            /* NavigationDrawerItem(label = {
                 Text(
                     text = "Share with friends",
                     style = MaterialTheme.typography.bodyMedium
                 )
             }, selected = false, onClick = {
                 scope.launch {
                     drawerState.close()
                     // navigator.navigate("share")
                 }
             }, icon = {
                 Icon(imageVector = Icons.Rounded.Share, contentDescription = "Share")
             })*/


            /*NavigationDrawerItem(
                label = {
                    Text(
                        text = "Rate",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }, selected = false, onClick = {
                    scope.launch {
                        drawerState.close()
                        // navigator.navigate("rate")
                    }
                }, icon = {
                    Icon(imageVector = Icons.Rounded.Star, contentDescription = "Rate")
                }
            )*/

            NavigationDrawerItem(label = {
                Text(
                    text = "Logout",
                    style = MaterialTheme.typography.bodyMedium
                )
            }, selected = false, onClick = {
                scope.launch {
                    viewModel.logOut()
                    drawerState.close()
                }
                Toast.makeText(
                    navHostController.context,
                    "Logged out successfully",
                    Toast.LENGTH_SHORT
                ).show()
                navHostController.popBackStack()
                navHostController.navigate(AppRoute.Splash)
            }, icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ExitToApp,
                    contentDescription = "Logout"
                )
            })

        }
    }) {
        Scaffold(
            topBar = {
                HomeAppBar(viewModel) {
                    scope.launch {
                        drawerState.open()
                    }
                }
            },
            bottomBar = {
                HomeBottomBar(viewModel) {
                    viewModel.sendMessage(it)
                }
            },
            floatingActionButton = {
                if (messages.value.isNotEmpty() && verticalScroll.value < verticalScroll.maxValue) {
                    FloatingActionButton(
                        onClick = {
                            scope.launch(Dispatchers.IO) {
                                verticalScroll.animateScrollTo(
                                    verticalScroll.maxValue, animationSpec =
                                    tween(500)
                                )
                            }
                        },
                        containerColor = MaterialTheme.colorScheme.onSurface,
                        shape = RoundedCornerShape(100),
                        modifier = Modifier,
                        contentColor = MaterialTheme.colorScheme.background
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = "Menu"
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxSize()
        ) { it ->

            println(messages.value)

            FlowRow(
                maxItemsInEachRow = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(it)
                    .then(
                        if (messages.value.isNotEmpty()) Modifier.verticalScroll(verticalScroll) else Modifier
                    ),
                verticalArrangement = Arrangement.Bottom
            )
            {
                if (messages.value.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = R.drawable.app_logo),
                            contentDescription = "",
                            modifier = Modifier
                                .size(300.dp)
                                .clip(
                                    RoundedCornerShape(30.dp)
                                ),
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                        )

                        OutlinedButton(
                            onClick = {
                                focusManager.moveFocus(FocusDirection.Down)
                            },
                            shape = MaterialTheme.shapes.large,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Start a new chat",
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }


                } else {

                    messages.value.forEach { message ->
                        when (message.role) {
                            Role.USER -> UserMessage(msg = message.content, modifier = Modifier)

                            Role.BOT -> BotMessage(
                                content = message.content, modifier = Modifier
                                    .padding(8.dp)
                            )
                        }
                        scope.launch(Dispatchers.IO) {
                            if (messages.value.last() == message) {
                                verticalScroll.animateScrollTo(
                                    Int.MAX_VALUE
                                )
                            }
                        }
                    }
                }

            }

            if (dialogState) {
                HelpDialog {
                    dialogState = false
                }
            }

        }


    }


}


@Composable
fun HelpDialog(onClick: () -> Unit) {
    AlertDialog(onDismissRequest = { }, confirmButton = {
        Button(
            onClick = { onClick() },
            modifier = Modifier
        ) {
            Text(text = "Ok")
        }
    },
        title = {
            Text(text = "ℹ️ About", style = MaterialTheme.typography.titleLarge)
        }, text = {
            Text(
                text = """
                This is a simple chat bot app that uses the OpenAI API to generate responses to user messages.
                The app is written in Kotlin and uses the Jetpack Compose UI toolkit.
                The app is open source and available on GitHub.
            """.trimIndent(),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start
            )
        })
}