package com.jesus.planetadelasfiestas.ViewModel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import androidx.lifecycle.viewModelScope
import com.jesus.planetadelasfiestas.data.AppTheme
import com.jesus.planetadelasfiestas.data.UserPreferences
import com.jesus.planetadelasfiestas.data.local.AlbumEntity
import com.jesus.planetadelasfiestas.data.local.AppDatabase
import com.jesus.planetadelasfiestas.data.local.toAlbum
import com.jesus.planetadelasfiestas.model.Album
import com.jesus.planetadelasfiestas.model.toEntity
import com.jesus.planetadelasfiestas.network.RetrofitInstance
import com.jesus.planetadelasfiestas.repository.DeezerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val prefs: UserPreferences,
    private val repository: DeezerRepository
) : ViewModel() {

    private val _saveResult = MutableStateFlow<Boolean?>(null)
    val saveResult: StateFlow<Boolean?> = _saveResult.asStateFlow()

    private val _albums = MutableStateFlow<List<Album>>(emptyList())
    val albums: StateFlow<List<Album>> = _albums.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val favoriteAlbums: StateFlow<Set<Long>> = repository.getFavoriteAlbumIdsFlow()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptySet())

    private val _comments = MutableStateFlow<Map<Long, List<String>>>(emptyMap())
    val comments: StateFlow<Map<Long, List<String>>> = _comments.asStateFlow()

    val appTheme: StateFlow<AppTheme> = prefs.theme.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        AppTheme.SYSTEM
    )

    private val _savedAlbums = MutableStateFlow<List<AlbumEntity>>(emptyList())
    val savedAlbums: StateFlow<List<AlbumEntity>> = _savedAlbums.asStateFlow()

    init {
        loadTopAlbums()
        observeSavedAlbums()
    }

    private fun observeSavedAlbums() {
        viewModelScope.launch {
            repository.getAllSavedAlbums().collect { albums ->
                _savedAlbums.value = albums
            }
        }
    }

    fun deleteSavedAlbum(album: AlbumEntity) {
        viewModelScope.launch {
            repository.deleteAlbumFromDb(album)
        }
    }

    fun loadTopAlbums() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val saved = repository.getAllSavedAlbums().firstOrNull()
                if (!saved.isNullOrEmpty()) {
                    _albums.value = saved.map { it.toAlbum() }
                }
                val topAlbums = repository.getTopAlbums()
                // Aquí cruzas con favoritos antes de mostrar
                _albums.value = markFavorites(topAlbums)
                repository.saveAlbumsToDb(topAlbums)
            } catch (e: Exception) {
                // Si la API falla, se mantiene lo cargado de Room
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
                _albums.value = markFavorites(results)
            } catch (e: Exception) {
                _albums.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleFavorite(album: Album) {
        viewModelScope.launch {
            repository.toggleFavorite(album)
        }
    }

    fun addComment(albumId: Long, comment: String) {
        val currentComments = _comments.value.toMutableMap()
        val updatedList = currentComments[albumId].orEmpty().toMutableList()
        updatedList.add(comment)
        currentComments[albumId] = updatedList
        _comments.value = currentComments
    }

    fun saveAlbum(album: Album) {
        viewModelScope.launch {
            repository.saveAlbumToDb(album.copy(esFavorito = true))
        }
    }

    fun deleteAlbum(album: Album) {
        viewModelScope.launch {
            repository.deleteAlbumFromDb(album.toEntity())
        }
    }

    fun resetSaveResult() {
        _saveResult.value = null
    }

    private suspend fun markFavorites(albums: List<Album>): List<Album> {
        val favoriteIds = repository.getFavoriteAlbumIdsFlow().first()
        return albums.map { album ->
            album.copy(esFavorito = favoriteIds.contains(album.id))
        }
    }
}