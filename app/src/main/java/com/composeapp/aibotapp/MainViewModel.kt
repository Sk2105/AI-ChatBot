package com.composeapp.aibotapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composeapp.aibotapp.data.db.entities.Message
import com.composeapp.aibotapp.data.db.entities.Role
import com.composeapp.aibotapp.domain.auth.usecase.GoogleSignInUseCase
import com.composeapp.aibotapp.domain.db.repository.MessageRepository
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content
import com.google.ai.client.generativeai.type.TextPart
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MessageRepository,
    private val auth: FirebaseAuth,
    private val googleSignInUseCase: GoogleSignInUseCase,
    private val geminiModel: GenerativeModel

) : ViewModel() {

    val currentUserAuth = mutableStateOf<FirebaseUser?>(null)

    val authState = mutableStateOf(false)

    val messageStatus = mutableStateOf(false)

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages = _messages.onStart {
        getAllMessages()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    init {
        authState.value = auth.currentUser != null
        if (authState.value) {
            currentUserAuth.value = auth.currentUser
        }
    }

    private fun getAllMessages() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllMessages().collect {
                _messages.value = it
            }
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            messageStatus.value = true
            repository.insertMessage(Message(content = message, role = Role.USER))
            repository.insertMessage(Message(content = "", role = Role.BOT))
            receiveMessage(message)
        }
    }


    private fun receiveMessage(message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val content = _messages.value.map { ms ->
                    Content(
                        parts = listOf(
                            TextPart(ms.content)
                        )
                    )
                }
                val msg = _messages.value.last {
                    it.role == Role.BOT
                }
                val chatResponse = geminiModel.startChat(content).sendMessage(message)
                chatResponse.text?.onEach {
                    msg.content += it.toString()
                    repository.updateMessage(msg)
                    messageStatus.value = false
                }
            } catch (e: Exception) {
                e.printStackTrace()
                val msg = _messages.value.last {
                    it.role == Role.BOT
                }
                msg.content = "Error: ${e.message}"
                repository.updateMessage(msg)
                messageStatus.value = false
            }
        }
    }

    fun logInWithGoogle(
        task: Task<GoogleSignInAccount>,
        onSuccess: () -> Unit,
        onError: (e: Exception) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            googleSignInUseCase(
                task = task,
                onSuccess = onSuccess,
                onError = onError
            )
        }
    }


    fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            auth.signOut()
            repository.deleteAllMessages()
            authState.value = false
            currentUserAuth.value = null
            messageStatus.value = false
        }
    }

}