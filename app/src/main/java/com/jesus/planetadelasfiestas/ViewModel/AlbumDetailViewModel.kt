package com.jesus.planetadelasfiestas.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesus.planetadelasfiestas.model.Album
import com.jesus.planetadelasfiestas.repository.AlbumRepository
import com.jesus.planetadelasfiestas.repository.DeezerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AlbumDetailViewModel(private val repository: DeezerRepository) : ViewModel() {

    private val _album = MutableStateFlow<Album?>(null)
    val album: StateFlow<Album?> = _album

    fun loadAlbumDetails(albumId: String) {
        viewModelScope.launch {
            _album.value = repository.getAlbumDetails(albumId)
        }
    }
}
