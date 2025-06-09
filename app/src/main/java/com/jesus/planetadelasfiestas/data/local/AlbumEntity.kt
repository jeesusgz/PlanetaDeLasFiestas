package com.jesus.planetadelasfiestas.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jesus.planetadelasfiestas.model.Album

@Entity(tableName = "albums")
data class AlbumEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val artistId: Long,  // Cambiado a Long para que coincida
    val coverUrl: String,
)

fun AlbumEntity.toAlbum() = Album(
    id = id,
    title = title,
    coverUrl = coverUrl,
    link = "", // Aquí pon un valor por defecto o ajusta según tu esquema
    genreId = 0,
    releaseDate = "",
    recordType = "",
    tracklistUrl = "",
    explicitLyrics = false,
    numberOfTracks = 0,
    duration = 0,
    artistId = artistId,
    artistName = "",
    artistPicture = "",
    fans = 0
)