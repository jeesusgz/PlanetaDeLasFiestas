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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.compose.PlanetaDeLasFiestasTheme
import com.jesus.planetadelasfiestas.R
import com.jesus.planetadelasfiestas.model.Album
import com.jesus.planetadelasfiestas.model.Datasource
import com.jesus.planetadelasfiestas.ui.components.AlbumCard
import com.jesus.planetadelasfiestas.ui.components.AlbumCardLand
import com.jesus.planetadelasfiestas.ui.components.MedHeaderComp
import com.jesus.planetadelasfiestas.utils.getWindowSizeClass

@Composable
fun AlbumListCompactScreen(
    albums: List<Album>,
    onFavoriteClick: (Album) -> Unit,
    favoriteAlbums: Set<String>,
    onDetailsClick: (Album) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        MedHeaderComp(title = stringResource(id = R.string.album_list))
        LazyColumn(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            items(albums) { album ->
                AlbumCard(
                    album = album,
                    isFavorite = favoriteAlbums.contains(album.albumName),
                    onFavoriteClick = onFavoriteClick,
                    onDetailsClick = onDetailsClick,
                    onClick = { onDetailsClick(album) }
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
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        MedHeaderComp(title = stringResource(id = R.string.album_list))
        LazyColumn(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            items(albums) { album ->
                AlbumCardLand(
                    album = album,
                    isFavorite = favoriteAlbums.contains(album.albumName),
                    onFavoriteClick = onFavoriteClick,
                    onDetailsClick = onDetailsClick,
                    onClick = { onDetailsClick(album) }
                )
            }
        }
    }
}



