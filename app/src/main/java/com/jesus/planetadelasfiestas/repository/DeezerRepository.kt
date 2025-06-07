package com.jesus.planetadelasfiestas.repository


import com.jesus.planetadelasfiestas.data.dto.toAlbum
import com.jesus.planetadelasfiestas.model.Album
import com.jesus.planetadelasfiestas.network.DeezerApiService
import com.jesus.planetadelasfiestas.network.RetrofitInstance

class DeezerRepository(private val apiService: DeezerApiService) {

    suspend fun searchAlbums(query: String): List<Album> {
        val response = apiService.searchAlbums(query)
        return response.data.map { it.toAlbum() }
    }

    suspend fun getAlbumDetails(albumId: String): Album {
        return apiService.getAlbumDetails(albumId).toAlbum()
    }
}