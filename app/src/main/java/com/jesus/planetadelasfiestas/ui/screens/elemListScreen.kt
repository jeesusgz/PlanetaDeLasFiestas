package com.jesus.planetadelasfiestas.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jesus.planetadelasfiestas.R
import com.jesus.planetadelasfiestas.ViewModel.AlbumListViewModel
import com.jesus.planetadelasfiestas.ViewModel.MainViewModel
import com.jesus.planetadelasfiestas.model.Album
import com.jesus.planetadelasfiestas.model.Routes
import com.jesus.planetadelasfiestas.ui.components.AlbumCard
import com.jesus.planetadelasfiestas.ui.components.AlbumCardLand
import com.jesus.planetadelasfiestas.ui.components.ConfirmDeleteDialog
import com.jesus.planetadelasfiestas.ui.components.MedHeaderComp

@Composable
fun AlbumListCompactScreen(
    viewModel: MainViewModel,
    favoriteAlbums: Set<Long>,
    onFavoriteClick: (Album) -> Unit,
    onDeleteAlbum: (Album) -> Unit,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var searchText by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var selectedAlbum by remember { mutableStateOf<Album?>(null) }

    val albums by viewModel.albums.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val saveResult by viewModel.saveResult.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(searchText) {
        if (searchText.length >= 3) {
            viewModel.searchAlbums(searchText)
        }
    }

    saveResult?.let { success ->
        LaunchedEffect(success) {
            if (success) {
                Toast.makeText(context, "Álbum guardado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Álbum ya está guardado", Toast.LENGTH_SHORT).show()
            }
            viewModel.resetSaveResult()
        }
    }

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

        if (isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(albums) { album ->
                    AlbumCard(
                        album = album,
                        isFavorite = favoriteAlbums.contains(album.id),
                        onFavoriteClick = {
                            if (favoriteAlbums.contains(album.id)) {
                                selectedAlbum = album
                                showDialog = true
                            } else {
                                viewModel.saveAlbum(album)
                            }
                        },
                        onDetailsClick = {
                            navController.navigate(Routes.albumDetailRoute(album.id))
                        },
                        onClick = {
                            navController.navigate(Routes.albumDetailRoute(album.id))
                        },
                        onDeleteClick = {
                            selectedAlbum = album
                            showDialog = true
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AlbumListMedExpScreen(
    viewModel: MainViewModel,
    favoriteAlbums: Set<Long>,
    onFavoriteClick: (Album) -> Unit,
    onDeleteAlbum: (Album) -> Unit,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var searchText by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var selectedAlbum by remember { mutableStateOf<Album?>(null) }

    val albums by viewModel.albums.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val saveResult by viewModel.saveResult.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(searchText) {
        if (searchText.length >= 3) {
            viewModel.searchAlbums(searchText)
        }
    }

    saveResult?.let { success ->
        LaunchedEffect(success) {
            if (success) {
                Toast.makeText(context, "Álbum guardado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Álbum ya está guardado", Toast.LENGTH_SHORT).show()
            }
            viewModel.resetSaveResult()
        }
    }

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

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                items(albums) { album ->
                    AlbumCardLand(
                        album = album,
                        isFavorite = favoriteAlbums.contains(album.id),
                        onFavoriteClick = {
                            if (favoriteAlbums.contains(album.id)) {
                                selectedAlbum = album
                                showDialog = true
                            } else {
                                viewModel.saveAlbum(album)
                            }
                        },
                        onDetailsClick = {
                            navController.navigate(Routes.albumDetailRoute(album.id))
                        },
                        onClick = {
                            navController.navigate(Routes.albumDetailRoute(album.id))
                        },
                        onDeleteClick = {
                            selectedAlbum = album
                            showDialog = true
                        }
                    )
                }
            }
        }
    }
}



