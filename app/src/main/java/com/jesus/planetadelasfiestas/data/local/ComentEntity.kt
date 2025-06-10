package com.jesus.planetadelasfiestas.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val albumId: Long,
    val text: String,
    val author: String,
    val timestamp: Long = System.currentTimeMillis()
)