package com.jesus.planetadelasfiestas.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jesus.planetadelasfiestas.R
import com.jesus.planetadelasfiestas.model.Album
import com.jesus.planetadelasfiestas.ui.components.AlbumCard
import com.jesus.planetadelasfiestas.ui.components.AlbumCardLand
import com.jesus.planetadelasfiestas.ui.components.MedHeaderComp

@Composable
fun FavListCompactScreen(
    albums: List<Album>,
    modifier: Modifier = Modifier,
    onDetailsClick: (Album) -> Unit,
    onFavoriteClick: (Album) -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        MedHeaderComp(stringResource(R.string.fav_album_list))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(albums) { album ->
                AlbumCard(
                    album = album,
                    isFavorite = true,
                    onDetailsClick = onDetailsClick,
                    onFavoriteClick = onFavoriteClick
                )
            }
        }
    }
}

@Composable
fun FavListMedExpScreen(
    albums: List<Album>,
    modifier: Modifier = Modifier,
    onDetailsClick: (Album) -> Unit,
    onFavoriteClick: (Album) -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        MedHeaderComp(stringResource(R.string.fav_album_list))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(albums) { album ->
                AlbumCardLand(
                    album = album,
                    isFavorite = true,
                    onDetailsClick = onDetailsClick,
                    onFavoriteClick = onFavoriteClick
                )
            }
        }
    }
}