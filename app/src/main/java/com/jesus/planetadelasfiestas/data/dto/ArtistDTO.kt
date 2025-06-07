package com.jesus.planetadelasfiestas.data.dto

import com.google.gson.annotations.SerializedName

data class ArtistDto(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("picture_medium") val picture: String
)