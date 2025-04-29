package com.jesus.planetadelasfiestas.ui.screens

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

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (selectedAlbum == null) "Lista de Álbumes" else "Detalles del Álbum",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    if (selectedAlbum != null) {
                        IconButton(onClick = { selectedAlbum = null }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Volver",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        if (selectedAlbum != null) {
            DetailItemScreen(
                album = selectedAlbum!!,
                isFavorite = favoriteAlbums.contains(selectedAlbum!!.albumName),
                onFavoriteClick = handleFavoriteClick,
                onBackClick = { selectedAlbum = null }
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
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