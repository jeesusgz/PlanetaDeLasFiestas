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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.text.insert

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

    fun getFavoriteAlbumsFlow(): Flow<List<AlbumEntity>> = albumDao.getFavoritos()
    fun getFavoriteAlbumIdsFlow(): Flow<Set<Long>> =
        getFavoriteAlbumsFlow().map { list -> list.map { it.id }.toSet() }

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
        val favoritosLocales = albumDao.getAll().first().associateBy { it.id }
        val entities = albums.map { album ->
            val local = favoritosLocales[album.id]
            album.toEntity().copy(esFavorito = local?.esFavorito ?: false)
        }
        albumDao.insertAll(entities)
    }

    suspend fun toggleFavorite(album: Album) {
        val entity = albumDao.getById(album.id)
        if (entity != null) {
            albumDao.insert(entity.copy(esFavorito = !entity.esFavorito))
        } else {
            albumDao.insert(album.toEntity().copy(esFavorito = true))
        }
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

    fun isFavorite(albumId: String): Flow<Boolean> {
        return albumDao.isAlbumFavorite(albumId.toLong())
    }

    suspend fun getAlbumFromRoom(albumId: Long): Album? {
        val entity = albumDao.getAlbumById(albumId)
        return entity?.toAlbum()
    }

    suspend fun getComments(albumId: Long): List<CommentEntity> {
        return commentDao.getCommentsByAlbumId(albumId)
    }

    suspend fun addComment(albumId: Long, text: String, author: String) {
        commentDao.insert(
            CommentEntity(
                albumId = albumId,
                text = text,
                author = author,
                timestamp = System.currentTimeMillis()
            )
        )
    }

    fun getFavoritosFlow(): Flow<List<Album>> {
        return albumDao.getFavoritos()
            .map { entities -> entities.map { it.toAlbum() } }
    }

    suspend fun removeFavorite(album: Album) {
        val entity = albumDao.getById(album.id)
        if (entity != null) {
            albumDao.delete(entity)
        }
    }
}