package com.jesus.planetadelasfiestas.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val albumId: Long,
    val text: String,
    val timestamp: Long
)