package com.jesus.planetadelasfiestas.repository


import com.jesus.planetadelasfiestas.data.dto.ArtistDto
import com.jesus.planetadelasfiestas.data.dto.toAlbum
import com.jesus.planetadelasfiestas.data.local.AlbumDao
import com.jesus.planetadelasfiestas.data.local.AlbumEntity
import com.jesus.planetadelasfiestas.model.Album
import com.jesus.planetadelasfiestas.model.toEntity
import com.jesus.planetadelasfiestas.network.DeezerApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DeezerRepository(
    private val api: DeezerApiService,
    private val albumDao: AlbumDao
) {
    suspend fun getTopAlbums(): List<Album> {
        return api.getTopAlbums().body()?.data?.map { it.toAlbum() } ?: emptyList()
    }

    suspend fun searchAlbums(query: String): List<Album> {
        return api.searchAlbums(query).data.map { it.toAlbum() }
    }

    suspend fun saveAlbumToDb(album: Album): Boolean {
        val result = albumDao.insert(album.toEntity())
        return result != -1L
    }

    fun getAllSavedAlbums(): Flow<List<AlbumEntity>> = albumDao.getAll()

    suspend fun deleteAlbumFromDb(album: AlbumEntity) {
        albumDao.delete(album)
    }

    fun getFavoriteAlbumsFlow(): Flow<List<AlbumEntity>> = albumDao.getAll()

    fun getFavoriteAlbumIdsFlow(): Flow<Set<Long>> = getFavoriteAlbumsFlow()
        .map { list -> list.map { it.id }.toSet() }

    suspend fun getAlbumDetails(albumId: String): Album? {
        return try {
            val response = api.getAlbumDetails(albumId)
            response.toAlbum()  // Directamente mapear la respuesta a tu modelo Album
        } catch (e: Exception) {
            null
        }
    }

    suspend fun saveAlbumsToDb(albums: List<Album>) {
        val entities = albums.map { it.toEntity() }
        albumDao.insertAll(entities)
    }
}