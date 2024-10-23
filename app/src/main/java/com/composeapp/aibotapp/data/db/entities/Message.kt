package com.composeapp.aibotapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "messages")
data class Message(
    @PrimaryKey(autoGenerate = true)
    val id: Long= 0L,
    var content: String,
    val role: Role
)