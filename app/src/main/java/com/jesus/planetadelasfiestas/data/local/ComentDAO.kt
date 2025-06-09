package com.jesus.planetadelasfiestas.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CommentDao {
    @Query("SELECT * FROM comments WHERE albumId = :albumId ORDER BY timestamp DESC")
    suspend fun getCommentsByAlbumId(albumId: Long): List<CommentEntity>

    @Insert
    suspend fun insert(comment: CommentEntity)

    @Delete
    suspend fun delete(comment: CommentEntity)
}