package com.jesus.planetadelasfiestas.model

import android.net.Uri

object Routes {
    const val AlbumList = "album_list"
    const val FavList = "fav_list"
    const val Profile = "profile"
    const val albumDetail = "album_detail"
    const val albumDetailRoute = "$albumDetail/{albumName}"
    const val About = "about"

    fun albumDetailRoute(albumName: String): String {
        val encodedName = Uri.encode(albumName)  // Codifica caracteres especiales
        return "detail/$encodedName"
    }
}