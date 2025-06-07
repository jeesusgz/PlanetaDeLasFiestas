package com.jesus.planetadelasfiestas.network

import com.jesus.planetadelasfiestas.data.dto.AlbumResponse
import com.jesus.planetadelasfiestas.data.dto.ArtistListResponse
import com.jesus.planetadelasfiestas.data.dto.SearchAlbumResponse
import com.jesus.planetadelasfiestas.data.dto.TopAlbumsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DeezerApiService {
    @GET("search/album")
    suspend fun searchAlbums(@Query("q") query: String): SearchAlbumResponse

    @GET("album/{id}")
    suspend fun getAlbumDetails(@Path("id") albumId: String): AlbumResponse

    @GET("genre/{genre_id}/artists")
    suspend fun getArtistsByGenre(@Path("genre_id") genreId: Int): Response<ArtistListResponse>

    @GET("chart/0/albums")
    suspend fun getTopAlbums(): Response<TopAlbumsResponse>
}