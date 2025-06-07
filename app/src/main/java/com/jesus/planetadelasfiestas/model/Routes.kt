package com.jesus.planetadelasfiestas.model

import android.net.Uri

object Routes {
    const val AlbumList = "album_list"
    const val FavList = "fav_list"
    const val Profile = "profile"
    const val albumDetail = "detail"
    const val albumDetailRoute = "$albumDetail/{albumId}"
    const val About = "about"

    fun albumDetailRoute(albumId: Long): String {
        return "$albumDetail/$albumId"
    }
}