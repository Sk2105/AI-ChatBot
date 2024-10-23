package com.composeapp.aibotapp.presentation.auth

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.composeapp.aibotapp.MainViewModel
import com.composeapp.aibotapp.R
import com.composeapp.aibotapp.presentation.navigation.routes.AppRoute
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun AuthScreen(
    viewModel: MainViewModel,
    navHostController: NavHostController
) {

    var authState by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val authResultLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { res ->
            try {
                val task =
                    GoogleSignIn.getSignedInAccountFromIntent(res.data)
                if (task.isSuccessful) {

                    viewModel.logInWithGoogle(task, onSuccess = {
                        authState = false
                        viewModel.currentUserAuth.value = Firebase.auth.currentUser
                        scope.launch(
                            Dispatchers.Main
                        ) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    "Login successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                navHostController.popBackStack()
                                navHostController.navigate(AppRoute.Chat)
                            }
                        }
                    }) {
                        scope.launch(
                            Dispatchers.Main
                        ) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    context,
                                    "Login Failed : ${it.message}",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }

                        }
                        authState = false
                    }
                } else {
                    scope.launch(
                        Dispatchers.Main
                    ) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                task.exception?.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                    authState = false
                }

            } catch (e: Exception) {
                authState = false
                Toast.makeText(
                    context,
                    e.message,
                    Toast.LENGTH_SHORT
                ).show()
                e.printStackTrace()
            }


        }


    val scale = remember { Animatable(100f) }
    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 400f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = LinearOutSlowInEasing
            )
        )
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.auth_image),
                contentDescription = "image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(scale.value.dp)
                    .padding(5.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Welcome to AI ChatBot",
                style = MaterialTheme.typography.headlineLarge.copy(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.tertiary
                        ),
                    ),
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(10.dp),

                )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = """
                    Please login to continue using the app, or create an account if you don't have one yet.
                """.trimIndent(),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.padding(10.dp)
            )


            Spacer(modifier = Modifier.height(10.dp))


            OutlinedButton(
                onClick = {
                    authState = true
                    authResultLauncher.launch(
                        getGoogleSignInClient(context).signInIntent
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp)
                    .clip(
                        RoundedCornerShape(10.dp)
                    )
                    .padding(8.dp),
                border = BorderStroke(
                    width = 1.dp,
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary,
                            MaterialTheme.colorScheme.tertiary
                        ),
                    )
                )

            ) {
                Text(
                    text = "Login with Google",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.secondary,
                                MaterialTheme.colorScheme.tertiary
                            ),
                        ),
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.padding(8.dp)

                )
            }

        }
        if (authState) {
            AlertDialog(title = {

            }, onDismissRequest = { }, confirmButton = { }, text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(
                        modifier = Modifier
                            .height(10.dp)
                    )
                    Text(
                        text = "Please wait while we authenticate you...",

                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontFamily = FontFamily.Monospace,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.padding(10.dp)
                    )
                }
            })
        }

    }


}

fun getGoogleSignInClient(context: Context): GoogleSignInClient {
    val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail().requestProfile().requestIdToken(
            context.getString(R.string.default_web_client_id)
        )
        .build()

    return GoogleSignIn.getClient(context, signInOptions)
}