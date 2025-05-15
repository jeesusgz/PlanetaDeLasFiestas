package com.jesus.planetadelasfiestas.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jesus.planetadelasfiestas.R
import com.jesus.planetadelasfiestas.model.Album
import com.jesus.planetadelasfiestas.model.Routes
import com.jesus.planetadelasfiestas.ui.components.AlbumCard
import com.jesus.planetadelasfiestas.ui.components.AlbumCardLand
import com.jesus.planetadelasfiestas.ui.components.ConfirmDeleteDialog
import com.jesus.planetadelasfiestas.ui.components.MedHeaderComp

@Composable
fun AlbumListCompactScreen(
    albums: List<Album>,
    onFavoriteClick: (Album) -> Unit,
    favoriteAlbums: Set<String>,
    onDetailsClick: (Album) -> Unit,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedAlbum by remember { mutableStateOf<Album?>(null) }
    var searchText by remember { mutableStateOf("") }

    val filteredAlbums = albums.filter {
        it.albumName.contains(searchText, ignoreCase = true) ||
                it.artistName.contains(searchText, ignoreCase = true)
    }

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

    Column(modifier = modifier.fillMaxSize()) {
        MedHeaderComp(title = stringResource(id = R.string.album_list))

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text(stringResource(R.string.search)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(filteredAlbums) { album ->
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
                    onDetailsClick = {
                        navController.navigate(Routes.albumDetailRoute(it.albumName))
                    },
                    onClick = { onDetailsClick(album) },
                    onDeleteClick = { albumToDelete ->
                        selectedAlbum = albumToDelete
                        showDialog = true
                    }
                )
            }
        }
    }
}

@Composable
fun AlbumListMedExpScreen(
    albums: List<Album>,
    onFavoriteClick: (Album) -> Unit,
    favoriteAlbums: Set<String>,
    onDetailsClick: (Album) -> Unit,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedAlbum by remember { mutableStateOf<Album?>(null) }
    var searchText by remember { mutableStateOf("") }

    val filteredAlbums = albums.filter {
        it.albumName.contains(searchText, ignoreCase = true) ||
                it.artistName.contains(searchText, ignoreCase = true)
    }

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

    Column(modifier = modifier.fillMaxSize()) {
        MedHeaderComp(title = stringResource(id = R.string.album_list_Extend))

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text(stringResource(R.string.search)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(filteredAlbums) { album ->
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
                    onDetailsClick = {
                        navController.navigate(Routes.albumDetailRoute(it.albumName))
                    },
                    onClick = { onDetailsClick(album) },
                    onDeleteClick = { albumToDelete ->
                        selectedAlbum = albumToDelete
                        showDialog = true
                    }
                )
            }
        }
    }
}


