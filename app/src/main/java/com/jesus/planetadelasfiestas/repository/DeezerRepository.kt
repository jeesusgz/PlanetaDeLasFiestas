package com.jesus.planetadelasfiestas.repository


import com.jesus.planetadelasfiestas.data.dto.ArtistDto
import com.jesus.planetadelasfiestas.data.dto.toAlbum
import com.jesus.planetadelasfiestas.data.local.AlbumDao
import com.jesus.planetadelasfiestas.data.local.AlbumEntity
import com.jesus.planetadelasfiestas.data.local.CommentDao
import com.jesus.planetadelasfiestas.data.local.CommentEntity
import com.jesus.planetadelasfiestas.data.local.toAlbum
import com.jesus.planetadelasfiestas.model.Album
import com.jesus.planetadelasfiestas.model.toEntity
import com.jesus.planetadelasfiestas.network.DeezerApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeezerRepository @Inject constructor(
    private val api: DeezerApiService,
    private val albumDao: AlbumDao,
    private val commentDao: CommentDao
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

    // Obtener detalles del álbum desde la API Deezer
    suspend fun getAlbumDetails(id: String): Album? {
        val response = api.getAlbumDetails(id)
        return response.toAlbum()
    }

    suspend fun getAlbumById(id: Long): AlbumEntity? {
        return albumDao.getById(id)
    }

    suspend fun getAlbumFromDb(albumId: Long): AlbumEntity? {
        return albumDao.getById(albumId)
    }

    suspend fun saveAlbumsToDb(albums: List<Album>) {
        val entities = albums.map { it.toEntity() }
        albumDao.insertAll(entities)
    }

    suspend fun getAlbumByIdFromDb(id: Long): AlbumEntity? {
        return albumDao.getAlbumById(id)
    }

    suspend fun saveAlbum(album: Album) {
        albumDao.insert(album.toEntity())
    }

    suspend fun deleteAlbum(album: Album) {
        albumDao.delete(album.toEntity())
    }

    // Comprobar si un álbum está guardado en favoritos (Room)
    fun isFavorite(albumId: String): Flow<Boolean> {
        return albumDao.isAlbumFavorite(albumId.toLong())
    }

    // Ya tienes esta función pero con String para albumId, la adaptamos a Long
    suspend fun getAlbumFromRoom(albumId: Long): Album? {
        val entity = albumDao.getAlbumById(albumId)
        return entity?.toAlbum()
    }

    suspend fun getComments(albumId: Long): List<String> {
        return commentDao.getCommentsByAlbumId(albumId).map { it.text }
    }

    suspend fun addComment(albumId: Long, commentText: String) {
        val commentEntity = CommentEntity(
            id = 0, // Auto-generado
            albumId = albumId,
            text = commentText,
            timestamp = System.currentTimeMillis()
        )
        commentDao.insert(commentEntity)
    }

    fun getFavoritosFlow(): Flow<List<Album>> {
        return albumDao.getFavoritos()
            .map { entities -> entities.map { it.toAlbum() } }
    }
}