package com.jesus.planetadelasfiestas.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesus.planetadelasfiestas.model.Album
import com.jesus.planetadelasfiestas.repository.DeezerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.jesus.planetadelasfiestas.data.local.CommentEntity

@HiltViewModel
class AlbumDetailViewModel @Inject constructor(
    private val repository: DeezerRepository
) : ViewModel() {

    private val _album = MutableStateFlow<Album?>(null)
    val album: StateFlow<Album?> = _album

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    private val _comments = MutableStateFlow<List<CommentEntity>>(emptyList())
    val comments: StateFlow<List<CommentEntity>> = _comments

    fun loadAlbumDetails(albumId: Long) {
        viewModelScope.launch {
            val albumFromDb = repository.getAlbumFromRoom(albumId)
            if (albumFromDb != null) {
                _album.value = albumFromDb
                _isFavorite.value = albumFromDb.esFavorito
                _comments.value = repository.getComments(albumId)
            } else {
                _album.value = null
                _isFavorite.value = false
                _comments.value = emptyList()
            }
            try {
                val albumFromApi = repository.getAlbumDetails(albumId.toString())
                if (albumFromApi != null) {
                    _album.value = albumFromApi.copy(esFavorito = _isFavorite.value)
                }
            } catch (e: Exception) {
            }
        }
    }

    fun addComment(albumId: Long, text: String, author: String) {
        viewModelScope.launch {
            repository.addComment(albumId, text, author)
            _comments.value = repository.getComments(albumId)
        }
    }
}