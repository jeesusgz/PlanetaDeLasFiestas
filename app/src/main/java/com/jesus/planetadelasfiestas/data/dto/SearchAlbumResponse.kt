package com.jesus.planetadelasfiestas.data.dto

import com.google.gson.annotations.SerializedName

data class SearchAlbumResponse(
    @SerializedName("data") val data: List<AlbumDto>
)