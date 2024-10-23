package com.composeapp.aibotapp.data.onboarding

import androidx.annotation.DrawableRes
import com.composeapp.aibotapp.R


data class OnboardingPage(
    @DrawableRes val image: Int,
    val title: String,
    val description: String
)

val onBoardingList = listOf(

        OnboardingPage(
            title = "Welcome to ChatBot AI",
            description = """
                Welcome to ChatBot AI, an AI-powered chatbot designed to assist users in generating code based on human instructions. 
            """.trimIndent(),
            image = R.drawable.onboarding1,
        ),
        OnboardingPage(
            title = "Features",
            description = """
                This chatbot is a simple AI that can generate code based on human instructions. It is designed to be easy to use and understand. 
            """.trimIndent(),
            image = R.drawable.onboarding2,
        ),
        OnboardingPage(
            title = "Get Started!",
            description = """
                Start using this app by typing in the input field. The AI will generate code according to your instructions. 
            """.trimIndent(),
            image = R.drawable.onboarding3,
        )

)