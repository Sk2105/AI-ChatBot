package com.composeapp.aibotapp.domain.auth.usecase

import com.composeapp.aibotapp.domain.auth.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import javax.inject.Inject

class GoogleSignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        task: Task<GoogleSignInAccount>,
        onSuccess: () -> Unit,
        onError: (e: Exception) -> Unit

    ) {
        authRepository.logInWithGoogle(
            task = task,
            onSuccess = onSuccess,
            onError = onError

        )

    }
}