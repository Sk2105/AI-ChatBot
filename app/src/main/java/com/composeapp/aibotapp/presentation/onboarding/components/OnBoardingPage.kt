package com.composeapp.aibotapp.presentation.onboarding.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.composeapp.aibotapp.data.onboarding.OnboardingPage
import com.composeapp.aibotapp.data.onboarding.onBoardingList


@Composable
fun OnBoardingPage(
    modifier: Modifier,
    onboardingPage: OnboardingPage = onBoardingList[0],
) {

    val scale = remember { Animatable(50f) }
      LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 300f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = LinearOutSlowInEasing
            )
        )

    }


    Column(
        modifier = modifier
            .fillMaxSize()
            ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            contentDescription = onboardingPage.title,
            painter = painterResource(onboardingPage.image),
            modifier = Modifier
                .size(scale.value.dp)
                .clip(
                    RoundedCornerShape(30.dp)
                )
                .animateContentSize(
                )
        )

        Text(
            text = onboardingPage.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontFamily = FontFamily.Monospace,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary,
                        MaterialTheme.colorScheme.tertiary
                    )
                )
            )
        )

        Text(
            text = onboardingPage.description,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily.Monospace,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface.copy(0.8f)
            )
        )

    }


}