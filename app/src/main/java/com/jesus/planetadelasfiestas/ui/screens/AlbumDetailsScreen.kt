package com.jesus.planetadelasfiestas.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jesus.planetadelasfiestas.ViewModel.AlbumDetailViewModel
import com.jesus.planetadelasfiestas.ViewModel.AlbumDetailViewModelFactory
import com.jesus.planetadelasfiestas.ViewModel.MainViewModel
import com.jesus.planetadelasfiestas.model.Album

@Composable
fun AlbumDetailScreen(
    albumId: Long,
    mainViewModel: MainViewModel,
    favoriteAlbums: Set<Long>,
    commentsMap: Map<Long, List<String>>,
    addComment: (Long, String) -> Unit,
    onBackClick: () -> Unit,
    onDeleteAlbum: (Album) -> Unit
) {
    val detailViewModel: AlbumDetailViewModel = hiltViewModel()

    val album by detailViewModel.album.collectAsState()
    val favoriteSet by mainViewModel.favoriteAlbums.collectAsState()

    LaunchedEffect(albumId) {
        detailViewModel.loadAlbumDetails(albumId)
    }

    fun handleFavoriteClick(album: Album) {
        if (favoriteSet.contains(album.id)) {
            mainViewModel.deleteAlbum(album)
        } else {
            mainViewModel.saveAlbum(album)
        }
    }

    if (album != null) {
        DetailItemScreen(
            album = album!!,
            isFavorite = favoriteSet.contains(album!!.id),
            onFavoriteClick = { handleFavoriteClick(album!!) },
            onDeleteAlbum = { onDeleteAlbum(it) },
            onBackClick = onBackClick,
            comments = commentsMap[album!!.id].orEmpty(),
            onAddComment = { comment -> addComment(album!!.id, comment) }
        )
    } else {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}