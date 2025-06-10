package com.jesus.planetadelasfiestas.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
fun FavListCompactScreen(
    albums: List<Album>,
    favoriteAlbums: Set<Long>,
    onFavoriteClick: (Album) -> Unit,
    onDeleteAlbum: (Album) -> Unit,
    onDetailsClick: (Album) -> Unit,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedAlbum by remember { mutableStateOf<Album?>(null) }

    if (showDialog && selectedAlbum != null) {
        ConfirmDeleteDialog(
            albumName = selectedAlbum!!.title,
            onCancel = { showDialog = false },
            onConfirm = {
                onDeleteAlbum(selectedAlbum!!)
                showDialog = false
            }
        )
    }

    Column(modifier.fillMaxSize()) {
        MedHeaderComp(stringResource(R.string.fav_list_title))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(albums) { album ->
                AlbumCard(
                    album = album,
                    isFavorite = favoriteAlbums.contains(album.id),
                    onFavoriteClick = { albumToToggle ->
                        if (favoriteAlbums.contains(albumToToggle.id)) {
                            selectedAlbum = albumToToggle
                            showDialog = true
                        } else {
                            onFavoriteClick(albumToToggle)
                        }
                    },
                    onDetailsClick = { navController.navigate(Routes.albumDetailRoute(album.id)) },
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
fun FavListMedExpScreen(
    albums: List<Album>,
    favoriteAlbums: Set<Long>,
    onFavoriteClick: (Album) -> Unit,
    onDeleteAlbum: (Album) -> Unit,
    onDetailsClick: (Album) -> Unit,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedAlbum by remember { mutableStateOf<Album?>(null) }

    if (showDialog && selectedAlbum != null) {
        ConfirmDeleteDialog(
            albumName = selectedAlbum!!.title,
            onCancel = { showDialog = false },
            onConfirm = {
                onDeleteAlbum(selectedAlbum!!)
                showDialog = false
            }
        )
    }

    Column(modifier.fillMaxSize()) {
        MedHeaderComp(stringResource(R.string.fav_list_title))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(albums) { album ->
                AlbumCard(
                    album = album,
                    isFavorite = favoriteAlbums.contains(album.id),
                    onFavoriteClick = { albumToToggle ->
                        if (favoriteAlbums.contains(albumToToggle.id)) {
                            selectedAlbum = albumToToggle
                            showDialog = true
                        } else {
                            onFavoriteClick(albumToToggle)
                        }
                    },
                    onDetailsClick = { navController.navigate(Routes.albumDetailRoute(album.id)) },
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
