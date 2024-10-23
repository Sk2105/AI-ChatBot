package com.composeapp.aibotapp.domain.db.repository

import com.composeapp.aibotapp.data.db.dao.MessageDao
import com.composeapp.aibotapp.data.db.entities.Message
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class MessageRepository @Inject constructor(private val messageDao: MessageDao) {
    suspend fun insertMessage(message: Message) {
        messageDao.insertMessage(message)
        delay(100)
    }
    fun getAllMessages(): Flow<List<Message>> {
        return messageDao.getAllMessages()
    }

    suspend fun deleteAllMessages() {
        messageDao.deleteAllMessages()
    }

    suspend fun updateMessage(message: Message) {
        messageDao.updateMessage(message.id, message.content)
    }

}