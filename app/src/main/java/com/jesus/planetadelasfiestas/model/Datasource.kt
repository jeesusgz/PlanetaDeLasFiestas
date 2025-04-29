package com.jesus.planetadelasfiestas.model

import com.jesus.planetadelasfiestas.R

object Datasource {
    val albumList: () -> MutableList<Album> = {
        mutableListOf(
            Album(
                imageName = "un_verano_sin_ti",
                albumName = "Un Verano Sin Ti",
                artistName = "Bad Bunny",
                year = "2022",
                songCount = 23
            ),
            Album(
                imageName = "midnights",
                albumName = "Midnights",
                artistName = "Taylor Swift",
                year = "2022",
                songCount = 13
            ),
            Album(
                imageName = "1989_taylors_version",
                albumName = "1989 (Taylor's Version)",
                artistName = "Taylor Swift",
                year = "2023",
                songCount = 21
            ),
            Album(
                imageName = "scorpion",
                albumName = "Scorpion",
                artistName = "Drake",
                year = "2018",
                songCount = 25
            ),
            Album(
                imageName = "certified_lover_boy",
                albumName = "Certified Lover Boy",
                artistName = "Drake",
                year = "2021",
                songCount = 21
            ),
            Album(
                imageName = "the_tortured_poets_department",
                albumName = "THE TORTURED POETS DEPARTMENT",
                artistName = "Taylor Swift",
                year = "2024",
                songCount = 16
            ),
            Album(
                imageName = "sos",
                albumName = "SOS",
                artistName = "SZA",
                year = "2022",
                songCount = 23
            ),
            Album(
                imageName = "manana_sera_bonito",
                albumName = "MAÑANA SERÁ BONITO",
                artistName = "Karol G",
                year = "2023",
                songCount = 17
            ),
            Album(
                imageName = "starboy",
                albumName = "Starboy",
                artistName = "The Weeknd",
                year = "2016",
                songCount = 18
            ),
            Album(
                imageName = "one_thing_at_a_time",
                albumName = "One Thing At A Time",
                artistName = "Morgan Wallen",
                year = "2023",
                songCount = 36
            )
        ).apply { shuffle() }
    }

    val getAlbumListXTimes: (Int) -> MutableList<Album> = { times ->
        val list = mutableListOf<Album>()
        for (i in 1..times) {
            list.addAll(albumList())
        }
        list.shuffle()
        list
    }

    val getAlbumByName: (String) -> Album? = { name ->
        albumList().find { it.albumName == name }
    }

    val getSomeRandAlbums: (Int) -> MutableList<Album> = { num ->
        val albums = albumList()
        if (num <= albums.size) albums.subList(0, num)
        else albums
    }

    fun getAlbumDrawableIdByName(name: String): Int {
        return when (name) {
            "un_verano_sin_ti" -> R.drawable.un_verano_sin_ti
            "midnights" -> R.drawable.midnights
            "1989_taylors_version" -> R.drawable.taylors_version_1989
            "scorpion" -> R.drawable.scorpion
            "certified_lover_boy" -> R.drawable.certified_lover_boy
            "the_tortured_poets_department" -> R.drawable.the_tortured_poets_departament
            "sos" -> R.drawable.sos
            "manana_sera_bonito" -> R.drawable.manana_sera_bonito
            "starboy" -> R.drawable.starboy
            "one_thing_at_a_time" -> R.drawable.one_thing_at_a_time
            else -> R.drawable.ic_launcher
        }
    }
}