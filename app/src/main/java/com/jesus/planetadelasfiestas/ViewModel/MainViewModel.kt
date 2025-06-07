package com.jesus.planetadelasfiestas.ViewModel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import androidx.lifecycle.viewModelScope
import com.jesus.planetadelasfiestas.data.AppTheme
import com.jesus.planetadelasfiestas.data.UserPreferences
import com.jesus.planetadelasfiestas.data.local.AppDatabase
import com.jesus.planetadelasfiestas.model.Album
import com.jesus.planetadelasfiestas.network.RetrofitInstance
import com.jesus.planetadelasfiestas.repository.DeezerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs = UserPreferences(application)
    private val apiService = RetrofitInstance.api
    private val _saveResult = MutableLiveData<Boolean?>()
    val saveResult: LiveData<Boolean?> = _saveResult
    private val albumDao = AppDatabase.getInstance(application).albumDao()
    val repository = DeezerRepository(apiService, albumDao)

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

    fun saveAlbum(album: Album) {
        viewModelScope.launch {
            val success = repository.saveAlbumToDb(album)  // Devuelve true si guardó, false si ya existía
            _saveResult.postValue(success)
        }
    }

    fun resetSaveResult() {
        _saveResult.postValue(null)
    }
}