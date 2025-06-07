package com.jesus.planetadelasfiestas.data.dto

import com.google.gson.annotations.SerializedName

data class TopAlbumsResponse(
    @SerializedName("data") val data: List<AlbumDto>
)