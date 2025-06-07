package com.jesus.planetadelasfiestas.repository

import com.jesus.planetadelasfiestas.data.dto.toAlbum
import com.jesus.planetadelasfiestas.model.Album
import com.jesus.planetadelasfiestas.network.DeezerApiService

class AlbumRepository(private val apiService: DeezerApiService) {

    suspend fun getAlbumsByQuery(query: String): List<Album> {
        return apiService.searchAlbums(query).data.map { it.toAlbum() }
    }
}