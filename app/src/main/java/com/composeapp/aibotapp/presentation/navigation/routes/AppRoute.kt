package com.composeapp.aibotapp.presentation.navigation.routes

import kotlinx.serialization.Serializable


sealed interface AppRoute {


    @Serializable
    data object Home : AppRoute

    @Serializable
    data object Auth : AppRoute

    @Serializable
    data object OnBoarding : AppRoute

    @Serializable
    data object Splash : AppRoute

    @Serializable
    data object Chat : AppRoute


}