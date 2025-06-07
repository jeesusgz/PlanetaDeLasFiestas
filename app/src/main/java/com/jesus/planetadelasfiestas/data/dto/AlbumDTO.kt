package com.jesus.planetadelasfiestas.data.dto

import com.google.gson.annotations.SerializedName
import com.jesus.planetadelasfiestas.model.Album

data class AlbumDto(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("cover_medium") val coverUrl: String,
    @SerializedName("artist") val artist: ArtistDto
)

fun AlbumDto.toAlbum() = Album(
    id = id,
    title = title,
    coverUrl = coverUrl,
    link = "",
    genreId = 0,
    releaseDate = "",
    recordType = "",
    tracklistUrl = "",
    explicitLyrics = false,
    numberOfTracks = 0,
    duration = 0,
    artistId = artist.id,
    artistName = artist.name,
    artistPicture = artist.picture
)