package com.jesus.planetadelasfiestas.repository


import com.jesus.planetadelasfiestas.data.dto.ArtistDto
import com.jesus.planetadelasfiestas.data.dto.toAlbum
import com.jesus.planetadelasfiestas.data.local.AlbumDao
import com.jesus.planetadelasfiestas.data.local.AlbumEntity
import com.jesus.planetadelasfiestas.model.Album
import com.jesus.planetadelasfiestas.network.DeezerApiService

class DeezerRepository(
    private val apiService: DeezerApiService,
    private val albumDao: AlbumDao  // <-- agregamos el DAO aquí
) {

    suspend fun searchAlbums(query: String): List<Album> {
        val response = apiService.searchAlbums(query)
        return response.data.map { it.toAlbum() }
    }

    suspend fun getAlbumDetails(albumId: String): Album {
        return apiService.getAlbumDetails(albumId).toAlbum()
    }

    suspend fun getArtistsByGenre(genreId: Int): List<ArtistDto> {
        val response = apiService.getArtistsByGenre(genreId)
        if (response.isSuccessful) {
            return response.body()?.data ?: emptyList()
        } else {
            throw Exception("Error obteniendo artistas por género")
        }
    }

    suspend fun getTopAlbums(): List<Album> {
        val response = apiService.getTopAlbums()
        if (response.isSuccessful) {
            return response.body()?.data?.map { it.toAlbum() } ?: emptyList()
        } else {
            throw Exception("Error al obtener top albums")
        }
    }

    suspend fun saveAlbumToDb(album: Album): Boolean {
        val albumEntity = AlbumEntity(
            id = album.id,
            title = album.title,
            artistId = album.artistId,  // asignar artistId (Long)
            coverUrl = album.coverUrl
        )
        val insertResult = albumDao.insertAlbum(albumEntity)
        return insertResult != -1L
    }
}