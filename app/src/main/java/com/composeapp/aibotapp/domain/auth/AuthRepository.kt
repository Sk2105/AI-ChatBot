package com.composeapp.aibotapp.domain.auth

import android.app.Activity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

interface AuthRepository {
    suspend fun  logInWithGoogle(
       task: Task<GoogleSignInAccount>,
       onSuccess: () -> Unit,
       onError: (e:Exception) -> Unit
    )
}