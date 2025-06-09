package com.jesus.planetadelasfiestas.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jesus.planetadelasfiestas.model.Album

@Entity(tableName = "albums")
data class AlbumEntity(
    @PrimaryKey val id: Long,
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

fun AlbumEntity.toAlbum() = Album(
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