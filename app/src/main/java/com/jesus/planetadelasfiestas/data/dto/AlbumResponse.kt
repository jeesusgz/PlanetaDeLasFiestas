package com.jesus.planetadelasfiestas.data.dto

import com.google.gson.annotations.SerializedName
import com.jesus.planetadelasfiestas.model.Album

data class AlbumResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("cover_medium") val coverUrl: String,
    @SerializedName("link") val link: String,
    @SerializedName("genre_id") val genreId: Int,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("record_type") val recordType: String,
    @SerializedName("tracklist") val tracklistUrl: String,
    @SerializedName("explicit_lyrics") val explicitLyrics: Boolean,
    @SerializedName("nb_tracks") val numberOfTracks: Int,
    @SerializedName("duration") val duration: Int,
    @SerializedName("artist") val artist: ArtistDto
)

fun AlbumResponse.toAlbum() = com.jesus.planetadelasfiestas.model.Album(
    id = id,
    title = title,
    coverUrl = coverUrl,
    link = link,
    genreId = genreId,
    releaseDate = releaseDate,
    recordType = recordType,
    tracklistUrl = tracklistUrl,
    explicitLyrics = explicitLyrics,
    numberOfTracks = numberOfTracks,
    duration = duration,
    artistId = artist.id,
    artistName = artist.name,
    artistPicture = artist.picture
)
