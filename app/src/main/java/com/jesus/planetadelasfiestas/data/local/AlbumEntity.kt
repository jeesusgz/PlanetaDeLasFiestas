package com.jesus.planetadelasfiestas.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "albums")
data class AlbumEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val artistId: Long,  // Cambiado a Long para que coincida
    val coverUrl: String,
)