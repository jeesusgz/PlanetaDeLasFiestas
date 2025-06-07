package com.jesus.planetadelasfiestas.data.local

import kotlinx.coroutines.flow.Flow

class LocalAlbumRepository(private val dao: AlbumDao) {
    suspend fun insert(album: AlbumEntity) = dao.insert(album)
    fun getAll(): Flow<List<AlbumEntity>> = dao.getAll()
    suspend fun isFavorite(id: Long): Boolean = dao.getById(id) != null
}