package com.composeapp.aibotapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.composeapp.aibotapp.MainViewModel
import com.composeapp.aibotapp.presentation.auth.AuthScreen
import com.composeapp.aibotapp.presentation.home.HomeScreen
import com.composeapp.aibotapp.presentation.navigation.routes.AppRoute
import com.composeapp.aibotapp.presentation.onboarding.OnBoardingScreen


@Composable
fun AppNavigation(route: AppRoute = AppRoute.Splash) {
    val navigator = rememberNavController()

    val viewModel: MainViewModel = hiltViewModel()

    NavHost(navController = navigator, startDestination = route) {

        navigation<AppRoute.Splash>(startDestination = AppRoute.OnBoarding) {

            composable<AppRoute.OnBoarding> {
                OnBoardingScreen(navigator)
            }

            composable<AppRoute.Auth> {
                AuthScreen(
                    viewModel = viewModel,
                    navHostController = navigator
                )

            }

            composable<AppRoute.Home> {
                HomeScreen(navHostController = navigator, viewModel = viewModel)

            }

        }



        navigation<AppRoute.Chat>(startDestination = AppRoute.Home) {

            composable<AppRoute.Home> {
                HomeScreen(navHostController = navigator, viewModel = viewModel)
            }

        }


    }
}