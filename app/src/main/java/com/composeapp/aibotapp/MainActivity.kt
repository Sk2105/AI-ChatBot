package com.composeapp.aibotapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.composeapp.aibotapp.presentation.navigation.AppNavigation
import com.composeapp.aibotapp.presentation.navigation.routes.AppRoute
import com.composeapp.aibotapp.ui.theme.AIBotAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var route: AppRoute = AppRoute.Splash
            val viewModel: MainViewModel = hiltViewModel()
            installSplashScreen()
            if (viewModel.authState.value) {
                route = AppRoute.Chat
            }

            AIBotAppTheme {
                AppNavigation(route = route)
            }
        }
    }
}