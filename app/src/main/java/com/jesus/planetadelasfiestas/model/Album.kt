package com.jesus.planetadelasfiestas.model

data class Album(
    val imageName: String,
    val albumName: String,
    val artistName: String,
    val year: String,
    val songCount: Int,
    val description: String,
    val comments: List<String> = emptyList()
)