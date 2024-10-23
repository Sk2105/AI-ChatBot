package com.composeapp.aibotapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.composeapp.aibotapp.data.db.dao.MessageDao
import com.composeapp.aibotapp.data.db.entities.Message

@Database(entities = [Message::class], version = 1, exportSchema = false)
abstract class MessageDB : RoomDatabase() {
    abstract fun messageDao(): MessageDao
}