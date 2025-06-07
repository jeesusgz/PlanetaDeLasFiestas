package com.jesus.planetadelasfiestas.repository


import com.jesus.planetadelasfiestas.data.dto.ArtistDto
import com.jesus.planetadelasfiestas.data.dto.toAlbum
import com.jesus.planetadelasfiestas.model.Album
import com.jesus.planetadelasfiestas.network.DeezerApiService

class DeezerRepository(private val apiService: DeezerApiService) {

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
}