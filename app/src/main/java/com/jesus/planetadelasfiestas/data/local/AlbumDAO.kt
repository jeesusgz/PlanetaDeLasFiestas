package com.jesus.planetadelasfiestas.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(album: AlbumEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(albums: List<AlbumEntity>)

    @Query("SELECT * FROM albums WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): AlbumEntity?

    @Query("SELECT * FROM albums")
    fun getAll(): Flow<List<AlbumEntity>>

    @Delete
    suspend fun delete(album: AlbumEntity)

    @Query("SELECT * FROM albums WHERE id = :albumId LIMIT 1")
    suspend fun getAlbumById(albumId: Long): AlbumEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM albums WHERE id = :albumId)")
    fun isFavorite(albumId: Long): Flow<Boolean>

    @Query("SELECT EXISTS(SELECT 1 FROM albums WHERE id = :id)")
    fun isAlbumFavorite(id: Long): Flow<Boolean>


}
