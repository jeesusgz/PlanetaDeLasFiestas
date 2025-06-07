package com.jesus.planetadelasfiestas.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesus.planetadelasfiestas.model.Album
import com.jesus.planetadelasfiestas.repository.AlbumRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AlbumListViewModel(private val repository: AlbumRepository) : ViewModel() {
    private val _albums = MutableStateFlow<List<Album>>(emptyList())
    val albums: StateFlow<List<Album>> = _albums

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchAlbums()
    }

    private fun fetchAlbums() {
        viewModelScope.launch {
            _isLoading.value = true
            _albums.value = repository.getAlbumsByQuery("")
            _isLoading.value = false
        }
    }

    fun searchAlbums(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val results = repository.getAlbumsByQuery(query)
                _albums.value = results
            } catch (e: Exception) {
                _albums.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
