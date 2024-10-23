package com.composeapp.aibotapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.composeapp.aibotapp.data.db.entities.Message
import kotlinx.coroutines.flow.Flow


@Dao
interface MessageDao {

    @Insert
    suspend fun insertMessage(message: Message)


    @Query("SELECT * FROM messages")
    fun getAllMessages(): Flow<List<Message>>

    @Query("DELETE FROM messages")
    suspend fun deleteAllMessages()

    @Query("UPDATE messages SET content = :content WHERE id = :id")
    suspend fun updateMessage(id: Long, content: String)




}