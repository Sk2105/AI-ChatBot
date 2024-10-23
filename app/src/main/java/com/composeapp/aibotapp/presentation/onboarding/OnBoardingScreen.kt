package com.composeapp.aibotapp.presentation.onboarding

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.composeapp.aibotapp.MainViewModel
import com.composeapp.aibotapp.data.onboarding.onBoardingList
import com.composeapp.aibotapp.presentation.navigation.routes.AppRoute
import com.composeapp.aibotapp.presentation.onboarding.components.OnBoardingPage
import kotlinx.coroutines.launch


@Composable
fun OnBoardingScreen(viewModel: MainViewModel,navigator: NavHostController) {
    val pagerState = rememberPagerState(
        pageCount = { 3 },
        initialPage = 0
    )

    val buttons = listOf(
        listOf(
            "",
            "Next"
        ),
        listOf(
            "Back",
            "Next"
        ),
        listOf(
            "Back",
            "Get Started"
        )
    )

    OnBoardingPager(
        pagerState = pagerState,
        buttons = buttons
    ) {

        navigator.popBackStack()
        navigator.navigate(AppRoute.Auth)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingPager(pagerState: PagerState, buttons: List<List<String>>, onClick: () -> Unit = {}) {

    val scope = rememberCoroutineScope()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp),
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ClickableText(text = AnnotatedString(buttons[pagerState.currentPage][0]),
                    modifier = Modifier
                        .padding(8.dp)
                        .animateContentSize(),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.primary,
                    ),
                    onHover = {}) {
                    if (pagerState.currentPage > 0) {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }

                }
                Button(
                    onClick = {
                        if (pagerState.currentPage < 2) {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        } else {
                            onClick()
                        }

                    },
                    modifier = Modifier
                        .padding(4.dp)
                        .animateContentSize(),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = buttons[pagerState.currentPage][1],
                        modifier = Modifier
                            .padding(4.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onPrimary,
                        )
                    )
                }
            }

        }
    ) {
        HorizontalPager(
            state = pagerState, modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) { idx ->
            OnBoardingPage(modifier = Modifier.padding(16.dp), onboardingPage = onBoardingList[idx])
        }
    }

}