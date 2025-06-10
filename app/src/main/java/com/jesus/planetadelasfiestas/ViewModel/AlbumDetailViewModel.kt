package com.jesus.planetadelasfiestas.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesus.planetadelasfiestas.data.local.toAlbum
import com.jesus.planetadelasfiestas.model.Album
import com.jesus.planetadelasfiestas.repository.AlbumRepository
import com.jesus.planetadelasfiestas.repository.DeezerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumDetailViewModel @Inject constructor(
    private val repository: DeezerRepository
) : ViewModel() {

    private val _album = MutableStateFlow<Album?>(null)
    val album: StateFlow<Album?> = _album

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    private val _comments = MutableStateFlow<List<String>>(emptyList())
    val comments: StateFlow<List<String>> = _comments

    fun loadAlbumDetails(albumId: Long) {
        viewModelScope.launch {
            val albumFromDb = repository.getAlbumFromRoom(albumId)
            if (albumFromDb != null) {
                _album.value = albumFromDb
                _isFavorite.value = albumFromDb.esFavorito
                _comments.value = loadComments(albumId)
            } else {
                _album.value = null
            }

            // Intenta cargar de la API, pero captura cualquier excepción de red
            try {
                val albumFromApi = repository.getAlbumDetails(albumId.toString())
                if (albumFromApi != null) {
                    _album.value = albumFromApi.copy(esFavorito = _isFavorite.value)
                    // Si quieres, puedes guardar en Room aquí
                }
            } catch (e: Exception) {
                // No hay conexión o error de red: ignora y sigue mostrando datos locales
            }
        }
    }

    private suspend fun loadComments(albumId: Long): List<String> {
        // Implementa si tienes guardado comentarios en DB, si no, devuelve vacío
        return emptyList()
    }
}