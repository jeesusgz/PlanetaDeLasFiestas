package com.jesus.planetadelasfiestas.model

import com.jesus.planetadelasfiestas.data.local.AlbumEntity

data class Album(
    val id: Long,
    val title: String,
    val coverUrl: String,
    val link: String,
    val genreId: Int,
    val releaseDate: String,
    val recordType: String,
    val tracklistUrl: String,
    val explicitLyrics: Boolean,
    val numberOfTracks: Int,
    val duration: Int,
    val artistId: Long,
    val artistName: String,
    val artistPicture: String,
    val fans: Int
)

fun Album.toEntity() = AlbumEntity(
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
    artistId = artistId,
    artistName = artistName,
    artistPicture = artistPicture,
    fans = fans
)


