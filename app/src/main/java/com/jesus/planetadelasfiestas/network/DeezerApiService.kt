package com.jesus.planetadelasfiestas.network

import com.jesus.planetadelasfiestas.data.dto.AlbumResponse
import com.jesus.planetadelasfiestas.data.dto.SearchAlbumResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DeezerApiService {
    @GET("search/album")
    suspend fun searchAlbums(@Query("q") query: String): SearchAlbumResponse

    @GET("album/{id}")
    suspend fun getAlbumDetails(@Path("id") albumId: String): AlbumResponse
}