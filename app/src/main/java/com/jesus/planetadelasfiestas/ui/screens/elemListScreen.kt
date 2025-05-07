package com.jesus.planetadelasfiestas.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jesus.planetadelasfiestas.model.Album
import com.jesus.planetadelasfiestas.model.Datasource
import com.jesus.planetadelasfiestas.ui.components.AlbumCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ElemListScreen() {
    val albumList = Datasource.albumList()
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

    Scaffold(
        topBar = {
            if (selectedAlbum == null) {
                TopAppBar(
                    title = { Text("Lista de Álbumes") }
                )
            } else {
                TopAppBar(
                    title = { Text("Detalles del Álbum") },
                    navigationIcon = {
                        IconButton(onClick = handleBackClick) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        if (selectedAlbum != null) {
            DetailItemScreen(
                album = selectedAlbum!!,
                isFavorite = favoriteAlbums.contains(selectedAlbum!!.albumName),
                onFavoriteClick = handleFavoriteClick,
                onBackClick = handleBackClick
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                items(albumList) { album ->
                    AlbumCard(
                        album = album,
                        onFavoriteClick = handleFavoriteClick,
                        isFavorite = favoriteAlbums.contains(album.albumName),
                        onDetailsClick = handleDetailsClick
                    )
                }
            }
        }
    }
}