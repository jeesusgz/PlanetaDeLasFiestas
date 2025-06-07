package com.jesus.planetadelasfiestas.ViewModel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import androidx.lifecycle.viewModelScope
import com.jesus.planetadelasfiestas.data.AppTheme
import com.jesus.planetadelasfiestas.data.UserPreferences
import com.jesus.planetadelasfiestas.model.Album
import com.jesus.planetadelasfiestas.network.RetrofitInstance
import com.jesus.planetadelasfiestas.repository.DeezerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs = UserPreferences(application)
    private val apiService = RetrofitInstance.api
    val repository = DeezerRepository(apiService)

    private val _albums = MutableStateFlow<List<Album>>(emptyList())
    val albums: StateFlow<List<Album>> = _albums

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _favoriteAlbums = MutableStateFlow<Set<Long>>(emptySet())
    val favoriteAlbums: StateFlow<Set<Long>> = _favoriteAlbums

    private val _comments = MutableStateFlow<Map<Long, List<String>>>(emptyMap())
    val comments: StateFlow<Map<Long, List<String>>> = _comments

    val appTheme: StateFlow<AppTheme> = prefs.theme.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        AppTheme.SYSTEM
    )

    private val genreMap = mapOf(
        "pop" to 132,
        "rock" to 152,
        "reggaeton" to 197,
        "rap" to 116,
        "electro" to 106,
        "latin" to 197
    )

    init {
        loadTopAlbums()
    }

    fun loadTopAlbums() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val topAlbums = repository.getTopAlbums()
                _albums.value = topAlbums
            } catch (e: Exception) {
                _albums.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadAlbumsByGenre(genreName: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val genreId = genreMap[genreName.lowercase()] ?: 132 // Default a pop
                val artistResponse = repository.getArtistsByGenre(genreId)
                val topArtistName = artistResponse.firstOrNull()?.name

                if (topArtistName != null) {
                    val results = repository.searchAlbums(topArtistName)
                        .sortedByDescending { it.fans }
                    _albums.value = results
                } else {
                    _albums.value = emptyList()
                }
            } catch (e: Exception) {
                _albums.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchAlbums(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val results = repository.searchAlbums(query)
                _albums.value = results
            } catch (e: Exception) {
                _albums.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleFavorite(album: Album) {
        val current = _favoriteAlbums.value
        _favoriteAlbums.value = if (current.contains(album.id)) {
            current - album.id
        } else {
            current + album.id
        }
    }

    fun addComment(albumId: Long, comment: String) {
        val currentComments = _comments.value.toMutableMap()
        val updatedList = currentComments[albumId].orEmpty().toMutableList()
        updatedList.add(comment)
        currentComments[albumId] = updatedList
        _comments.value = currentComments
    }
}