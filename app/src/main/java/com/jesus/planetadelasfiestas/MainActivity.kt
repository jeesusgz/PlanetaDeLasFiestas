package com.jesus.planetadelasfiestas

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.compose.PlanetaDeLasFiestasTheme
import com.jesus.planetadelasfiestas.model.Album
import com.jesus.planetadelasfiestas.model.Datasource
import com.jesus.planetadelasfiestas.ui.screens.AlbumListCompactScreen
import com.jesus.planetadelasfiestas.ui.screens.AlbumListMedExpScreen
import com.jesus.planetadelasfiestas.ui.screens.DetailItemScreen
import com.jesus.planetadelasfiestas.utils.getWindowSizeClass

class MainActivity : ComponentActivity() {
    @SuppressLint("ContextCastToActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlanetaDeLasFiestasTheme {
                val albums = Datasource.albumList()
                val windowSizeClass = getWindowSizeClass(this)
                var selectedAlbum by remember { mutableStateOf<Album?>(null) }
                var favoriteAlbums by remember { mutableStateOf(mutableSetOf<String>()) }

                val handleDetailsClick: (Album) -> Unit = { album ->
                    selectedAlbum = album
                }

                val handleBackClick: () -> Unit = {
                    selectedAlbum = null
                }

                val handleFavoriteClick: (Album) -> Unit = { album ->
                    if (favoriteAlbums.contains(album.albumName)) {
                        favoriteAlbums.remove(album.albumName)
                    } else {
                        favoriteAlbums.add(album.albumName)
                    }
                }

                when {
                    selectedAlbum != null -> {
                        DetailItemScreen(
                            album = selectedAlbum!!,
                            isFavorite = favoriteAlbums.contains(selectedAlbum!!.albumName),
                            onFavoriteClick = handleFavoriteClick,
                            onBackClick = handleBackClick
                        )
                    }

                    windowSizeClass == WindowWidthSizeClass.Compact -> {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            AlbumListCompactScreen(
                                albums = albums,
                                onFavoriteClick = handleFavoriteClick,
                                favoriteAlbums = favoriteAlbums,
                                onDetailsClick = handleDetailsClick,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }

                    windowSizeClass == WindowWidthSizeClass.Medium || windowSizeClass == WindowWidthSizeClass.Expanded -> {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            AlbumListMedExpScreen(
                                albums = albums,
                                onFavoriteClick = handleFavoriteClick,
                                favoriteAlbums = favoriteAlbums,
                                onDetailsClick = handleDetailsClick,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }
}
