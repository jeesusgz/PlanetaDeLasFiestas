package com.jesus.planetadelasfiestas.model

object Routes {
    const val AlbumList = "album_list"
    const val FavList = "fav_list"
    const val Profile = "profile"
    const val albumDetail = "album_detail"
    const val albumDetailRoute = "$albumDetail/{albumName}"
    const val About = "about"

    fun albumDetailRoute(albumName: String): String {
        return "$albumDetail/$albumName"
    }
}