package com.jesus.planetadelasfiestas.data.dto

import com.google.gson.annotations.SerializedName

data class ArtistListResponse(
    @SerializedName("data") val data: List<ArtistDto>
)