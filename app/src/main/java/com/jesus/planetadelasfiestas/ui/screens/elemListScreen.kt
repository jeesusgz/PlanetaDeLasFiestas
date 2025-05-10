package com.jesus.planetadelasfiestas.ui.screens

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.compose.PlanetaDeLasFiestasTheme
import com.jesus.planetadelasfiestas.model.Album
import com.jesus.planetadelasfiestas.model.Datasource
import com.jesus.planetadelasfiestas.ui.components.AlbumCard
import com.jesus.planetadelasfiestas.ui.components.AlbumCardLand
import com.jesus.planetadelasfiestas.utils.getWindowSizeClass

@SuppressLint("ContextCastToActivity")
@Composable
fun ElemListApp() {
    val albums = Datasource.albumList()
    val windowSize = getWindowSizeClass(LocalContext.current as Activity)
    var favoriteAlbums by remember { mutableStateOf(mutableSetOf<String>()) }
    var selectedAlbum by remember { mutableStateOf<Album?>(null) }


    val handleFavoriteClick: (Album) -> Unit = { album ->
        if (favoriteAlbums.contains(album.albumName)) {
            favoriteAlbums.remove(album.albumName)
        } else {
            favoriteAlbums.add(album.albumName)
        }
    }

    val handleDetailsClick: (Album) -> Unit = { album ->
        selectedAlbum = album
    }

    val handleBackClick: () -> Unit = {
        selectedAlbum = null
    }

    PlanetaDeLasFiestasTheme {
        Scaffold { innerPadding ->
            if (selectedAlbum != null) {
                DetailItemScreen(
                    album = selectedAlbum!!,
                    isFavorite = favoriteAlbums.contains(selectedAlbum!!.albumName),
                    onFavoriteClick = handleFavoriteClick,
                    onBackClick = handleBackClick
                )
            } else {
                when (windowSize) {
                    WindowWidthSizeClass.Compact -> {
                        AlbumListCompactScreen(
                            albums = albums,
                            onFavoriteClick = handleFavoriteClick,
                            favoriteAlbums = favoriteAlbums,
                            onDetailsClick = handleDetailsClick,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                    else -> {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumListCompactScreen(
    albums: List<Album>,
    onFavoriteClick: (Album) -> Unit,
    favoriteAlbums: Set<String>,
    onDetailsClick: (Album) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(title = { Text("Lista de Álbumes") })
        LazyColumn(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            items(albums) { album ->
                AlbumCard(
                    album = album,
                    onFavoriteClick = onFavoriteClick,
                    isFavorite = favoriteAlbums.contains(album.albumName),
                    onDetailsClick = onDetailsClick
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumListMedExpScreen(
    albums: List<Album>,
    onFavoriteClick: (Album) -> Unit,
    favoriteAlbums: Set<String>,
    onDetailsClick: (Album) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(title = { Text("Pantalla media o grande") })
        LazyColumn(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            items(albums) { album ->
                AlbumCardLand(
                    album = album,
                    onFavoriteClick = onFavoriteClick,
                    isFavorite = favoriteAlbums.contains(album.albumName),
                    onDetailsClick = onDetailsClick
                )
            }
        }
    }
}



