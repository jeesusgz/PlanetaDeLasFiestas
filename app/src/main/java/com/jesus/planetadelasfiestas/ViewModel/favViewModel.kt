package com.jesus.planetadelasfiestas.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesus.planetadelasfiestas.data.local.toAlbum
import com.jesus.planetadelasfiestas.model.Album
import com.jesus.planetadelasfiestas.model.toEntity
import com.jesus.planetadelasfiestas.repository.DeezerRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritosViewModel(
    private val repository: DeezerRepository
) : ViewModel() {

    val favoritos: StateFlow<List<Album>> = repository.getFavoriteAlbumsFlow()
        .map { list -> list.map { it.toAlbum() } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun eliminarFavorito(album: Album) {
        viewModelScope.launch {
            repository.deleteAlbumFromDb(album.toEntity())
        }
    }
}