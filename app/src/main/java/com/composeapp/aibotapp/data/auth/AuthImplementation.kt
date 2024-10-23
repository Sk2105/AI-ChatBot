package com.composeapp.aibotapp.data.auth

import com.composeapp.aibotapp.domain.auth.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthImplementation @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override suspend fun logInWithGoogle(
        task: Task<GoogleSignInAccount>,
        onSuccess: () -> Unit,
        onError: (e: Exception) -> Unit
    ) {
        val account: GoogleSignInAccount = task.getResult(ApiException::class.java)!!
        if(account.idToken == null){
            onError(Exception("Id token is null"))
            return
        }
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        val authResult = auth.signInWithCredential(credential).await()

        if (authResult != null) {
            val user = authResult.user
            println("User: $user")
            onSuccess()
        } else {
            onError(Exception("Authentication failed."))
        }

    }

}