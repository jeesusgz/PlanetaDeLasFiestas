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

    suspend fun getTopAlbums(): List<Album> {
        val response = apiService.getTopAlbums()  // Esto debe llamar al endpoint "chart/0/albums"
        if (response.isSuccessful) {
            // response.body() debe ser de tipo TopAlbumsResponse que contiene lista de álbumes en .data
            return response.body()?.data?.map { it.toAlbum() } ?: emptyList()
        } else {
            throw Exception("Error al obtener top albums")
        }
    }
}