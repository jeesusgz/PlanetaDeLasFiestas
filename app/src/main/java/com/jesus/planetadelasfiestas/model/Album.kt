package com.jesus.planetadelasfiestas.model

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