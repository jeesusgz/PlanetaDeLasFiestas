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
                songCount = 23,
                description = "Este álbum de Bad Bunny es una explosión de ritmos caribeños y reggaetón, capturando la esencia del verano y las emociones intensas que lo acompañan"
            ),
            Album(
                imageName = "midnights",
                albumName = "Midnights",
                artistName = "Taylor Swift",
                year = "2022",
                songCount = 13,
                description = "En 'Midnights', Taylor Swift lleva al oyente a un viaje introspectivo durante la noche. Con letras profundamente personales y una producción pop refinada"

            ),
            Album(
                imageName = "1989_taylors_version",
                albumName = "1989 (Taylor's Version)",
                artistName = "Taylor Swift",
                year = "2023",
                songCount = 21,
                description = "La regrabación del icónico álbum de 1989 marca un nuevo capítulo para Taylor Swift. Con una nueva visión de los éxitos de su era pop"
            ),
            Album(
                imageName = "scorpion",
                albumName = "Scorpion",
                artistName = "Drake",
                year = "2018",
                songCount = 25,
                description = "Doble álbum que mezcla rap y R&B, 'Scorpion' es una obra extensa y personal de Drake. Con 25 canciones, incluye algunos de sus mayores éxitos como 'God's Plan' y 'In My Feelings'"
            ),
            Album(
                imageName = "certified_lover_boy",
                albumName = "Certified Lover Boy",
                artistName = "Drake",
                year = "2021",
                songCount = 21,
                description = "En 'Certified Lover Boy', Drake combina su vulnerabilidad emocional con su típica confianza. Este álbum está lleno de colaboraciones con otros artistas populares y éxitos virales, explorando temas de amor, desamor y relaciones, con un estilo accesible y directo"
            ),
            Album(
                imageName = "the_tortured_poets_department",
                albumName = "THE TORTURED POETS DEPARTMENT",
                artistName = "Taylor Swift",
                year = "2024",
                songCount = 16,
                description = "Un álbum poético y profundo que explora el amor, la pérdida y la identidad. Con una producción sobria y elegante, 'The Tortured Poets Department' es un viaje lírico en el que Taylor Swift refleja su evolución como artista y como persona, con una delicada mezcla de sentimientos y sonidos."
            ),
            Album(
                imageName = "sos",
                albumName = "SOS",
                artistName = "SZA",
                year = "2022",
                songCount = 23,
                description = "En 'SOS', SZA se adentra en un viaje emocional sobre el amor propio y las relaciones. Con influencias de soul, R&B y pop"
            ),
            Album(
                imageName = "manana_sera_bonito",
                albumName = "MAÑANA SERÁ BONITO",
                artistName = "Karol G",
                year = "2023",
                songCount = 17,
                description = "Karol G demuestra en 'Mañana Será Bonito' su capacidad para mezclar reggaetón, pop y baladas de manera optimista y esperanzadora"
            ),
            Album(
                imageName = "starboy",
                albumName = "Starboy",
                artistName = "The Weeknd",
                year = "2016",
                songCount = 18,
                description = "es un álbum oscuro y elegante que combina elementos electrónicos y de R&B. The Weeknd lleva al oyente a través de un paisaje sonoro envolvente y melancólico, con colaboraciones estelares como Daft Punk. "
            ),
            Album(
                imageName = "one_thing_at_a_time",
                albumName = "One Thing At A Time",
                artistName = "Morgan Wallen",
                year = "2023",
                songCount = 36,
                description = "Un álbum extenso que fusiona el country y el pop, 'One Thing At A Time' refleja la experiencia personal de Morgan Wallen. Con 36 canciones, el álbum explora temas de vida, amor y lecciones aprendidas a lo largo de un viaje musical que ha capturado la atención del público en todo el mundo"
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