package com.jesus.planetadelasfiestas.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jesus.planetadelasfiestas.R
import com.jesus.planetadelasfiestas.model.Album
import com.jesus.planetadelasfiestas.ui.components.AlbumCard
import com.jesus.planetadelasfiestas.ui.components.AlbumCardLand
import com.jesus.planetadelasfiestas.ui.components.ConfirmDeleteDialog
import com.jesus.planetadelasfiestas.ui.components.MedHeaderComp

@Composable
fun FavListCompactScreen(
    albums: List<Album>,
    onFavoriteClick: (Album) -> Unit,
    favoriteAlbums: Set<String>,
    onDetailsClick: (Album) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedAlbum by remember { mutableStateOf<Album?>(null) }

    if (showDialog && selectedAlbum != null) {
        ConfirmDeleteDialog(
            albumName = selectedAlbum!!.albumName,
            onCancel = { showDialog = false },
            onConfirm = {
                onFavoriteClick(selectedAlbum!!)
                showDialog = false
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        MedHeaderComp(stringResource(R.string.fav_list_title))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(albums) { album ->
                AlbumCard(
                    album = album,
                    isFavorite = favoriteAlbums.contains(album.albumName),
                    onFavoriteClick = {
                        if (favoriteAlbums.contains(album.albumName)) {
                            selectedAlbum = album
                            showDialog = true
                        } else {
                            onFavoriteClick(album)
                        }
                    },
                    onDetailsClick = onDetailsClick,
                    onClick = { onDetailsClick(album) },
                    onDeleteClick = {
                        selectedAlbum = it
                        showDialog = true
                    }
                )
            }
        }
    }
}

@Composable
fun FavListMedExpScreen(
    albums: List<Album>,
    onFavoriteClick: (Album) -> Unit,
    favoriteAlbums: Set<String>,
    onDetailsClick: (Album) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedAlbum by remember { mutableStateOf<Album?>(null) }

    if (showDialog && selectedAlbum != null) {
        ConfirmDeleteDialog(
            albumName = selectedAlbum!!.albumName,
            onCancel = { showDialog = false },
            onConfirm = {
                onFavoriteClick(selectedAlbum!!)
                showDialog = false
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(albums) { album ->
                AlbumCardLand(
                    album = album,
                    isFavorite = favoriteAlbums.contains(album.albumName),
                    onFavoriteClick = {
                        if (favoriteAlbums.contains(album.albumName)) {
                            selectedAlbum = album
                            showDialog = true
                        } else {
                            onFavoriteClick(album)
                        }
                    },
                    onDetailsClick = onDetailsClick,
                    onClick = { onDetailsClick(album) },
                    onDeleteClick = {
                        selectedAlbum = it
                        showDialog = true
                    }
                )
            }
        }
    }
}
